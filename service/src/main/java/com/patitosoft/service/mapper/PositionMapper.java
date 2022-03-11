
package com.patitosoft.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;
import com.patitosoft.entity.Position;
import com.patitosoft.projections.SalariesPerPosition;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    PositionDTO positionToPositionDTO(Position position);

    List<PositionDTO> positionsToPositionDTOs(List<Position> position);

    Position positionDTOToPosition(PositionDTO positionDTO);

    default List<PositionSalaryRangesDTO> salariesPerPositionToDTO(List<SalariesPerPosition> salariesPerPosition) {
        if (salariesPerPosition == null) {
            return null;
        }
        List<PositionSalaryRangesDTO> positionSalaryRangesDTO = new ArrayList<>();

        Map<String, Map<Double, Long>> activePositions = salariesPerPosition.stream()
            .filter(p -> nonNull(p.getCurrent()) && p.getCurrent()).collect(
                groupingBy(SalariesPerPosition::getPosition,
                    groupingBy(SalariesPerPosition::getSalary, counting())));
        activePositions.forEach((k, v) -> positionSalaryRangesDTO.add(new PositionSalaryRangesDTO(k, v)));

        Stream<String> inactivePositions = salariesPerPosition.stream()
            .map(SalariesPerPosition::getPosition)
            .filter(position -> !activePositions.containsKey(position)).distinct();
        inactivePositions.forEach(p -> positionSalaryRangesDTO.add(new PositionSalaryRangesDTO(p, null)));

        return positionSalaryRangesDTO;
    }
}
