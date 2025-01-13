package com.wora.gotYou.repositories;

import com.wora.gotYou.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
