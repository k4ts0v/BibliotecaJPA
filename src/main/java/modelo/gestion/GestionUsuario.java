package modelo.gestion;

import jakarta.persistence.NoResultException;
import modelo.dao.DAOUsuario;
import modelo.dto.Usuario;
import org.hibernate.annotations.processing.SQL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestionUsuario {
    private ArrayList<Usuario> listaUsuarios;
    private DAOUsuario daoUsuario;

    public GestionUsuario() {
        listaUsuarios = new ArrayList<>();
        daoUsuario = new DAOUsuario();
        try {
            listaUsuarios = daoUsuario.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer createUsuario(Usuario usuario) {
        try {
            if(daoUsuario.create(usuario) == 1) {
                listaUsuarios.add(usuario);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al crear el usuario." + e.getMessage());
        }
        return -1;
    }

    public Integer readUsuario(Usuario usuario) {
        try {
            Usuario usuarioDB = daoUsuario.read(usuario);
            if(listaUsuarios.contains(usuarioDB)) {
                System.out.println(usuarioDB);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("No se ha encontrado el usuario." + e.getMessage());
        }
        return -1;
    }

    public Integer readAllUsuarios() {
        listaUsuarios.forEach(usuario -> System.out.println(usuario));
        return 1;
    }

    public Integer updateUsuario(Usuario usuario) {
        try {
            if(daoUsuario.update(usuario) == 1) {
                listaUsuarios.removeIf(l -> l.getId().equals(usuario.getId()));
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario." + e.getMessage());
        }
        return -1;
    }

    public Integer deleteUsuario(Usuario usuario) {
        try {
            if(daoUsuario.delete(usuario) == 1) {
                listaUsuarios.remove(usuario);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario."  + e.getMessage());
        }
        return -1;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public boolean verificarPenalizacion(Usuario usuario) {
        if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isBefore(LocalDate.now())) {
            System.out.println("El usuario no puede realizar préstamos porque tiene una penalización activa.");
            return true;
        }
        return false;
    }

    public boolean verificarUsuarioAdministrador(Usuario usuario) {
        return daoUsuario.verificarUsuarioAdministrador(usuario);
    }

    public boolean verificarUsuarioNormal(Usuario usuario) {
        return daoUsuario.verificarUsuarioNormal(usuario);
    }

    public Usuario getUsuarioById(Integer idUsuario) {
        try {
            return daoUsuario.findUsuarioById(idUsuario);
        } catch (SQLException e) {
            System.out.println("Error al obtener el usuario." + e.getMessage());
        }
        return null;
    }

    public Usuario getUsuarioByEmailPassword(String email, String password) {
        try {
            return daoUsuario.getUsuarioByEmailPassword(email, password);
        } catch (SQLException | NoResultException e) {
            System.out.println("Error al obtener el usuario." + e.getMessage());
        }
        return null;
    }
}
