package org.josfranmc.singleton;

public class Main {
	
    public static void main(String[] args) {
    	
    	EagerSingleton esingle = EagerSingleton.getInstance();
    	
    	System.out.println("EagerSingleton:");
    	//Al crear un objeto EagerSingleton su valor es ONE
    	System.out.println(esingle.getValue());
    	//podemos cambiar su valor
    	esingle.setValue("TWO");
    	System.out.println(esingle.getValue());
    	//si obtenemos una nueva instancia será la misma de antes por lo que no tenemos el valor por defecto al crearse (ONE) si no el que tenía la instancia ya creada (TWO)
    	esingle = EagerSingleton.getInstance(); 
    	System.out.print(esingle.getValue());
        System.out.println("\n---------");

        System.out.println("EnumSingleton:");
        EnumSingleton enumSingle = EnumSingleton.INSTANCE;
        System.out.println(enumSingle.getValue());
        enumSingle.setValue("TWO");
        System.out.println(enumSingle.getValue());
        enumSingle = EnumSingleton.INSTANCE;
        System.out.print(enumSingle.getValue());
        System.out.println("\n---------");
        
        System.out.println("InnerClassSingleton:");
        InnerClassSingleton innerSingle = InnerClassSingleton.getInstance();
        System.out.println(innerSingle.getValue());
        innerSingle.setValue("TWO");
        System.out.println(innerSingle.getValue());
        innerSingle = InnerClassSingleton.getInstance();
        System.out.print(innerSingle.getValue());
        System.out.println("\n---------");

        System.out.println("ThreadSafeSingleton:");
        new Thread(new Runnable() {
            @Override
            public void run() {
            	ThreadSafeSingleton tsSingle = ThreadSafeSingleton.getInstance("ONE");
                System.out.println(tsSingle.getValue() + " - thread1");
            }
        }).start();

        ThreadSafeSingleton tsSingle = ThreadSafeSingleton.getInstance("TWO");
        System.out.println(tsSingle.getValue());
        
        new Thread(new Runnable() {
            @Override
            public void run() {
            	ThreadSafeSingleton tsSingle = ThreadSafeSingleton.getInstance("THREE");
                System.out.println(tsSingle.getValue() + " - thread2");
            }
        }).start();
    }
}