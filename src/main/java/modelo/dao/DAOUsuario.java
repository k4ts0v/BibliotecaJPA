package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.conexion.Conexion;
import modelo.dto.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOUsuario {
    EntityManager em = Conexion.getConexion();

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

    public Usuario read(Usuario usuario) throws SQLException {
        return em.find(Usuario.class, usuario.getId());
    }

    public ArrayList<Usuario> readAll() throws SQLException {
        return (ArrayList<Usuario>) em.createQuery("SELECT u FROM Usuario u").getResultList();
    }

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

    public Integer delete(Usuario usuario) throws SQLException {
        try {
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.remove(em.find(Usuario.class, usuario.getId()));
            em.getTransaction().commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public Usuario findUsuarioById(Integer id) throws SQLException {
        return em.find(Usuario.class, id);
    }

    public boolean verificarUsuarioAdministrador(Usuario u) {
        Long count = (Long) em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email AND u.password = :password AND u.tipo = 'administrador'")
            .setParameter("email", u.getEmail())
            .setParameter("password", u.getPassword())
            .getSingleResult();
        return count > 0;
    }

    public boolean verificarUsuarioNormal(Usuario u) {
        Long count = (Long) em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email AND u.password = :password AND u.tipo = 'normal'")
                .setParameter("email", u.getEmail())
                .setParameter("password", u.getPassword())
                .getSingleResult();
        return count > 0;
    }

    public Usuario getUsuarioByEmailPassword(String email, String password) throws SQLException {
        Usuario usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password")
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
        return usuario;
    }
}
