package com.patitosoft.repository;

import java.time.LocalDateTime;
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

    @Transactional
    @Modifying
    @Query(value = "UPDATE Employee e SET e.deleteFlg = :deleted, updatedOn = :updated WHERE e.email = :email")
    void fireOrHireEmployee(@Param("email") String email, @Param("deleted") Boolean deleted, @Param("updated") LocalDateTime updated);

}
