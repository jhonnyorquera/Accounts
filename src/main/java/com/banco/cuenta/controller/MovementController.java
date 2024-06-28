package com.banco.cuenta.controller;

import com.banco.cuenta.dto.MovementDto;
import com.banco.cuenta.dto.ReportMovementDto;
import com.banco.cuenta.services.ReportService;
import com.banco.cuenta.services.MovementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movement")
@AllArgsConstructor
public class MovementController {

  private MovementService movementService;
  private ReportService reportService;

  @PutMapping
  public void saveMovement(@RequestBody MovementDto movementDto) {
    if (movementDto.getAccountNumber().equals("") || movementDto.getAccountNumber() == null) {
      throw new RuntimeException("Account Number is required");
    }
    movementService.saveMovement(movementDto);
  }

  @GetMapping("/report")
  public ReportMovementDto getReport(@RequestParam String identity, @RequestParam String dateBegin, String dateEnd) {
    return reportService.getReportMovements(identity, dateBegin, dateEnd);
  }


}