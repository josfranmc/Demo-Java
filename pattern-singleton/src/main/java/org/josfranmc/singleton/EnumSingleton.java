package org.josfranmc.singleton;

//Es thread-safe, serializable y no tiene problemas con Reflection
public enum EnumSingleton {

    INSTANCE;
	
	private String value = "ONE";
    
	/*Este constructor es implícito (por ser enum)
	private EnumSingleton() {

    }*/
	
    public String getValue(){
        return value;
    }
    
    public void setValue(String value){
        this.value = value;
    }
}
