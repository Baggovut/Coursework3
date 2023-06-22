package ru.skypro.lessons.springboot.auctionsystem.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.auctionsystem.dto.Bidder;
import ru.skypro.lessons.springboot.auctionsystem.dto.CreateLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.FullLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.Lot;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;

import java.util.List;

public interface LotService {
    Bidder getFirstBidder(Integer id);
    Bidder getFrequentBidder(Integer id);
    FullLot getFullLotInfo(Integer id);
    void startLotTrading(Integer id);
    void createBid(Integer id, String name);
    void stopLotTrading(Integer id);
    Lot createLot(CreateLot createLot);
    List<Lot> getLotsList(Status status, Integer page);
    ResponseEntity<Resource> getLotExport();
}
