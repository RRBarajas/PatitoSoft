package com.patitosoft.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.patitosoft.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmailAndDeleteFlgFalse(String email);

    @Query(value = "SELECT e FROM Employee e LEFT OUTER JOIN e.position p "
        + "WHERE lower(e.firstName) LIKE concat('%', lower(:firstName), '%') "
        + "AND lower(e.lastName) LIKE concat('%', lower(:lastName), '%') "
        + "AND (:position IS NULL OR lower(p.positionName) = lower(:position))")
    List<Employee> findByNameAndPosition(@Param("firstName") String firstName, @Param("lastName") String lastName,
        @Param("position") String position);

    List<Employee> findByBirthDateBetween(LocalDate today, LocalDate nextWeek);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Employee e SET e.deleteFlg = :deleted, updatedOn = :updated WHERE e.email = :email")
    void fireOrHireEmployee(@Param("email") String email, @Param("deleted") Boolean deleted, @Param("updated") LocalDateTime updated);

}
