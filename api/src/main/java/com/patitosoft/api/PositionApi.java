package com.patitosoft.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;

@FeignClient(path = "positions", name = "${feign.position-api.name}", url = "${feign.position-api.url}")
public interface PositionApi {

    @GetMapping(value = "/{id}")
    PositionDTO getPosition(@PathVariable("id") Long id);

    @GetMapping(value = "/")
    List<PositionDTO> getAllPositions();

    @PostMapping
    PositionDTO createPosition(@RequestBody PositionDTO positionDTO);

    @PatchMapping("/{id}")
    PositionDTO updatePosition(@PathVariable("id") Long id,
        @RequestBody PositionDTO positionDTO);

    @DeleteMapping("/{id}")
    void deletePosition(@PathVariable("id") Long id);

    @GetMapping(value = "/salaries")
    List<PositionSalaryRangesDTO> getSalaryRangesPerPosition();
}
