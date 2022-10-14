package com.deyaniragenao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

}