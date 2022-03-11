package com.patitosoft.service.mapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;
import com.patitosoft.entity.Position;
import com.patitosoft.projections.SalariesPerPosition;
import com.patitosoft.service.utils.PositionUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PositionMapperTest {

    PositionMapper mapper = PositionMapper.INSTANCE;

    @Test
    void positionToPositionDTO_ReturnDTO_IfValidPosition() {
        Position position = PositionUtils.getPosition();

        PositionDTO positionDTO = mapper.positionToPositionDTO(position);

        assertNotNull(positionDTO);
        assertEquals(position.getPositionId(), positionDTO.getPositionId());
        assertEquals(position.getPositionName(), positionDTO.getPositionName());
    }

    @Test
    void positionToPositionDTO_ReturnListWithNull_IfContainsNullPosition() {
        List<Position> positionsList = PositionUtils.getPositionsList();

        List<PositionDTO> positionDTOS = mapper.positionsToPositionDTOs(positionsList);

        assertNotNull(positionDTOS);
        assertEquals(positionDTOS.size(), positionDTOS.size());
        assertNotNull(positionDTOS.get(0));
        assertNull(positionDTOS.get(1));
    }

    @Test
    void positionDTOToPosition_ReturnPosition_IfValidDTO() {
        PositionDTO positionDTO = PositionUtils.getPositionDTO();

        Position position = mapper.positionDTOToPosition(positionDTO);

        assertNotNull(position);
        assertEquals(positionDTO.getPositionId(), position.getPositionId());
        assertEquals(positionDTO.getPositionName(), position.getPositionName());
    }

    @Test
    void salariesPerPositionToDTO_ReturnEmptyList_IfNoPositions() {
        List<PositionSalaryRangesDTO> positions = mapper.salariesPerPositionToDTO(Collections.emptyList());

        assertNotNull(positions);
        assertEquals(0, positions.size());
    }

    @Test
    void salariesPerPositionToDTO_ReturnList_IfPositionWithOrWithoutEmployees() {
        List<SalariesPerPosition> salariesPerPosition = PositionUtils.getSalariesPerPosition();

        List<PositionSalaryRangesDTO> positions = mapper.salariesPerPositionToDTO(salariesPerPosition);
        // We sort it to make assertions by index, since the current mapper does not have sorting logic
        positions.sort(Comparator.comparing(PositionSalaryRangesDTO::getPosition));

        assertNotNull(positions);
        assertEquals(3, positions.size());
        assertEquals(1, positions.get(0).getSalaries().size());
        assertEquals(2L, positions.get(1).getSalaries().get(204.0));

        assertEquals("Tester", positions.get(2).getPosition());
        assertNull(positions.get(2).getSalaries());
    }

}