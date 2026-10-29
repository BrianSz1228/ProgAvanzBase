package edu.usal;

import edu.usal.model.Perfil;
import edu.usal.model.Usuario;
import edu.usal.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Pricipal {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        /*Usuario usuario = new Usuario(null, "userName1");
        usuario.setPerfil(new Perfil(null, "........................", "http://"));

        session.saveOrUpdate(usuario);

        session.getTransaction().commit();*/

        session.close();

    }


}
