package com.wora.gotYou.repositories;

import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
