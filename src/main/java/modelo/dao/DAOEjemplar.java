package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.conexion.Conexion;
import modelo.dto.Ejemplar;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOEjemplar {
    EntityManager em = Conexion.getConexion();

    /**
     * Método que crea un nuevo ejemplar en la BD.
     * @param ejemplar Objeto ejemplar a crear.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que lee un ejemplar de la BD.
     * @param ejemplar Objeto ejemplar a leer.
     * @return 1 si se ha encontrado el ejemplar, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Ejemplar read(Ejemplar ejemplar) throws SQLException {
        return em.find(Ejemplar.class, ejemplar.getId());
    }

    /**
     * Método que lee todos los ejemplares de la BD.
     * @return ArrayList con todos los ejemplares.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public ArrayList<Ejemplar> readAll() throws SQLException {
        return (ArrayList<Ejemplar>) em.createQuery("SELECT e FROM Ejemplar e JOIN FETCH e.isbn").getResultList();
    }

    /**
     * Método que actualiza un ejemplar en la BD.
     * @param ejemplar Objeto ejemplar a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que elimina un ejemplar de la BD.
     * @param ejemplar Objeto ejemplar a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que obtiene un ejemplar por su ID.
     * @param id ID del ejemplar.
     * @return Objeto ejemplar, si se encuentra. Null en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Ejemplar findEjemplarById(Integer id) throws SQLException {
        return em.find(Ejemplar.class, id);
    }
}
