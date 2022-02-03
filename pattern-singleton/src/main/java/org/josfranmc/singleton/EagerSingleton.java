package org.josfranmc.singleton;

public class EagerSingleton {

    private static final EagerSingleton instance;

    private String value;
    
    //Siempre se crea la instancia, llamemos o no a la clase
    static {
        try {
            instance = new EagerSingleton();
        } catch (Exception e) {
            throw new RuntimeException("Exception creating singleton instance");
        }
    }
    
    private EagerSingleton() {
    	value = "ONE";
    }
    
    public static EagerSingleton getInstance() {
        return instance;
    }
    
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public EagerSingleton clone() throws CloneNotSupportedException {
	        throw new CloneNotSupportedException();
	}
}
