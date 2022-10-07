package be.vdab.dance.domain;

import java.util.regex.Pattern;

public class Boeking {
    /*private static final Pattern PATTERN =
            Pattern.compile("^.*$");*/
    private final long id;
    private final String naam;
    private final int aantalTickets;
    private final long festivalId;
    public Boeking(String naam, int aantalTickets, long festivalId){
        //var matcher = PATTERN.matcher(naam);
        if (naam == null || naam.isEmpty()){
            throw new IllegalArgumentException("Naam moet ingevuld zijn");
        }
        if (aantalTickets <= 0){
            throw new IllegalArgumentException("Aantal tickets moet positief zijn");
        }
        if (festivalId <= 0){
            throw new IllegalArgumentException("Id moet positief zijn");
        }
        this.id = 0;
        this.naam = naam;
        this.aantalTickets = aantalTickets;
        this.festivalId = festivalId;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getAantalTickets() {
        return aantalTickets;
    }

    public long getFestivalId() {
        return festivalId;
    }
}
