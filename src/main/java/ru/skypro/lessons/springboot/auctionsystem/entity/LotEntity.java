package ru.skypro.lessons.springboot.auctionsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;

import java.util.List;

@Data
@Entity
@Table(name = "lot")
public class LotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @Column(name = "title", nullable = false)
    private String title;
    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;
    @Column(name = "start_price", nullable = false)
    private Integer startPrice;
    @Column(name = "bid_price", nullable = false)
    private Integer bidPrice;

    @OneToMany
    @JoinColumn(name = "lot_id",referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    private List<BidEntity> bids;
}
