package com.example.SquareGame.model;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomHeartbeat implements HeartbeatSensor {

	private final Random random = new Random();

	@Override
	public int get() {
		return random.nextInt(100);
	}
}
