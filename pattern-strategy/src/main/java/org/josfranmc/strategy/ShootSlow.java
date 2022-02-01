package org.josfranmc.strategy;

public class ShootSlow implements IShootStrategy {

	@Override
	public void shoot() {
		System.out.print("Shooting slow...");
	}
}