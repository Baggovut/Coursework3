package ru.skypro.lessons.springboot.auctionsystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class BidderNameBidDatePK implements Serializable {
    private String bidderName;
    private OffsetDateTime bidDate;
}
