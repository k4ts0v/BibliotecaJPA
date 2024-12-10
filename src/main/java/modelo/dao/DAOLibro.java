package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.conexion.Conexion;
import modelo.dto.Libro;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOLibro {
    EntityManager em = Conexion.getConexion();

    public Integer create(Libro libro) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.persist(libro);
            em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
            return -1;
    }

    public Libro read(Libro libro) throws SQLException {
        return em.find(Libro.class, libro.getIsbn());
    }

    public ArrayList<Libro> readAll() throws SQLException {
        return (ArrayList<Libro>) em.createQuery("SELECT l FROM Libro l").getResultList();
    }

    public Integer update(Libro libro) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            Libro libroBD = em.find(Libro.class, libro.getIsbn());
            libroBD.setTitulo(libro.getTitulo());
            libroBD.setAutor(libro.getAutor());
            em.merge(libroBD);
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

    public Integer delete(Libro libro) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.remove(em.find(Libro.class, libro.getIsbn()));
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
}
