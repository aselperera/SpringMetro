//package com.team.service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.team.entity.Journey;
//import com.team.entity.User;
//import com.team.persistence.JourneyDao;
//
//@Service
//public class JourneyServiceImpl implements JourneyService {
//	@Autowired
//	JourneyDao journeyDao;
//	
//	
//	/*
//	@Override
//	public Journey getJourneyById(int userId) {
//		User user = restTemplate.getForObject("http://localhost:8001/users/"+userId, User.class);	
//		Journey journey = new
//	*/
//
//	@Override
//	public boolean saveJourney(Journey journey) {
//		Journey savedJourney = journeyDao.save(journey);
//		if(savedJourney != null) {
//			return true;
//		}
//		return false;
//	}
//	
//	public LocalDateTime getCurrentTime() {
//		LocalDateTime currentTime = LocalDateTime.now();
//		return currentTime;
//	}
//
//	@Override
//	public Journey getJourneyById(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//}

package com.team.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.entity.Journey;

import com.team.persistence.JourneyDao;

@Service
public class JourneyServiceImpl implements JourneyService {
	@Autowired
	JourneyDao journeyDao;

	@Override
	public Journey getJourneyById(int userId) {

		return journeyDao.getById(userId);

	}

	@Override
	public boolean saveJourney(Journey journey) {
		Journey savedJourney = journeyDao.save(journey);
		if (savedJourney != null) {
			return true;
		}
		return false;
	}

	@Override
	public Journey startJourney(int userId, int startStationId) {
		Journey journey = new Journey(userId, startStationId, 0, LocalDateTime.now(), null, 0.00, false);
		saveJourney(journey);
		return journey;
		
	}

	@Override
	public Journey updateJourney(int userId, int endStationId) {
		
		// Get user existing journey
		Journey existingUserJourney = getJourneyById(userId);
		
		double fareBetweenStations = 5.00;
		double fareSameStation = 2.00; // Minimum 2.00 spend to account for someone swiping and swiping out at same station
		
		// Calculate fare
		double fare = Math.max(fareBetweenStations * (Math.abs(endStationId - existingUserJourney.getStartStationId())), fareSameStation);
		
		// Determine whether to apply fine
		LocalDateTime currentTime = LocalDateTime.now();
		boolean applyFine = false;
		if(ChronoUnit.MINUTES.between(existingUserJourney.getStartTime(), currentTime) >= 120) {
			applyFine = true;
			fare += 10.00;
		}
		
		// Update fields
		existingUserJourney.setEndStationId(endStationId);
		existingUserJourney.setEndTime(currentTime);
		existingUserJourney.setPrice(fare);
		existingUserJourney.setApplyFine(applyFine);
		
		if(saveJourney(existingUserJourney)) {
			return getJourneyById(userId);
		}
		return null;
		
	}

}
