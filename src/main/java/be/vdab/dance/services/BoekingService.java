package be.vdab.dance.services;

import be.vdab.dance.domain.Boeking;
import be.vdab.dance.exceptions.FestivalNietGevondenException;
import be.vdab.dance.repositories.BoekingRepository;
import be.vdab.dance.repositories.FestivalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BoekingService {
    private final BoekingRepository boekingRepository;
    private final FestivalRepository festivalRepository;
    public BoekingService(BoekingRepository boekingRepository,
                          FestivalRepository festivalRepository){
        this.boekingRepository = boekingRepository;
        this.festivalRepository = festivalRepository;
    }
    @Transactional
    public void create(Boeking boeking){
        var optionalFestivalTeBoeken = festivalRepository.findAndLockById(boeking.getFestivalId());
        if (optionalFestivalTeBoeken.isEmpty()){
            throw new FestivalNietGevondenException(boeking.getFestivalId());
        }
        var festivalTeBoeken = optionalFestivalTeBoeken.get();
        festivalTeBoeken.boek(boeking.getAantalTickets());
        festivalRepository.update(festivalTeBoeken);
        boekingRepository.create(boeking);
    }
}
