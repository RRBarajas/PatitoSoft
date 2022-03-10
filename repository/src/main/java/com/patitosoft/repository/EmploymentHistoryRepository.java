package com.patitosoft.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.entity.EmploymentHistoryKey;
import com.patitosoft.projections.SalariesPerPosition;

@Repository
public interface EmploymentHistoryRepository extends JpaRepository<EmploymentHistory, EmploymentHistoryKey> {

    Optional<EmploymentHistory> findByEmployeeEmailAndCurrentTrue(String email);

    @Query(value = "SELECT p.positionName as position, ep.salary as salary, "
        + "e.email as email, ep.current as current "
        + "FROM EmploymentHistory ep "
        + "RIGHT OUTER JOIN ep.position p "
        + "LEFT OUTER JOIN ep.employee e "
        + "WHERE (e.deleteFlg IS NULL OR e.deleteFlg = false)")
    List<SalariesPerPosition> findSalaryRangesByPosition();
}
