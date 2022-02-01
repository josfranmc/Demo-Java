package org.josfranmc.decorator;

public class StarshipDecorator implements IStarship {

	private final IStarship starship;
	
	public StarshipDecorator(IStarship starship) {
		this.starship = starship;
	}
	
	@Override
	public void configStarship() {
		this.starship.configStarship();
	}
}