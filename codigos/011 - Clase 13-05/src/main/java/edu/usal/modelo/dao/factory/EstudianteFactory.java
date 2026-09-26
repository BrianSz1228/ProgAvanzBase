package edu.usal.modelo.dao.factory;

import edu.usal.modelo.dao.implementaciones.EstudianteDAOImpl;
import edu.usal.modelo.dao.interfaces.EstudianteDAO;

public class EstudianteFactory {

    public static EstudianteDAO getEstudianteFactory(String origen){
        if(origen.equals("MEM")){
            return new EstudianteDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }

}
