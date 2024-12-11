package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.conexion.Conexion;
import modelo.dto.Libro;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOLibro {
    EntityManager em = Conexion.getConexion();

    /**
     * Método que crea un nuevo libro en la BD.
     * @param libro Objeto libro a crear.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que lee un libro de la BD.
     * @param libro Objeto libro a leer.
     * @return 1 si se ha encontrado el libro, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Libro read(Libro libro) throws SQLException {
        return em.find(Libro.class, libro.getIsbn());
    }

    /**
     * Método que lee todos los libros de la BD.
     * @return ArrayList con todos los libros.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public ArrayList<Libro> readAll() throws SQLException {
        return (ArrayList<Libro>) em.createQuery("SELECT l FROM Libro l").getResultList();
    }

    /**
     * Método que actualiza un libro en la BD.
     * @param libro Objeto libro a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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

    /**
     * Método que elimina un libro de la BD.
     * @param libro Objeto libro a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
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
