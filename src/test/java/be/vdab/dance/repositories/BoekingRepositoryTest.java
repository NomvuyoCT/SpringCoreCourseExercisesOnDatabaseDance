package be.vdab.dance.repositories;

import be.vdab.dance.domain.Boeking;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BoekingRepository.class)
@Sql("/festivals.sql")
class BoekingRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BOEKINGEN = "boekingen";
    private BoekingRepository boekingRepository;
    BoekingRepositoryTest(BoekingRepository boekingRepository){
        this.boekingRepository = boekingRepository;
    }
    private long idVanFestival1(){
        return jdbcTemplate.queryForObject( //jdbcTemplate(in this context) is a AbstractTransactionalJUnit4SpringContextTests
                //field
                "select id from festivals where naam = 'festival1'", Long.class
        );
    }

    @Test
    void create() {
        var id = idVanFestival1();
        boekingRepository.create(
                new Boeking("festival1", 1, id)
        );
        assertThat(countRowsInTableWhere(BOEKINGEN, "festivalId = " + id)).isOne();
    }
}
