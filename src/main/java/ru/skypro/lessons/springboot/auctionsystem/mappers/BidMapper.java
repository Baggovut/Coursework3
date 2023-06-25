package ru.skypro.lessons.springboot.auctionsystem.mappers;

import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.auctionsystem.dto.Bidder;
import ru.skypro.lessons.springboot.auctionsystem.entity.BidEntity;

@Component
public class BidMapper {
    public Bidder toBidder(BidEntity bid){
        if(bid == null){
            return null;
        }

        Bidder bidder = new Bidder();
        bidder.setBidderName(bid.getBidderName());
        bidder.setBidDate(bid.getBidDate());
        return bidder;
    }
}
