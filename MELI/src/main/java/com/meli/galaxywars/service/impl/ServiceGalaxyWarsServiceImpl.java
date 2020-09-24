package com.meli.galaxywars.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.meli.galaxywars.models.PositionDto;
import com.meli.galaxywars.models.RequestDto;
import com.meli.galaxywars.models.ResponseDto;
import com.meli.galaxywars.models.SatelliteDto;
import com.meli.galaxywars.service.ServiceGalaxyWarsService;

@Service
public class ServiceGalaxyWarsServiceImpl implements ServiceGalaxyWarsService {
	@SuppressWarnings("serial")
	static Map<String, PositionDto> MAP_SATELITES = new HashMap<String, PositionDto>() {
		{
			put("Kenobi", new PositionDto(-500, -200));
			put("Skywalker", new PositionDto(100, -100));
			put("Sato", new PositionDto(500, 100));
		}
	};

	@Override
	public ResponseDto topsecret(@Valid RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		double[] positions = this.getLocation(requestDto.getSatellites()[0].getDistance(),
				requestDto.getSatellites()[1].getDistance(), requestDto.getSatellites()[2].getDistance());
		responseDto.setPosition(new PositionDto(positions[0], positions[1]));
		String message = this.getMessage(requestDto.getSatellites()[0].getMessage(),
				requestDto.getSatellites()[1].getMessage(), requestDto.getSatellites()[2].getMessage());
		responseDto.setMessage(message);
		if (responseDto.getMessage() == null || responseDto.getPosition() == null)
			responseDto.setRESPONSE_CODE("404");
		return responseDto;
	}

	@Override
	public ResponseDto topsecretSplit(@Valid SatelliteDto satelliteDto, String satelliteName) {
		ResponseDto responseDto = new ResponseDto();
		double[] positions;
		if (satelliteName.equals("Kenobi")) {
			positions = this.getLocation(satelliteDto.getDistance(), 0, 0);
		} else if (satelliteName.equals("Skywalker")) {
			positions = this.getLocation(0, satelliteDto.getDistance(), 0);
		} else if (satelliteName.equals("Sato")) {
			positions = this.getLocation(0, 0, satelliteDto.getDistance());
		}
		else {
			responseDto.setRESPONSE_CODE("404");
			return responseDto;
		}

		responseDto.setPosition(new PositionDto(positions[0], positions[1]));
		String message = this.getMessage(satelliteDto.getMessage(), null, null);
		responseDto.setMessage(message);
		if (responseDto.getMessage() == null || responseDto.getPosition() == null)
			responseDto.setRESPONSE_CODE("404");
		return responseDto;
	}

	private String getMessage(String[] message1, String[] message2, String[] message3) {
		String valor = "";
		try {
			for (int i = 0; i < message1.length; i++) {
				if (!message1[i].equals("")) {
					valor += message1[i];
				} else if (message2!=null && !message2[i].equals("")) {
					valor += message2[i];
				} else if (message3!=null && !message3[i].equals("")) {
					valor += message3[i];
				}
				valor += " ";
			}
		} catch (Exception e) {
			System.out.println("Error en el mensaje!!");
			valor = null;
		}
		return valor;
	}

	private double[] getLocation(double... distances) {
		double[] centroid = null;
		try {
			double[][] positions = new double[3][2];
			double[] distancia = new double[3]; 
			int i = 0;
			for (Map.Entry<String, PositionDto> entry : MAP_SATELITES.entrySet()) {
				if(distances[i]!=0) {
					positions[i][0] = entry.getValue().getX();
					positions[i][1] = entry.getValue().getY();
					distancia[i]=distances[i];
				}
				i++;
			}
			i = 0;
			NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
					new TrilaterationFunction(positions, distancia), new LevenbergMarquardtOptimizer());
			Optimum optimum = solver.solve();
			centroid = optimum.getPoint().toArray();
		} catch (Exception e) {
			System.out.println("Error en la localización!!");
			centroid = null;
		}
		return centroid;
	}

}
