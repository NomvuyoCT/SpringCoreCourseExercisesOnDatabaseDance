package be.vdab.dance.repositories;

import be.vdab.dance.domain.Festival;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(FestivalRepository.class)
@Sql("/festivals.sql")
class FestivalRepositoryTest extends
        AbstractTransactionalJUnit4SpringContextTests {
    private static final String FESTIVALS = "festivals";
    private final FestivalRepository festivalRepository;
    public FestivalRepositoryTest(FestivalRepository festivalRepository){
        this.festivalRepository = festivalRepository;
    }

    @Test
    void findAllGeeftAlleFestivalsGesorteerdOpNaam() {
        assertThat(festivalRepository.findAll())
                .hasSize(countRowsInTable(FESTIVALS))
                .extracting(Festival::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }


    @Test
    void findUitverkochtGeeftDeUitverkochteFestivalsGesorteerdOpNaam() {
        assertThat(festivalRepository.findUitverkocht())
                .hasSize(countRowsInTableWhere(FESTIVALS, "ticketsbeschikbaar = 0"))
                .extracting(Festival::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void create() {
        var id = festivalRepository
                .create(new Festival(0, "festival3", 10, BigDecimal.valueOf(1000)));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(FESTIVALS, "id = " + id)).isOne();
    }
    private long idVanFestival1(){
        return jdbcTemplate.queryForObject(
                "select id from festivals where naam = 'festival1'", Long.class
        );
    }

    @Test
    void delete() {
        var id = idVanFestival1();
        festivalRepository.delete(id);
        assertThat(countRowsInTableWhere(FESTIVALS, "id = " + id)).isZero();
    }

    @Test
    void findAndLockById() {
        assertThat(festivalRepository.findAndLockById(idVanFestival1()))
                .hasValueSatisfying(
                        festival -> assertThat(festival.getReclameBudget()).isEqualByComparingTo("festival")
                );
    }

    @Test
    void findAndLockByOnbestaandeIdVindGeenFestival() {
        assertThat(festivalRepository.findAndLockById(Long.MAX_VALUE)).isEmpty();
    }

    @Test
    void findAantal() {
        assertThat(festivalRepository.findAantal()).isEqualTo(countRowsInTable(FESTIVALS));
    }

    @Test
    void verhoogReclameBudgetVanAlleFestivalsMetTien() {
        festivalRepository.verhoogReclameBudgetVanAlleFestivals((BigDecimal.TEN));
        var id = idVanFestival1();
        assertThat(countRowsInTableWhere(FESTIVALS,
                "reclameBudget = 3000.00 and id = " + id)).isOne();
    }

    @Test
    void update() {
        var id = idVanFestival1();
        var festival =new Festival(id, "festival1", 30, BigDecimal.valueOf(700));
        festivalRepository.update(festival);
        assertThat(countRowsInTableWhere(FESTIVALS,
                "id = " + id)).isOne();
    }
}
