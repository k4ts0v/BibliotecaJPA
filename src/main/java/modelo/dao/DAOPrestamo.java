package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.conexion.Conexion;
import modelo.dto.Prestamo;
import modelo.dto.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPrestamo {
    EntityManager em = Conexion.getConexion();

    /**
     * Método que crea un nuevo préstamo en la BD.
     * @param prestamo Objeto préstamo a crear.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Integer create(Prestamo prestamo) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            // Es necesario que los objetos de los ejemplares y usuarios estén persistidos.
            if (prestamo.getEjemplar().getId() == null) {
                em.persist(prestamo.getEjemplar()); // Persistir el ejemplar si no está guardado.
                System.out.println("Ejemplar creado.");
            }
            if (prestamo.getUsuario().getId() == null) {
                em.persist(prestamo.getUsuario()); // Persistir el usuario si no está guardado.
                System.out.println("Usuario creado.");
            }

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

    /**
     * Método que lee un préstamo de la BD.
     * @param prestamo Objeto préstamo a leer.
     * @return 1 si se ha encontrado el préstamo, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Prestamo read(Prestamo prestamo) throws SQLException {
        return em.find(Prestamo.class, prestamo.getId());
    }

    /**
     * Método que lee todos los préstamos de la BD.
     * @return ArrayList con todos los préstamos.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public ArrayList<Prestamo> readAll() throws SQLException {
        return (ArrayList<Prestamo>) em.createQuery(
                "SELECT DISTINCT p FROM Prestamo p " +
                        "JOIN FETCH p.usuario u " +  // Fetch associated Usuario
                        "JOIN FETCH p.ejemplar e",   // Fetch associated Ejemplar
                Prestamo.class
        ).getResultList();
    }

    /**
     * Método que lee todos los préstamos de un usuario.
     * @param usuariotmp Usuario del préstamo.
     * @return ArrayList con todos los préstamos.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que actualiza un préstamo en la BD.
     * @param prestamo Objeto préstamo a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que elimina un préstamo de la BD.
     * @param prestamo Objeto préstamo a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que verifica si un usuario no ha superado el límite de préstamos.
     * @param id ID del usuario.
     * @return Verdadero si el usuario no ha superado el límite de préstamos, falso en caso contrario.
     */
    public boolean verificarNumPrestamos(Integer id) {
        Long count = (Long) em.createQuery("SELECT COUNT(p) FROM Prestamo p WHERE p.usuario.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return count <= 3;
    }
}
