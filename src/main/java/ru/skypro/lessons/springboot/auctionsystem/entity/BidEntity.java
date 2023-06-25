package ru.skypro.lessons.springboot.auctionsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "bid")
@IdClass(BidderNameBidDatePK.class)
public class BidEntity {
    @Id
    @Column(name = "bidder_name")
    private String bidderName;
    @Id
    @CreationTimestamp
    @Column(name = "bid_date")
    private OffsetDateTime bidDate;
    @Column(name = "lot_id")
    private Integer lotId;
}
