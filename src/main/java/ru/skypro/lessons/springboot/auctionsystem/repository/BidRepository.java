package ru.skypro.lessons.springboot.auctionsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.auctionsystem.dto.Bidder;
import ru.skypro.lessons.springboot.auctionsystem.entity.BidEntity;

import java.util.Optional;

public interface BidRepository extends CrudRepository<BidEntity,Integer> {
    Optional<BidEntity> findFirstByLotIdIsOrderByBidDate(Integer lotId);
    Optional<BidEntity> findFirstByLotIdIsOrderByBidDateDesc(Integer lotId);
    Integer countByLotId(Integer lotId);
    @Query("SELECT new ru.skypro.lessons.springboot.auctionsystem.dto.Bidder(bid.bidderName, bid.bidDate) FROM BidEntity bid " +
            "WHERE bid.lotId = :id " +
            "GROUP BY bid.bidderName, bid.bidDate " +
            "ORDER BY COUNT(bid.bidderName) DESC , bid.bidDate DESC " +
            "LIMIT 1"
    )
    Optional<Bidder> findFrequentBidder(@Param("id") Integer lotId);
}
