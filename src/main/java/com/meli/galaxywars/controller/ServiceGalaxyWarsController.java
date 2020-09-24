package com.meli.galaxywars.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.galaxywars.models.RequestDto;
import com.meli.galaxywars.models.ResponseDto;
import com.meli.galaxywars.models.SatelliteDto;
import com.meli.galaxywars.service.ServiceGalaxyWarsService;

@RestController
@RequestMapping("/service")
public class ServiceGalaxyWarsController {
	@Autowired
	ServiceGalaxyWarsService serviceGalaxyWarsService;
	
	@PostMapping("/topsecret")
	public ResponseDto topsecret(@RequestBody @Valid RequestDto requestDto) {
		ResponseDto responseDto=serviceGalaxyWarsService.topsecret(requestDto);
		return responseDto;		
	}
	
	@PostMapping(value = "/topsecret_split/{satelliteName}")
	public ResponseDto getTopsecretSplit(@RequestBody @Valid SatelliteDto satelliteDto, @PathVariable String satelliteName) {
		ResponseDto responseDto=serviceGalaxyWarsService.topsecretSplit(satelliteDto,satelliteName);
		return responseDto;		
	}
}
