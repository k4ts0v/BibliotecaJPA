package modelo.dao;

import jakarta.persistence.EntityManager;
import modelo.conexion.Conexion;
import modelo.dto.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOUsuario {
    EntityManager em = Conexion.getConexion();

    /**
     * Método que crea un nuevo usuario en la BD.
     * @param usuario Objeto usuario a crear.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Integer create(Usuario usuario) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.persist(usuario);
            em.getTransaction().commit();
        }
        catch(Exception e){
            em.getTransaction().rollback();
        }
        return -1;
    }

    /**
     * Método que lee un usuario de la BD.
     * @param usuario Objeto usuario a leer.
     * @return 1 si se ha encontrado el usuario, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Usuario read(Usuario usuario) throws SQLException {
        return em.find(Usuario.class, usuario.getId());
    }

    /**
     * @return ArrayList con todos los usuarios.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public ArrayList<Usuario> readAll() throws SQLException {
        return (ArrayList<Usuario>) em.createQuery("SELECT u FROM Usuario u").getResultList();
    }

    /**
     * Método que actualiza un usuario en la BD.
     * @param usuario Objeto usuario a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Integer update(Usuario usuario) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.merge(usuario);
            em.getTransaction().commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Método que elimina un usuario de la BD.
     * @param usuario Objeto usuario a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     * @throws SQLException
     */
    public Integer delete(Usuario usuario) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.remove(em.find(Usuario.class, usuario.getId()));
            em.getTransaction().commit();
            return 1;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Método que obtiene un usuario por su ID.
     * @param id ID del usuario.
     * @return Objeto usuario, si se encuentra. Null en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Usuario findUsuarioById(Integer id) throws SQLException {
        return em.find(Usuario.class, id);
    }

    /**
     * Método que verifica si existe un usuario administrador con los datos del usuario pasado como parámetro.
     * @param u Usuario a verificar.
     * @return Verdadero si existe un usuario administrador, falso en caso contrario.
     */
    public boolean verificarUsuarioAdministrador(Usuario u) {
        Long count = (Long) em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email AND u.password = :password AND u.tipo = 'administrador'")
            .setParameter("email", u.getEmail())
            .setParameter("password", u.getPassword())
            .getSingleResult();
        return count > 0;
    }

    /**
     * Método que verifica si existe un usuario normal con los datos del usuario pasado como parámetro.
     * @param u Objeto usuario.
     * @return Verdadero si existe un usuario normal, falso en caso contrario.
     */
    public boolean verificarUsuarioNormal(Usuario u) {
        Long count = (Long) em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email AND u.password = :password AND u.tipo = 'normal'")
                .setParameter("email", u.getEmail())
                .setParameter("password", u.getPassword())
                .getSingleResult();
        return count > 0;
    }

    /**
     * Método que obtiene un usuario por su email y password.
     * @param email Email del usuario.
     * @param password Password del usuario.
     * @return Objeto usuario, si se encuentra. Null en caso contrario.
     * @throws SQLException Excepción lanzada si no se ha podido realizar la operación.
     */
    public Usuario getUsuarioByEmailPassword(String email, String password) throws SQLException {
        Usuario usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password")
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
        return usuario;
    }
}
