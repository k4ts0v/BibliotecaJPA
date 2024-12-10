package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import modelo.conexion.Conexion;
import modelo.dto.Prestamo;
import modelo.dto.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPrestamo {
    EntityManager em = Conexion.getConexion();

    public Integer create(Prestamo prestamo) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            // Persist related entities if they are not already in the database
            if (prestamo.getEjemplar().getId() == null) {
                em.persist(prestamo.getEjemplar()); // Persist Ejemplar if not saved
                System.out.println("Ejemplar creado.");
            }
            if (prestamo.getUsuario().getId() == null) {
                em.persist(prestamo.getUsuario()); // Persist Usuario if not saved
                System.out.println("Usuario creado.");
            }

            System.out.println(prestamo.getUsuario().toString());
            System.out.println(prestamo.getEjemplar().toString());
            System.out.println(prestamo.toString());
            em.persist(prestamo);
            em.getTransaction().commit();
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return -1;
    }

    public Prestamo read(Prestamo prestamo) throws SQLException {
        return em.find(Prestamo.class, prestamo.getId());
    }

    public ArrayList<Prestamo> readAll() throws SQLException {
        return (ArrayList<Prestamo>) em.createQuery(
                "SELECT DISTINCT p FROM Prestamo p " +
                        "JOIN FETCH p.usuario u " +  // Fetch associated Usuario
                        "JOIN FETCH p.ejemplar e",   // Fetch associated Ejemplar
                Prestamo.class
        ).getResultList();
    }

    public ArrayList<Prestamo> readAllByUsuario(Usuario usuariotmp) throws SQLException {
        Usuario usuario = em.find(Usuario.class, usuariotmp.getId()); // El usuario debe estar persistido.
        return (ArrayList<Prestamo>) em.createQuery(
                        "SELECT DISTINCT p FROM Prestamo p " +
                                "JOIN FETCH p.usuario u " +  // Fetch associated Usuario
                                "JOIN FETCH p.ejemplar e " +  // Fetch associated Ejemplar
                                "WHERE u = :usuario",         // Filter by the given Usuario
                        Prestamo.class
                ).setParameter("usuario", usuario)  // Bind the usuario parameter
                .getResultList();
    }

    public Integer update(Prestamo prestamo) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.merge(prestamo);
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

    public Integer delete(Prestamo prestamo) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.remove(em.find(Prestamo.class, prestamo.getId()));
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

    public boolean verificarNumPrestamos(Integer id) {
        Long count = (Long) em.createQuery("SELECT COUNT(p) FROM Prestamo p WHERE p.usuario.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return count <= 3;
    }
}
