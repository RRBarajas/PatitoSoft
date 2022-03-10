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
import com.patitosoft.projections.EmployeeForTotals;
import com.patitosoft.projections.EmployeesBirthdays;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    boolean existsByEmailAndDeleteFlg(String email, Boolean deleteFlg);

    Optional<Employee> findByEmailAndDeleteFlgFalse(String email);

    @Query(value = "SELECT e FROM Employee e "
        + "LEFT OUTER JOIN e.employmentHistory ep "
        + "LEFT OUTER JOIN ep.position p "
        + "WHERE lower(e.firstName) LIKE concat('%', lower(:firstName), '%') "
        + "AND lower(e.lastName) LIKE concat('%', lower(:lastName), '%') "
        + "AND (:position IS NULL OR lower(p.positionName) = lower(:position)) "
        + "AND (ep.current IS NULL or ep.current = true)")
    List<Employee> findByNameAndPosition(@Param("firstName") String firstName, @Param("lastName") String lastName,
        @Param("position") String position);

    @Query(value = "SELECT e.email as email, "
        + "concat(e.firstName, ' ', e.lastName) as name, "
        + "e.birthDate as birthDate "
        + "FROM Employee e "
        + "WHERE e.deleteFlg = false "
        + "AND e.birthDate between :today AND :nextWeek")
    List<EmployeesBirthdays> findByBirthDateBetween(@Param("today") LocalDate today, @Param("nextWeek") LocalDate nextWeek);

    @Query(value = "SELECT e.email as email, e.gender as gender, "
        + "p.positionName as position, "
        + "a.countryName as country, a.stateName as state, "
        + "ep.current as current "
        + "FROM Employee e "
        + "LEFT OUTER JOIN e.address a "
        + "LEFT OUTER JOIN e.employmentHistory ep "
        + "LEFT OUTER JOIN ep.position p "
        + "WHERE e.deleteFlg = false")
    List<EmployeeForTotals> findEmployeesForTotals();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Employee e SET e.deleteFlg = :deleted, updatedOn = :updated WHERE e.email = :email")
    void fireOrHireEmployee(@Param("email") String email, @Param("deleted") Boolean deleted, @Param("updated") LocalDateTime updated);

}
