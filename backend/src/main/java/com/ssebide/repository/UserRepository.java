package com.ssebide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
