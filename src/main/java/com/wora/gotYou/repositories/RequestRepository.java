package com.wora.gotYou.repositories;

import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE LOWER(r.title) LIKE LOWER(concat('%', :keyword, '%')) OR LOWER(r.description) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Request> searchByKeyword(@Param("keyword") String keyword);
}
