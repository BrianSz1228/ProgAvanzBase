package edu.usal.gamestore;

import edu.usal.gamestore.dao.factory.VideoJuegoFactory;
import edu.usal.gamestore.dao.interfaces.VideoJuegoDAO;
import edu.usal.gamestore.dominio.VideoJuego;

import java.util.List;

public class Principal {

    public static void main(String[] args) {
        VideoJuego videoJuego = new VideoJuego();
        //TODO: Sacar el hardcode y usar properties
        String fuente = "TXT";

        VideoJuegoDAO dao = VideoJuegoFactory.getVideoJuegoDAO(fuente);
        dao.crear(videoJuego);

        List<VideoJuego> videoJuegos = dao.obtenerTodos();
    }
}
