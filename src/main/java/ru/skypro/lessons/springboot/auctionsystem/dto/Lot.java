package ru.skypro.lessons.springboot.auctionsystem.dto;

import lombok.Data;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;

@Data
public class Lot {
    private Integer id;
    private Status status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
}
