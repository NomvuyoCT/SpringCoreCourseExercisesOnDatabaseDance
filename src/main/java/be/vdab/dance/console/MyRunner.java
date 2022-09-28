package be.vdab.dance.console;

import be.vdab.dance.services.FestivalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class MyRunner implements CommandLineRunner {
    private final FestivalService festivalService;
    public MyRunner(FestivalService festivalService){
        this.festivalService = festivalService;
    }

    @Override
    public void run(String... args) {
        festivalService.findAll().forEach(festival ->
                System.out.println(festival.getId() +  ":" + festival.getNaam()
                + ":" + festival.getTicketsBeschikbaar() + ":" + festival.getReclameBudget()));
    }
}
