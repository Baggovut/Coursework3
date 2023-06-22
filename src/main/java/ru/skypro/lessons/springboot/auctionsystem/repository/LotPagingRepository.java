package ru.skypro.lessons.springboot.auctionsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.auctionsystem.entity.LotEntity;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;

import org.springframework.data.domain.Pageable;

public interface LotPagingRepository extends PagingAndSortingRepository<LotEntity,Integer> {
    @Transactional
    Page<LotEntity> findLotEntitiesByStatus(Pageable pageable, Status status);
}
