package org.josfranmc.strategy;

public class ShootContext {

	private IShootStrategy shootStrategy;

	public ShootContext(String strategy) {
		setStrategy(strategy);
	}

	public void executeStrategy() {
		shootStrategy.shoot();
	}
	
	public void setStrategy(String strategy) {
		if (strategy.equals(IShootStrategy.FAST)) {
			this.shootStrategy = new ShootFast();
		} else if (strategy.equals(IShootStrategy.SLOW)) {
			this.shootStrategy = new ShootSlow();
		}
	}
}
