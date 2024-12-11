package modelo.gestion;

import jakarta.persistence.NoResultException;
import modelo.dao.DAOUsuario;
import modelo.dto.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestionUsuario {
    private ArrayList<Usuario> listaUsuarios;
    private DAOUsuario daoUsuario;

    /**
     * Constructor de la clase.
     */
    public GestionUsuario() {
        listaUsuarios = new ArrayList<>();
        daoUsuario = new DAOUsuario();
        try {
            listaUsuarios = daoUsuario.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que crea un nuevo usuario en la BD, y lo añade a la lista de usuarios de la memoria.
     * @param usuario Objeto usuario a crear.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     */
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

    /**
     * Método que lee un usuario de la BD.
     * @param usuario Objeto usuario a leer.
     * @return 1 si se ha encontrado el usuario, -1 en caso contrario.
     */
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

    /**
     * Método que lee todos los usuarios de la BD.
     * @return 1 si se han leido todos los usuarios, -1 en caso contrario.
     */
    public Integer readAllUsuarios() {
        listaUsuarios.forEach(usuario -> System.out.println(usuario));
        return 1;
    }

    /**
     * Método que actualiza un usuario en la BD y en la lista de usuarios de la memoria.
     * @param usuario Objeto usuario a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     */
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

    /**
     * Método que elimina un usuario de la BD y de la lista de usuarios de la memoria.
     * @param usuario Objeto usuario a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     */
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

    /**
     * Método que obtiene la lista de usuarios de la memoria.
     * @return Lista de usuarios.
     */
    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * Método que verifica si un usuario tiene una penalización activa.
     * @param usuario Objeto usuario.
     * @return Verdadero si el usuario tiene una penalización activa, falso en caso contrario.
     */
    public boolean verificarPenalizacion(Usuario usuario) {
        if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isBefore(LocalDate.now())) {
            System.out.println("El usuario no puede realizar préstamos porque tiene una penalización activa.");
            return true;
        }
        return false;
    }

    /**
     * Método que verifica si existe un usuario administrador con los datos del usuario pasado como parámetro.
     * @param usuario Objeto usuario.
     * @return Verdadero si existe un usuario administrador, falso en caso contrario.
     */
    public boolean verificarUsuarioAdministrador(Usuario usuario) {
        return daoUsuario.verificarUsuarioAdministrador(usuario);
    }

    /**
     * Método que verifica si existe un usuario normal con los datos del usuario pasado como parámetro.
     * @param usuario Objeto usuario.
     * @return Verdadero si existe un usuario normal, falso en caso contrario.
     */
    public boolean verificarUsuarioNormal(Usuario usuario) {
        return daoUsuario.verificarUsuarioNormal(usuario);
    }

    /**
     * Método que obtiene un usuario por su ID.
     * @param idUsuario ID del usuario.
     * @return Objeto usuario, si se encuentra. Null en caso contrario.
     */
    public Usuario getUsuarioById(Integer idUsuario) {
        try {
            return daoUsuario.findUsuarioById(idUsuario);
        } catch (SQLException e) {
            System.out.println("Error al obtener el usuario." + e.getMessage());
        }
        return null;
    }

    /**
     * Método que obtiene un usuario por su email y password.
     * @param email Email del usuario.
     * @param password Password del usuario.
     * @return Objeto usuario, si se encuentra. Null en caso contrario.
     */
    public Usuario getUsuarioByEmailPassword(String email, String password) {
        try {
            return daoUsuario.getUsuarioByEmailPassword(email, password);
        } catch (SQLException | NoResultException e) {
            System.out.println("Error al obtener el usuario." + e.getMessage());
        }
        return null;
    }
}
