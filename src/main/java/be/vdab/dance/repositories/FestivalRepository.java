package be.vdab.dance.repositories;

import be.vdab.dance.domain.Festival;
import be.vdab.dance.exceptions.FestivalNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class FestivalRepository {
    private final JdbcTemplate template;

    public FestivalRepository(JdbcTemplate template) {
        this.template = template;
    }
    private final RowMapper<Festival> festivalMapper =
            (result, rowNum) -> new Festival(result.getLong("id"),
                    result.getString("naam"), result.getInt("ticketsBeschikbaar"),
                    result.getBigDecimal("reclameBudget"));

    public List<Festival> findAll() {
        var sql = """
                select id, naam, ticketsBeschikbaar, reclameBudget
                from dance.festivals
                order by naam
                """;
        return template.query(sql, festivalMapper);
    }
    public List<Festival> findUitverkocht() {
        var sql = """
                select id, naam, ticketsBeschikbaar, reclameBudget
                from  dance.festivals
                where ticketsBeschikbaar = 0
                order by naam
                """;

        return template.query(sql, festivalMapper);
    }
    public void delete(long id){
        var sql = """
                delete from dance.festivals
                where id = ?
                """;

        template.update(sql, id);
    }
    public long create(Festival festival){
        var sql = """
                insert into festivals(naam, ticketsBeschikbaar, reclameBudget)
                values (?, ?, ?)
                """;

        var keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            var statement = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, festival.getNaam());
                statement.setLong(2, festival.getTicketsBeschikbaar());
                statement.setBigDecimal(3, festival.getReclameBudget());
                return statement;
        }
        ,
                keyHolder);
        return keyHolder.getKey().longValue();
    }
    public Optional<Festival> findAndLockById(long id){
        try {
            var sql = """
                select id, naam, ticketsBeschikbaar, reclameBudget
                from festivals
                where id = ?
                for update
                """;
            return Optional.of(template.queryForObject(sql, festivalMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex){
            return Optional.empty();
        }
    }
    public long findAantal(){
        var sql = """
                select count(*)
                from festivals
                """;
        return template.queryForObject(sql, Long.class);
    }
    public int verhoogReclameBudgetVanAlleFestivals(BigDecimal budget){
        var sql = """
                update festivals
                set reclameBudget = reclameBudget +  ?
                """;
        return template.update(sql, budget);
    }
    public void update(Festival festival){
        var sql = """
                update festivals
                set ticketsBeschikbaar = ?
                where id = ?
                """;
        if (template.update(sql, festival.getTicketsBeschikbaar(),festival.getId()) == 0){
            throw new FestivalNietGevondenException(festival.getId());
        }
    }
}
