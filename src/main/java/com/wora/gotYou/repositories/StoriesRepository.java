package com.wora.gotYou.repositories;

import com.wora.gotYou.entities.Stories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoriesRepository extends JpaRepository<Stories, Integer> {
}
