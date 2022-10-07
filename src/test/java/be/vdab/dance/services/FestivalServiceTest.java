package be.vdab.dance.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(FestivalService.class)
class FestivalServiceTest {
    private final FestivalService festivalService;
    public FestivalServiceTest(FestivalService festivalService){
        this.festivalService = festivalService;
    }

    @Test
    void hetBudgetVanEenFestival() {
    }
}
