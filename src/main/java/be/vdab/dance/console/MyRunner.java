package be.vdab.dance.console;

import be.vdab.dance.domain.Boeking;
import be.vdab.dance.exceptions.FestivalNietGevondenException;
import be.vdab.dance.exceptions.OnvoldoendeTicketsException;
import be.vdab.dance.services.BoekingService;
import be.vdab.dance.services.FestivalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component

public class MyRunner implements CommandLineRunner {
    private final FestivalService festivalService;
    private final BoekingService boekingService;
    public MyRunner(FestivalService festivalService, BoekingService boekingService){
        this.festivalService = festivalService;
        this.boekingService = boekingService;
    }

    @Override
    public void run(String... args) {
        //serviceEnTransactieGrotereMethod();
        serviceMetMeerdereRepositoryDependencies();
    }
    public void serviceEnTransactieGrotereMethod(){
        System.out.println("Geef id: ");
        var scanner = new Scanner(System.in);
        var id = scanner.nextInt();
        festivalService.annulatie(id);
        festivalService.findAll().forEach(festival ->
                System.out.println(festival.getId() +  ":" + festival.getNaam()
                        + ":" + festival.getTicketsBeschikbaar() + ":" + festival.getReclameBudget()));
        System.out.print("Festival id: ");
        long idVoorAnnulatie = scanner.nextLong();
        try {
            festivalService.annulatie(idVoorAnnulatie);
            System.out.println("Festival geannuleerd");
        } catch (FestivalNietGevondenException ex){
            System.err.println("Festival " + ex.getId() + " niet gevonden");
        }
    }
    public void serviceMetMeerdereRepositoryDependencies(){
        System.out.println("Geef naam van het festival: ");
        var scanner = new Scanner(System.in);
        var naam = scanner.nextLine();
        System.out.println("Geef aantal gewenste tickets");
        var aantalTickets = scanner.nextInt();
        System.out.println("Geef id van festival");
        var festivalId = scanner.nextLong();
        try {
            var boeking = new Boeking(naam, aantalTickets, festivalId);
            boekingService.create(boeking);
            System.out.println("Boeking gelukt");
        } catch (IllegalArgumentException ex){
            System.err.println(ex.getMessage());
        } catch (FestivalNietGevondenException ex){
            System.err.println("Boeking mislukt. Festival ontbreekt. Id:" + ex.getId());
        } catch (OnvoldoendeTicketsException ex){
            System.err.println("Boeking mislukt. Onvoldoende tickets.");
        }

    }



}
