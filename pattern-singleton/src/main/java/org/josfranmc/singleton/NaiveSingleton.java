package org.josfranmc.singleton;

//Válido solo en un contexto single-thread. Creación Lazy
public class NaiveSingleton {

    private static NaiveSingleton instance;
    
    private String value;
    
    private NaiveSingleton() {
    	value = "ONE";
    }
    
    //Se crea la instancia la primera vez que llamamos al método
    public static NaiveSingleton getInstance() {
        if (instance == null) {
            instance = new NaiveSingleton();
        }
        return instance;
    }
    
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public NaiveSingleton clone() throws CloneNotSupportedException {
	        throw new CloneNotSupportedException();
	}
}
