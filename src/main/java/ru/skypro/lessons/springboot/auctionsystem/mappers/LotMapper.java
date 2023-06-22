package ru.skypro.lessons.springboot.auctionsystem.mappers;

import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.auctionsystem.dto.CreateLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.FullLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.Lot;
import ru.skypro.lessons.springboot.auctionsystem.entity.LotEntity;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;

@Component
public class LotMapper {

    public LotEntity toEntity(CreateLot createLot){
        if(createLot == null){
            return null;
        }

        LotEntity lotEntity = new LotEntity();
        lotEntity.setTitle(createLot.getTitle());
        lotEntity.setStatus(Status.CREATED);
        lotEntity.setDescription(createLot.getDescription());
        lotEntity.setStartPrice(createLot.getStartPrice());
        lotEntity.setBidPrice(createLot.getBidPrice());

        return lotEntity;
    }

    public Lot toLot(LotEntity lotEntity){
        if(lotEntity == null){
            return null;
        }

        Lot lot = new Lot();
        lot.setId(lotEntity.getId());
        lot.setStatus(lotEntity.getStatus());
        lot.setTitle(lotEntity.getTitle());
        lot.setDescription(lotEntity.getDescription());
        lot.setStartPrice(lotEntity.getStartPrice());
        lot.setBidPrice(lotEntity.getBidPrice());

        return lot;
    }

    public FullLot toFullLot(LotEntity lotEntity){
        if(lotEntity == null){
            return null;
        }

        FullLot fullLot = new FullLot();
        fullLot.setId(lotEntity.getId());
        fullLot.setStatus(lotEntity.getStatus());
        fullLot.setTitle(lotEntity.getTitle());
        fullLot.setDescription(lotEntity.getDescription());
        fullLot.setStartPrice(lotEntity.getStartPrice());
        fullLot.setBidPrice(lotEntity.getBidPrice());
        fullLot.setCurrentPrice(
                lotEntity.getStartPrice() + lotEntity.getBidPrice() * lotEntity.getBids().size()
        );
        //fullLot.setLastBid(null);
        fullLot.setLastBid(
                lotEntity.getBids().get(lotEntity.getBids().size()-1)
        );

        return fullLot;
    }
}
