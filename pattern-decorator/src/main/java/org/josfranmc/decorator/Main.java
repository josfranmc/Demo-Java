package org.josfranmc.decorator;

public class Main {
	
    public static void main(String[] args) {
        IStarship starship = new StarshipBasic();
        starship.configStarship();
        System.out.println("\n---------");

        IStarship starshipBattle = new StarshipBattleDecorator(new StarshipBasic());
        starshipBattle.configStarship();
        System.out.println("\n---------");

        IStarship starshipCargo = new StarshipCargoDecorator(new StarshipBattleDecorator(new StarshipBasic()));
        starshipCargo.configStarship();
        System.out.println("\n---------");
    }
}