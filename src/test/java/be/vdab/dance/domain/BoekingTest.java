package be.vdab.dance.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class BoekingTest {
    @Test
    void eenBoekingDieLukt() {
        new Boeking( "festival1", 20, 3);
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void naamMoetNietLeegZijn(String string) {
        assertThatIllegalArgumentException().isThrownBy(()
        -> new Boeking(string, 20, 3));
    }

    @Test
    void naamFestivalMoetIngevuldZijn() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new Boeking(null, 20, 3));
    }

    @Test
    void eenBoekingMetGeenTicketsIsMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new Boeking("festival1", 0, 3));
    }

    @Test
    void eenBoekingMetNegatiefAantalTicketsMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new Boeking("festival1", -1, 3));
    }

    @Test
    void festivalIdMoetPositiefZijn() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new Boeking("festival1", 20, 0));
    }


}
