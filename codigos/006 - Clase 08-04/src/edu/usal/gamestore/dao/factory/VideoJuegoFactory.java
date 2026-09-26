package edu.usal.gamestore.dao.factory;

import edu.usal.gamestore.dao.implementacion.VideoJuegoDAOImplDat;
import edu.usal.gamestore.dao.implementacion.VideoJuegoDAOImplTxt;
import edu.usal.gamestore.dao.interfaces.VideoJuegoDAO;
import edu.usal.gamestore.dominio.VideoJuego;

public class VideoJuegoFactory {

    public static VideoJuegoDAO getVideoJuegoDAO(String source){
        if(source.equals("TXT")) {
            return new VideoJuegoDAOImplTxt();
        } else if(source.equals("DAT")){
            return new VideoJuegoDAOImplDat();
        }
        throw new RuntimeException("No implementation");
    }
}
