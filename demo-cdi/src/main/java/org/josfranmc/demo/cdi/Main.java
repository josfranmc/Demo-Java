package org.josfranmc.demo.cdi;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
//import jakarta.enterprise.inject.spi.CDI;

public class Main {
	
    public static void main( String[] args ) {
    	
    	SeContainer container = null;

        try {
            container = SeContainerInitializer.newInstance().initialize(); 
            
            //Obtenemos un bean via CDI
            Controller controller = container.select(Controller.class).get();
            
            //También podemos obtenerlo directamente del contenedor, sin instanciarlo
            //Controller controller = CDI.current().select(Controller.class).get();
            
            controller.execute();
        } finally {
            if (container != null) {
                container.close();
            }
        }
    }
}
