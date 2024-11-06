package com.exemple.demo.exception;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String login);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
