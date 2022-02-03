package org.josfranmc.singleton;

//Es thread-safe y Lazy
public class InnerClassSingleton {

	private String value = "ONE";
	
	private static class SingletonHolder {
		public static InnerClassSingleton instance = new InnerClassSingleton();
	}
	
	private InnerClassSingleton() {

	}

	public static InnerClassSingleton getInstance() {
		return SingletonHolder.instance;
	}

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public InnerClassSingleton clone() throws CloneNotSupportedException {
	        throw new CloneNotSupportedException();
	}
}
