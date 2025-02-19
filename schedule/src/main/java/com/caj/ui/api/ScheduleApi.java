package com.caj.ui.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caj.domain.schedule.ScheduleService;
import com.caj.ui.dto.GenericResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.micrometer.tracing.Tracer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1")
public class ScheduleApi {

	/**
	 * sheduleService
	 */
	@Autowired
	ScheduleService sheduleService;
	
	@Autowired
	Tracer tracer;
	
	/**
	 * 訂位 API
	 * 
	 * @param reservationRequestDTO
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Operation(summary = "查詢時刻表", description = "查詢時刻表")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "操作成功"),
			@ApiResponse(responseCode = "500", description = "操作失敗") })
	@GetMapping(value = "/schedules", produces = "application/json")
	public ResponseEntity<GenericResponseDTO> reservation()
			throws JsonMappingException, JsonProcessingException {
		log.info("GET /schedules");
		return new ResponseEntity<>(
				GenericResponseDTO.builder()
				    .data(sheduleService.getScheduleDoList())
					.code(String.valueOf(HttpStatus.OK.value()))
					.message(HttpStatus.OK.name())
					.traceId(tracer.currentSpan().context().traceId())
					.build(), HttpStatus.OK);
	}
}
