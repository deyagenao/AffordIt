package com.deyaniragenao.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long>{

	@Query("SELECT i FROM Income i WHERE i.account.id =?1")
	Set<Income> findIncomesByAccountId(String id);

	@Query("SELECT SUM(i.monthlyAmount) FROM Income i "
			+ "GROUP BY i.account "
			+ "HAVING i.account =?1")
	BigDecimal findIncomeTotalByAccountId(String id);
}
