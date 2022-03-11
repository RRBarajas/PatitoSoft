package com.patitosoft.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.patitosoft.dto.EmploymentDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.entity.Position;
import com.patitosoft.repository.EmploymentHistoryRepository;
import com.patitosoft.repository.PositionRepository;
import com.patitosoft.service.exception.InvalidPositionException;
import com.patitosoft.service.exception.PositionAlreadyExistsException;
import com.patitosoft.service.exception.PositionNotFoundException;
import com.patitosoft.service.mapper.PositionMapper;
import com.patitosoft.service.utils.EmployeeUtils;
import com.patitosoft.service.utils.PositionUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private EmploymentHistoryRepository historyRepository;

    @Spy
    private PositionMapper mapper = PositionMapper.INSTANCE;

    @InjectMocks
    private PositionService positionService;

    @Test
    void getPosition_ThrowException_IfNotFound() {
        when(positionRepository.findById(anyLong())).thenReturn(Optional.empty());

        PositionNotFoundException exception = assertThrows(
            PositionNotFoundException.class,
            () -> positionService.getPosition(1L)
        );
        assertEquals("Position '1' does not exist", exception.getMessage());
    }

    @Test
    void getPosition_ReturnDTO_IfValidPosition() {
        Optional<Position> position = Optional.of(PositionUtils.getPosition());
        when(positionRepository.findById(anyLong())).thenReturn(position);

        PositionDTO positionDTO = positionService.getPosition(1L);

        assertNotNull(positionDTO);
        assertEquals(1L, positionDTO.getPositionId());
        assertEquals("Position", positionDTO.getPositionName());
    }

    @Test
    void createPosition_ThrowException_IfPositionExists() {
        PositionDTO positionDTO = PositionUtils.getPositionDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(true);

        PositionAlreadyExistsException exception = assertThrows(
            PositionAlreadyExistsException.class,
            () -> positionService.createPosition(positionDTO)
        );
        assertEquals("Position '1' already exists", exception.getMessage());
    }

    @Test
    void createPosition_ReturnDTO_IfValidPosition() {
        PositionDTO positionDTO = PositionUtils.getPositionDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(false);
        when(positionRepository.save(any())).thenAnswer(a -> a.getArguments()[0]);

        PositionDTO newPosition = positionService.createPosition(positionDTO);

        assertNotNull(newPosition);
        assertEquals(positionDTO.getPositionId(), newPosition.getPositionId());
        assertEquals(positionDTO.getPositionName(), newPosition.getPositionName());
    }

    @Test
    void updatePosition_ThrowException_IfNotFound() {
        PositionDTO positionDTO = PositionUtils.getPositionDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(false);

        PositionNotFoundException exception = assertThrows(
            PositionNotFoundException.class,
            () -> positionService.updatePosition(1L, positionDTO)
        );
        assertEquals("Position '1' does not exist", exception.getMessage());
    }

    @Test
    void updatePosition_ThrowException_IfPositionsDiffer() {
        PositionDTO positionDTO = PositionUtils.getPositionDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(true);

        InvalidPositionException exception = assertThrows(
            InvalidPositionException.class,
            () -> positionService.updatePosition(2L, positionDTO)
        );
        assertEquals("Position must be the same in the passed parameter and the object", exception.getMessage());
    }

    @Test
    void updatePosition_ReturnDTO_IfValidPosition() {
        PositionDTO positionDTO = PositionUtils.getPositionDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(true);
        when(positionRepository.save(any())).thenAnswer(a -> a.getArguments()[0]);

        PositionDTO newPosition = positionService.updatePosition(1L, positionDTO);

        assertNotNull(newPosition);
        assertEquals(positionDTO.getPositionId(), newPosition.getPositionId());
        assertEquals(positionDTO.getPositionName(), newPosition.getPositionName());
    }

    @Test
    void deletePosition_ThrowException_IfNotFound() {
        when(positionRepository.existsById(anyLong())).thenReturn(false);

        PositionNotFoundException exception = assertThrows(
            PositionNotFoundException.class,
            () -> positionService.deletePosition(1L)
        );
        assertEquals("Position '1' does not exist", exception.getMessage());
    }

    @Test
    void assignEmployeePosition_ThrowException_IfPositionDoesNotExist() {
        EmploymentDTO employmentDTO = PositionUtils.getEmploymentDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(false);

        PositionNotFoundException exception = assertThrows(
            PositionNotFoundException.class,
            () -> positionService.assignEmployeePosition("name@email.com", employmentDTO)
        );
        assertEquals("Position '1' does not exist", exception.getMessage());
    }

    @Test
    void assignEmployeePosition_SaveTwice_IfValidPosition() {
        Optional<EmploymentHistory> employmentHistory = Optional.of(EmployeeUtils.getEmploymentHistory());
        EmploymentDTO employmentDTO = PositionUtils.getEmploymentDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(true);
        when(historyRepository.findByEmployeeEmailAndCurrentTrue(any())).thenReturn(employmentHistory);

        positionService.assignEmployeePosition("name@email.com", employmentDTO);

        verify(positionRepository, times(1)).existsById(anyLong());
        verify(historyRepository, times(1)).save(any());
        verify(historyRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void assignEmployeePosition_SaveOnce_IfNoCurrentPosition() {
        EmploymentDTO employmentDTO = PositionUtils.getEmploymentDTO();
        when(positionRepository.existsById(anyLong())).thenReturn(true);
        when(historyRepository.findByEmployeeEmailAndCurrentTrue(any())).thenReturn(Optional.empty());

        positionService.assignEmployeePosition("name@email.com", employmentDTO);

        verify(positionRepository, times(1)).existsById(anyLong());
        verify(historyRepository, times(1)).saveAndFlush(any());
    }
}