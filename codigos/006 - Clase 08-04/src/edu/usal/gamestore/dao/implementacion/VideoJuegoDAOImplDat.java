package edu.usal.gamestore.dao.implementacion;

import edu.usal.gamestore.dao.interfaces.VideoJuegoDAO;
import edu.usal.gamestore.dominio.VideoJuego;

import java.util.List;

public class VideoJuegoDAOImplDat implements VideoJuegoDAO {
    @Override
    public boolean crear(VideoJuego videoJuego) {
        return false;
    }

    @Override
    public List<VideoJuego> obtenerTodos() {
        return List.of();
    }
}
