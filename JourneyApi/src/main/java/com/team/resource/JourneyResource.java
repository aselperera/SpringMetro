package com.team.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.team.entity.Journey;
import com.team.service.JourneyService;

@RestController
public class JourneyResource {
	@Autowired
	private JourneyService journeyService;
	
	@GetMapping(path = "/journeys/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Journey getJourneyByIdResource(@PathVariable("id") int id) {
		return journeyService.getJourneyById(id);
	}
	
	@PostMapping(path = "/journeys", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean saveJourneyResource(@RequestBody Journey journey) {
		return journeyService.saveJourney(journey);
	}
	
	@PostMapping(path = "/journeys/start/{userId}/{startStationId}")
	public Journey startJourneyResource(@PathVariable("userId") int userId, @PathVariable("startStationId") int startStationId) {
		return journeyService.startJourney(userId, startStationId);
	}
	
	@PutMapping(path = "/journeys/update/{userId}/{endStationId}")
	public Journey updateJourneyResource(@PathVariable("userId") int userId, @PathVariable("endStationId") int endStationId) {
		return journeyService.updateJourney(userId, endStationId);
	}
}
