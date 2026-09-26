package edu.usal.modelo.dao.implementaciones;

import edu.usal.modelo.dao.interfaces.EstudianteDAO;
import edu.usal.modelo.dominio.Estudiante;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    private List<Estudiante> estudiantes;

    public EstudianteDAOImpl() {
        this.estudiantes = new ArrayList<>(
                Arrays.asList(
                        new Estudiante(1, "Jon Doe", "111111"),
                        new Estudiante(2, "Marie Doe", "2222222")
                )
        );
    }

    @Override
    public boolean crearEstudiante(Estudiante estudiante) {
        estudiante.setId(this.estudiantes.size() + 1);
        this.estudiantes.add(estudiante);
        this.estudiantes.forEach(System.out::println);
        return true;
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        return this.estudiantes;
    }
}
