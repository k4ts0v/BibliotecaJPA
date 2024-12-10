package modelo.menus;

import modelo.dao.DAOPrestamo;
import modelo.dto.Ejemplar;
import modelo.dto.Libro;
import modelo.dto.Prestamo;
import modelo.dto.Usuario;
import modelo.gestion.GestionEjemplar;
import modelo.gestion.GestionLibro;
import modelo.gestion.GestionPrestamo;
import modelo.gestion.GestionUsuario;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    static MenuPrincipal menu = new MenuPrincipal();

    public static void main(String[] args) throws SQLException {
//        Usuario usuario = new Usuario("23342184A", "Foo", "foobar@mail.co", "P455w0rd!", "normal", null);
//        gestionUsuario.createUsuario(usuario);
//        gestionUsuario.createUsuario(new Usuario("23342184A", "Foo", "foobar@mail.co", "P455w0rd!", "Administrador", null));
//        gestionLibro.createLibro(new Libro("9781486010974", "Título", "Autor"));
//        gestionLibro.createLibro(new Libro("978-0-522-86467-0", "Otro título", "Otro autor"));
//        Ejemplar ejemplar = new Ejemplar(new Libro("978-1-4860-1097-4", "Título", "Autor"), "Disponible");
//        gestionEjemplar.createEjemplar(ejemplar);
//        gestionEjemplar.createEjemplar(new Ejemplar(new Libro("978-1-4860-1097-4", "Título", "Autor"), "Prestado"));
//        Prestamo prestamo = new Prestamo(usuario, ejemplar, LocalDate.now());
//        System.out.println(gestionPrestamo.createPrestamo(prestamo));
//        gestionPrestamo.readAllPrestamos();
        menu.run();
    }

}
