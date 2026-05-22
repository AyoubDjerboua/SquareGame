package com.example.SquareGame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SquareGame.model.HeartbeatSensor;

@RestController
public class HeartbeatController {

	@Autowired
	private HeartbeatSensor heartbeatSensor;

	@GetMapping("/heartbeat")
	public int getHeartbeat() {
		return heartbeatSensor.get();
	}
}
