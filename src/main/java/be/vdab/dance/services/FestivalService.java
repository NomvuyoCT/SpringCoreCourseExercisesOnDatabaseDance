package be.vdab.dance.services;

import be.vdab.dance.domain.Festival;
import be.vdab.dance.exceptions.FestivalNietGevondenException;
import be.vdab.dance.repositories.FestivalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FestivalService {
    private final FestivalRepository festivalRepository;
    public FestivalService(FestivalRepository festivalRepository){
        this.festivalRepository =festivalRepository;
    }
    public List<Festival> findAll(){
        return festivalRepository.findAll();
    }
    public List<Festival> findUitverkocht() {
        return festivalRepository.findUitverkocht();
    }
    @Transactional
    public void delete(long id){
        festivalRepository.delete(id);
    }
    @Transactional
    public long create(Festival festival){
        return festivalRepository.create(festival);
    }
    @Transactional
    public void annulatie(long id){
    var festivalDatGeannuleerdWordt = festivalRepository.findAndLockById(id);
    if (festivalDatGeannuleerdWordt.isEmpty()){
        throw new FestivalNietGevondenException(id);
    }

    var budget = festivalDatGeannuleerdWordt.get().getReclameBudget();
    festivalRepository.delete(id);
    var aantalFestivals = festivalRepository.findAantal();
    var verhoogMet = budget.divide(BigDecimal.valueOf(aantalFestivals),2, RoundingMode.HALF_UP);

    festivalRepository.verhoogReclameBudgetVanAlleFestivals(verhoogMet);


    }
}
