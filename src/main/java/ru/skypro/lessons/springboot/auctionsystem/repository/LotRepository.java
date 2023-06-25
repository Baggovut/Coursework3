package ru.skypro.lessons.springboot.auctionsystem.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.auctionsystem.entity.LotEntity;
import ru.skypro.lessons.springboot.auctionsystem.entity.enums.Status;

public interface LotRepository extends CrudRepository<LotEntity,Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE LotEntity lot SET lot.status = :status WHERE lot.id = :id")
    void setLotStatus(@Param("id") Integer id, @Param("status") Status status);
}
