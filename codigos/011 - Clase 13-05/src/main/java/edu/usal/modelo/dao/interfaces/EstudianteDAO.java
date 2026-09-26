package edu.usal.modelo.dao.interfaces;

import edu.usal.modelo.dominio.Estudiante;

import java.util.List;

public interface EstudianteDAO {

    //CRUD
    boolean crearEstudiante(Estudiante estudiante);
    List<Estudiante> obtenerTodos();

}
