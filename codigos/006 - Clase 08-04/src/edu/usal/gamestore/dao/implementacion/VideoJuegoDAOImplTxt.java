package edu.usal.gamestore.dao.implementacion;

import edu.usal.gamestore.dao.interfaces.VideoJuegoDAO;
import edu.usal.gamestore.dominio.VideoJuego;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class VideoJuegoDAOImplTxt implements VideoJuegoDAO {
    @Override
    public boolean crear(VideoJuego videoJuego) {
        File file = new File("videojuego.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        return false;
    }

    @Override
    public List<VideoJuego> obtenerTodos() {
        //TODO : Implementar logica de lectura de datos del archivo de txt
        return List.of();
    }
}
