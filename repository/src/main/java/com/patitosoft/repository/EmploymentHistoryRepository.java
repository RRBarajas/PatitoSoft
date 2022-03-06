package com.patitosoft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.entity.EmploymentHistoryKey;

@Repository
public interface EmploymentHistoryRepository extends JpaRepository<EmploymentHistory, EmploymentHistoryKey> {

    Optional<EmploymentHistory> findByEmployeeEmailAndCurrentTrue(String email);
}
