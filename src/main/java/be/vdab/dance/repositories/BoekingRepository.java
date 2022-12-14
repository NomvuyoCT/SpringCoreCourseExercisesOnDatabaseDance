package be.vdab.dance.repositories;

import be.vdab.dance.domain.Boeking;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BoekingRepository {
    private final JdbcTemplate template; //in this context, JdbcTemplate is a class in the Spring framework
    public BoekingRepository(JdbcTemplate template){
        this.template =template;
    }
    public void create(Boeking boeking){
        var sql = """
                insert into boekingen(naam, aantalTickets, festivalId)
                values(?, ?, ?)
                """;
        template.update(sql, boeking.getNaam(), boeking.getAantalTickets(),
                boeking.getFestivalId());
    }
}
