package com.meli.galaxywars.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SatelliteDto {
	private String name;
	private double distance;
	private String[] message;	 
}
