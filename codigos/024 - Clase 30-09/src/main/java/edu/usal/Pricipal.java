package edu.usal;

import edu.usal.model.Persona;
import edu.usal.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Pricipal {

    public static void main(String[] args) {
        Transaction transaction = null;

        Session session = HibernateUtil.getSessionFactory().openSession();

        transaction = session.beginTransaction();

        Persona laura = new Persona(1L, "Laura", "Martinez", "4586521");

        //session.save(laura);

        Persona lauraByBD = session.get(Persona.class, 1L);

        System.out.println(lauraByBD);

        lauraByBD.setApellido("Lopez");

        session.update(lauraByBD);

        //session.saveOrUpdate(laura);

        transaction.commit();

        session.close();

    }


}
