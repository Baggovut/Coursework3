package ru.skypro.lessons.springboot.auctionsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bidder {
    private String bidderName;
    private OffsetDateTime bidDate;
}
