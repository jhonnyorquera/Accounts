package com.banco.cuenta.services;

import com.banco.cuenta.dto.MovementDto;

public interface MovementService {

  String saveMovement(MovementDto movementDto);

}
