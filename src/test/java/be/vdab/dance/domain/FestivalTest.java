package be.vdab.dance.domain;

import be.vdab.dance.exceptions.OnvoldoendeTicketsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class FestivalTest {
    private Festival coachella, burningMan;
    @BeforeEach
    void beforeEach(){
        coachella = new Festival(1, "Coachella", 10, BigDecimal.valueOf(1000));
        burningMan = new Festival(1, "Burning Man", 5, BigDecimal.valueOf(1500));
    }

    @Test
    void boek() {
        coachella.boek(3);
        assertThat(coachella.getTicketsBeschikbaar()).isEqualTo(7);
    }

    @Test
    void boekenMisluktBijOnvoldoendeTickets() {
        assertThatExceptionOfType(OnvoldoendeTicketsException.class).isThrownBy(() ->
                coachella.boek(11));
    }

}
