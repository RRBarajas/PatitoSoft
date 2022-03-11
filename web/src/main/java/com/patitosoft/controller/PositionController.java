package com.patitosoft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patitosoft.api.PositionApi;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;

@RestController
@RequestMapping("positions")
public class PositionController {

    private final PositionApi positionApi;

    public PositionController(PositionApi positionApi) {
        this.positionApi = positionApi;
    }

    @GetMapping("/{id}")
    public PositionDTO getPosition(@PathVariable Long id) {
        return positionApi.getPosition(id);
    }

    @GetMapping
    public List<PositionDTO> getAllPositions() {
        return positionApi.getAllPositions();
    }

    @PostMapping
    public PositionDTO createPosition(@RequestBody @Valid PositionDTO positionDTO) {
        return positionApi.createPosition(positionDTO);
    }

    @PatchMapping("/{id}")
    public PositionDTO updatePosition(@PathVariable("id") Long id,
        @RequestBody @Valid PositionDTO positionDTO) {
        return positionApi.updatePosition(id, positionDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePosition(@PathVariable("id") Long id) {
        positionApi.deletePosition(id);
    }

    @GetMapping(value = "/salaries")
    public List<PositionSalaryRangesDTO> getSalaryRangesPerPosition() {
        return positionApi.getSalaryRangesPerPosition();
    }
}
