package ru.skypro.lessons.springboot.auctionsystem.dto;

import lombok.Data;

@Data
public class CreateLot {
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
}
