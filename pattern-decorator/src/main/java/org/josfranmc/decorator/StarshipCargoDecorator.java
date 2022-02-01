package org.josfranmc.decorator;

public class StarshipCargoDecorator extends StarshipDecorator {

	public StarshipCargoDecorator(IStarship starship) {
		super(starship);
	}

	@Override
	public void configStarship() {
		super.configStarship();
		System.out.print(" (Starship Cargo:");
		this.addWarehouse();
		System.out.print(")");
	}

	private void addWarehouse() {
		System.out.print(" Warehouse ");
	}
}