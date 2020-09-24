package com.meli.galaxywars.service;

import javax.validation.Valid;

import com.meli.galaxywars.models.RequestDto;
import com.meli.galaxywars.models.ResponseDto;
import com.meli.galaxywars.models.SatelliteDto;

public interface ServiceGalaxyWarsService {
	ResponseDto topsecret(@Valid RequestDto requestDto);

	ResponseDto topsecretSplit(@Valid SatelliteDto satelliteDto, String satelliteName);	
}
