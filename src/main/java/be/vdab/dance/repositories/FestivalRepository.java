package be.vdab.dance.repositories;

import be.vdab.dance.domain.Festival;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
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
}
