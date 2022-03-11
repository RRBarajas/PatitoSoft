package com.patitosoft.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patitosoft.api.PositionApi;
import com.patitosoft.dto.EmploymentDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;
import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.entity.Position;
import com.patitosoft.projections.SalariesPerPosition;
import com.patitosoft.repository.EmploymentHistoryRepository;
import com.patitosoft.repository.PositionRepository;
import com.patitosoft.service.exception.InvalidPositionException;
import com.patitosoft.service.exception.PositionAlreadyExistsException;
import com.patitosoft.service.exception.PositionAlreadyMappedException;
import com.patitosoft.service.exception.PositionNotFoundException;
import com.patitosoft.service.mapper.PositionMapper;

@Service
public class PositionService implements PositionApi {

    private final PositionRepository repository;

    private final EmploymentHistoryRepository historyRepository;

    private final PositionMapper mapper;

    public PositionService(PositionRepository repository,
        EmploymentHistoryRepository historyRepository,
        PositionMapper mapper) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.mapper = mapper;
    }

    @Override
    public PositionDTO getPosition(Long id) {
        Position position = repository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));
        return mapper.positionToPositionDTO(position);
    }

    @Override
    public List<PositionDTO> getAllPositions() {
        List<Position> allPositions = repository.findAll();
        return mapper.positionsToPositionDTOs(allPositions);
    }

    @Override
    public PositionDTO createPosition(PositionDTO positionDTO) {
        if (repository.existsById(positionDTO.getPositionId())) {
            throw new PositionAlreadyExistsException(positionDTO.getPositionId());
        }
        Position position = mapper.positionDTOToPosition(positionDTO);
        return mapper.positionToPositionDTO(repository.save(position));
    }

    @Override
    public PositionDTO updatePosition(Long id, PositionDTO positionDTO) {
        if (!repository.existsById(id)) {
            throw new PositionNotFoundException(id);
        }
        validatePositionId(id, positionDTO);
        Position position = mapper.positionDTOToPosition(positionDTO);
        return mapper.positionToPositionDTO(repository.save(position));
    }

    @Override
    public void deletePosition(Long id) {
        if (!repository.existsById(id)) {
            throw new PositionNotFoundException(id);
        } else if (historyRepository.existsByPositionId(id)) {
            throw new PositionAlreadyMappedException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<PositionSalaryRangesDTO> getSalaryRangesPerPosition() {
        List<SalariesPerPosition> salaryRangesByPosition = historyRepository.findSalaryRangesByPosition();
        return mapper.salariesPerPositionToDTO(salaryRangesByPosition);
    }

    @Transactional
    public void assignEmployeePosition(String email, EmploymentDTO employmentDTO) {
        if (!repository.existsById(employmentDTO.getPositionId())) {
            throw new PositionNotFoundException(employmentDTO.getPositionId());
        }

        Optional<EmploymentHistory> currentPosition = historyRepository.findByEmployeeEmailAndCurrentTrue(email.toLowerCase());
        if (employmentDTO.isCurrentPosition() && currentPosition.isPresent()) {
            EmploymentHistory oldPosition = currentPosition.get();
            oldPosition.setCurrent(Boolean.FALSE);
            oldPosition.setTo(LocalDateTime.now());
            historyRepository.save(oldPosition);
            employmentDTO.setTo(null);
        }
        EmploymentHistory entity = mapper.employmentDTOToEmploymentHistory(employmentDTO);
        entity.setEmployeeEmail(email.toLowerCase());
        historyRepository.saveAndFlush(entity);
    }

    private void validatePositionId(Long position, PositionDTO positionDTO) {
        if (!position.equals(positionDTO.getPositionId())) {
            throw new InvalidPositionException();
        }
    }
}
