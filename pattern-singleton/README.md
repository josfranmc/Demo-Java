# Demo-Java
Ejemplos de uso del patrón Singleton. Se exponen varias implementaciones.    

Mediante este patrón se garantiza que tan solo exista un objeto de una clase determinada al mismo tiempo que proporciona un única forma de creación del mismo.  
Hay que tener en cuenta que se crea un solo objeto dentro del ClassLoader en el que se ejecuta la apliación. Si tenemos varios ClassLoader cada uno tendrá su propio objeto Singleton.