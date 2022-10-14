package com.deyaniragenao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Discretionary;

@Repository
public interface DiscretionaryRepository extends JpaRepository<Discretionary, Long>{

}
