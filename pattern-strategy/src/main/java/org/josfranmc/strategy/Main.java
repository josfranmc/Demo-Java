package org.josfranmc.strategy;

public class Main {
	
    public static void main(String[] args) {
    	ShootContext shoot = new ShootContext(IShootStrategy.FAST);
    	shoot.executeStrategy();
        System.out.println("\n---------");

        shoot.setStrategy(IShootStrategy.SLOW);
    	shoot.executeStrategy();
        System.out.println("\n---------");
    }
}