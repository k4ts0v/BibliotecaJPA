package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.conexion.Conexion;
import modelo.dto.Ejemplar;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOEjemplar {
    EntityManager em = Conexion.getConexion();

    public Integer create(Ejemplar ejemplar) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.persist(ejemplar);
            em.getTransaction().commit();
        }
        catch(Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return -1;
    }

    public Ejemplar read(Ejemplar ejemplar) throws SQLException {
        return em.find(Ejemplar.class, ejemplar.getId());
    }

    public ArrayList<Ejemplar> readAll() throws SQLException {
        return (ArrayList<Ejemplar>) em.createQuery("SELECT e FROM Ejemplar e JOIN FETCH e.isbn").getResultList();
    }

    public Integer update(Ejemplar ejemplar) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.merge(ejemplar);
            em.getTransaction().commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return -1;
    }

    public Integer delete(Ejemplar ejemplar) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.remove(em.find(Ejemplar.class, ejemplar.getId()));
            em.getTransaction().commit();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return -1;
    }

    public Ejemplar findEjemplarById(Integer id) throws SQLException {
        return em.find(Ejemplar.class, id);
    }
}
