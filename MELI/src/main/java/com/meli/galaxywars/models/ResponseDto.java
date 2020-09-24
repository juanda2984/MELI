package com.meli.galaxywars.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {
	private PositionDto position;
	private String message;
	private String RESPONSE_CODE;
}
