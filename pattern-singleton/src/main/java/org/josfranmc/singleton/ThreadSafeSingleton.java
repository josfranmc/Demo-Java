package org.josfranmc.singleton;

//Válido en entornos multi-thread a partir de Java 1.5
//La inicialización es Lazy
//https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
public class ThreadSafeSingleton {

    private volatile static ThreadSafeSingleton instance;
    
    private String value;
    
    private ThreadSafeSingleton(String value) {
    	this.value = value;
    }
    
    public static ThreadSafeSingleton getInstance(String value) {
    	ThreadSafeSingleton localRef = instance;
        if (localRef == null) {
            synchronized (ThreadSafeSingleton.class) {
            	localRef = instance;
            	if (localRef == null) {
            		instance = localRef = new ThreadSafeSingleton(value);
            	}
            }
        }
        return localRef;
    }
    
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public ThreadSafeSingleton clone() throws CloneNotSupportedException {
	        throw new CloneNotSupportedException();
	}
}
