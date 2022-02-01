package org.josfranmc.decorator;

public class StarshipBattleDecorator extends StarshipDecorator {

	public StarshipBattleDecorator(IStarship starship) {
		super(starship);
	}
	
	@Override
	public void configStarship() {
		super.configStarship();
		System.out.print(" (Starship Battle:");
		this.addLaser();
		this.addShield();
		System.out.print(")");
	}

	public void addLaser() {
		System.out.print(" Laser ");
	}

	private void addShield() {
		System.out.print(" Shield ");
	}
}
