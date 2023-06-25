package ru.skypro.lessons.springboot.auctionsystem.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.auctionsystem.dto.Bidder;
import ru.skypro.lessons.springboot.auctionsystem.dto.CreateLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.FullLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.Lot;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;
import ru.skypro.lessons.springboot.auctionsystem.service.LotService;

import java.util.List;

@RestController
@RequestMapping("/lot")
@AllArgsConstructor
public class LotController {
    private final  LotService lotService;
    @GetMapping("/{id}/first")
    public Bidder getFirstBidder(@PathVariable(name = "id") Integer id){
        return lotService.getFirstBidder(id);
    }
    @GetMapping("/{id}/frequent")
    public Bidder getFrequentBidder(@PathVariable(name = "id") Integer id){
        return lotService.getFrequentBidder(id);
    }
    @GetMapping("/{id}")
    public FullLot getFullLotInfo(@PathVariable(name = "id") Integer id){
        return lotService.getFullLotInfo(id);
    }
    @PostMapping("/{id}/start")
    public void startLotTrading(@PathVariable(name = "id") Integer id){
        lotService.startLotTrading(id);
    }
    @PostMapping("/{id}/bid")
    public void createBid(@PathVariable(name = "id") Integer id, @RequestBody String name){
        lotService.createBid(id, name);
    }
    @PostMapping("/{id}/stop")
    public void stopLotTrading(@PathVariable(name = "id") Integer id){
        lotService.stopLotTrading(id);
    }
    @PostMapping()
    public Lot createLot(@RequestBody CreateLot createLot){
        return lotService.createLot(createLot);
    }
    @GetMapping
    public List<Lot> getLotsList(Status status, Integer page){
        return lotService.getLotsList(status, page);
    }
    @GetMapping("/export")
    public ResponseEntity<Resource> getLotExport(){
        return lotService.getLotExport();
    }
}
