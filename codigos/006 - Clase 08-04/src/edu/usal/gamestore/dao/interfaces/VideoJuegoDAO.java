package edu.usal.gamestore.dao.interfaces;

import edu.usal.gamestore.dominio.VideoJuego;

import java.util.List;

public interface VideoJuegoDAO {

    //CRUD - QUE

    boolean crear(VideoJuego videoJuego);
    List<VideoJuego> obtenerTodos();

}
