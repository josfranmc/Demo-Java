package org.josfranmc.strategy;

public class ShootFast implements IShootStrategy {

	@Override
	public void shoot() {
		System.out.print("Shooting fast...");
	}
}