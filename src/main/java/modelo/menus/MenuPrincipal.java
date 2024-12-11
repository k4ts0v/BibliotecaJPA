package modelo.menus;

import modelo.dto.Usuario;
import modelo.gestion.GestionUsuario;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuPrincipal {
    Scanner k =  new Scanner(System.in);
    MenuUsuarioNormal menuUsuarioNormal = new MenuUsuarioNormal();
    MenuAdministrador menuAdministrador = new MenuAdministrador();
    GestionUsuario gestionUsuario = new GestionUsuario();
    static Usuario usuarioLogado = null;

    /**
     * Método que imprime el menú principal.
     */
    private void prtMenu() {
        System.out.println("""
                1 - Iniciar sesión como miembro de la biblioteca
                2 - Iniciar sesión como administrador de la biblioteca
                -1 - Salir del programa
                """);
    }

    /**
     * Método que gestiona el menú principal.
     */
    private void menu() {
        boolean salida = false;
        while (!salida) {
            prtMenu();
            int opcion = k.nextInt();
            switch (opcion) {
                case 1 -> loginUsuario();
                case 2 -> loginAdministrador();
                case -1 -> salida = true;
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    /**
     * Método que recoge los datos necesarios para iniciar sesión (email y password).
     * @return Objeto Usuario con los datos recogidos anteriormente.
     */
    private Usuario recogerDatos() {
        k.nextLine();
        String tipo;
        System.out.print("Ingrese su email: ");
        String email = k.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = k.nextLine();
        return new Usuario(null, "", "", email, password, "", LocalDate.now());
    }

    /**
     * Método que inicia sesión como miembro de la biblioteca.
     */
    public void loginUsuario() {
        Usuario usuario = recogerDatos();
        Usuario usuarioPersistido = gestionUsuario.getUsuarioByEmailPassword(usuario.getEmail(), usuario.getPassword());
        if (gestionUsuario.verificarUsuarioNormal(usuario)) {
            setUsuarioLogado(usuarioPersistido);
            menuUsuarioNormal.run();
        } else {
            System.out.println("Las credenciales no son correctas.");
        }
    }

    /**
     * Método que inicia sesión como administrador de la biblioteca.
     */
    public void loginAdministrador() {
        Usuario usuario = recogerDatos();
        Usuario usuarioPersistido = gestionUsuario.getUsuarioByEmailPassword(usuario.getEmail(), usuario.getPassword());
        if (gestionUsuario.verificarUsuarioAdministrador(usuarioPersistido)) {
            setUsuarioLogado(usuarioPersistido);
            menuAdministrador.run();
        } else {
            System.out.println("Las credenciales no son correctas.");
        }
    }

    /**
     * Método que obtiene el usuario logueado.
     * @return Objeto usuario, que contiene el usuario que ha iniciado sesión.
     */
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Método que establece el usuario logueado.
     * @param usuario Objeto usuario, que contiene el usuario que ha iniciado sesión.
     */
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    /**
     * Método que ejecuta el menú principal.
     */
    public void run() {
        menu();
    }
}
