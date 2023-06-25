package ru.skypro.lessons.springboot.auctionsystem.service;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.auctionsystem.dto.Bidder;
import ru.skypro.lessons.springboot.auctionsystem.dto.CreateLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.FullLot;
import ru.skypro.lessons.springboot.auctionsystem.dto.Lot;
import ru.skypro.lessons.springboot.auctionsystem.entity.BidEntity;
import ru.skypro.lessons.springboot.auctionsystem.entity.LotEntity;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;
import ru.skypro.lessons.springboot.auctionsystem.exceptions.IdNotFoundException;
import ru.skypro.lessons.springboot.auctionsystem.exceptions.WrongParamsException;
import ru.skypro.lessons.springboot.auctionsystem.mappers.BidMapper;
import ru.skypro.lessons.springboot.auctionsystem.mappers.LotMapper;
import ru.skypro.lessons.springboot.auctionsystem.repository.BidRepository;
import ru.skypro.lessons.springboot.auctionsystem.repository.LotPagingRepository;
import ru.skypro.lessons.springboot.auctionsystem.repository.LotRepository;

import java.io.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class LotServiceImpl implements LotService{
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final LotPagingRepository lotPagingRepository;
    private final BidMapper bidMapper;
    private final LotMapper lotMapper;
    @Override
    public Bidder getFirstBidder(Integer id) {
        return bidRepository.findFirstByLotIdIsOrderByBidDate(id)
                .map(bidMapper::toBidder)
                .orElseThrow(
                        () -> new IdNotFoundException("Ставка с lot_id="+id+" не найдена.")
                );
    }

    @Override
    public Bidder getFrequentBidder(Integer id) {
        return bidRepository.findFrequentBidder(id)
                .orElseThrow(
                        () -> new IdNotFoundException("Ставка с lot_id="+id+" не найдена.")
                );
    }

    @Override
    public FullLot getFullLotInfo(Integer id) {
        FullLot fullLot = lotRepository.findById(id)
                .map(lotMapper::toFullLot)
                .orElseThrow(
                        () -> new IdNotFoundException("Лот с id="+id+" не найден.")
                );
        return fullLot;
    }

    @Override
    public void startLotTrading(Integer id) {
        LotEntity existedLot = lotRepository.findById(id)
                .orElseThrow(
                        () -> new IdNotFoundException("Лот с id="+id+" не найден.")
                );

        if(existedLot.getStatus() == Status.CREATED){
            lotRepository.setLotStatus(id, Status.STARTED);
        } else if (existedLot.getStatus() == Status.STOPPED) {
            throw new WrongParamsException("Торги по лоту уже завершены.");
        }
    }

    @Override
    public void createBid(Integer id, String name) {
        LotEntity existedLot = lotRepository.findById(id)
                .orElseThrow(
                        () -> new IdNotFoundException("Лот с id="+id+" не найден.")
                );

        if(existedLot.getStatus() == Status.STARTED){
            OffsetDateTime currentTime = OffsetDateTime.now();
            BidEntity bid = new BidEntity();

            bid.setBidderName(name);
            bid.setLotId(id);
            bid.setBidDate(currentTime);
            bidRepository.save(bid);
        }else{
            throw new WrongParamsException("Торги по лоту ещё не начаты.");
        }
    }

    @Override
    public void stopLotTrading(Integer id) {
        LotEntity existedLot = lotRepository.findById(id)
                .orElseThrow(
                        () -> new IdNotFoundException("Лот с id="+id+" не найден.")
                );

        if(existedLot.getStatus() == Status.STARTED){
            lotRepository.setLotStatus(id, Status.STOPPED);
        }else if(existedLot.getStatus() == Status.CREATED){
            throw new WrongParamsException("Торги по лоту ещё не начаты.");
        }
    }

    @Override
    public Lot createLot(CreateLot createLot) {
        if(createLot.getStartPrice() < 1 || createLot.getBidPrice() < 1){
            throw new WrongParamsException("Стартовая цена и шаг ставки не могут быть меньше 1.");
        }
        LotEntity lotEntity = lotMapper.toEntity(createLot);
        lotRepository.save(lotEntity);
        return lotMapper.toLot(lotEntity);
    }

    @Override
    public List<Lot> getLotsList(Status status, Integer page) {
        int unitsPerPage = 10;

        if(page == null || page < 0){
            page = 0;
        }
        Pageable lotsOfConcretePage = PageRequest.of(page,unitsPerPage);
        Page<Lot> lotPage = lotPagingRepository.findLotEntitiesByStatus(lotsOfConcretePage,status).map(lotMapper::toLot);
        return lotPage.toList();
    }

    @Override
    public ResponseEntity<Resource> getLotExport() {
        String fileName = "lots_export.csv";

        List<LotEntity> lotEntities = new ArrayList<>();
        lotRepository.findAll().forEach(lotEntities::add);

        ByteArrayInputStream in;
        CSVFormat format = CSVFormat.EXCEL.builder()
                .setHeader("id","Title","Status","Last bidder","Current price").build();
        try(
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                CSVPrinter printer = new CSVPrinter(new PrintWriter(outputStream), format)
        ){
            for(LotEntity lotEntity : lotEntities){
                List<String> data = Arrays.asList(
                        String.valueOf(lotEntity.getId()),
                        lotEntity.getTitle(),
                        lotEntity.getStatus().name(),
                        lotEntity.getBids().stream().max(Comparator.comparing(BidEntity::getBidDate)).orElse(new BidEntity()).getBidderName(),
                        String.valueOf(
                                lotEntity.getBids().size()*lotEntity.getBidPrice()+lotEntity.getStartPrice()
                        )
                );
                printer.printRecord(data);
            }

            printer.flush();
            in = new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Resource resource = new InputStreamResource(in);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+fileName+"\"")
                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.parseMediaType("application/csv")))
                .body(resource);
    }
}
