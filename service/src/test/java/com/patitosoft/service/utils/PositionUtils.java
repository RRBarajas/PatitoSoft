package com.patitosoft.service.utils;

import java.util.ArrayList;
import java.util.List;

import com.patitosoft.dto.PositionDTO;
import com.patitosoft.entity.Position;
import com.patitosoft.projections.SalariesPerPosition;

public class PositionUtils {

    public static Position getPosition() {
        Position position = new Position();
        position.setPositionId(1L);
        position.setPositionName("Position");
        return position;
    }

    public static List<Position> getPositionsList() {
        List<Position> positions = new ArrayList<>();
        positions.add(getPosition());
        positions.add(null);
        return positions;
    }

    public static PositionDTO getPositionDTO() {
        PositionDTO position = new PositionDTO();
        position.setPositionId(1L);
        position.setPositionName("Position");
        return position;
    }

    public static List<SalariesPerPosition> getSalariesPerPosition() {
        SalariesPerPosition employee1 = new SalariesPerPositionImpl("Developer", 10.5, "name@email.com", Boolean.TRUE);
        SalariesPerPosition employee2 = new SalariesPerPositionImpl("Success Coach", 204D, "second@email.com", Boolean.TRUE);
        SalariesPerPosition employee3 = new SalariesPerPositionImpl("Success Coach", 204.0, "third@email.com", Boolean.TRUE);
        SalariesPerPosition employee4 = new SalariesPerPositionImpl("Tester", 1234.56, "fourth@email.com", Boolean.FALSE);
        return List.of(employee1, employee2, employee3, employee4);
    }
}
