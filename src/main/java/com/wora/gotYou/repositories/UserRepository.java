package com.wora.gotYou.repositories;

import com.wora.gotYou.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
