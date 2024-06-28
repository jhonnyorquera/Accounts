package com.banco.cuenta.services;

import com.banco.cuenta.dto.ReportMovementDto;

public interface ReportService {


  ReportMovementDto getReportMovements(String identity, String dateBegin, String dateEnd);
}
