package modelo.menus;

import modelo.dto.Libro;
import modelo.gestion.GestionLibro;

import java.util.Scanner;

public class MenuGestionLibros {
    static Scanner k =  new Scanner(System.in);
    static GestionLibro gestionLibro = new GestionLibro();

    /**
     * Método que imprime el menú principal.
     */
    private static void prtMenu() {
        System.out.println("""
                1 - Añadir un libro
                2 - Mostrar un libro
                3 - Mostrar todos los libros
                4 - Actualizar un libro
                5 - Eliminar un libro
                -1 - Salir
                """);
    }

    /**
     * Método que gestiona el menú principal.
     */
    private static void menu() {
        boolean salida = false;
        while (!salida) {
            prtMenu();
            int opcion = k.nextInt();
            switch (opcion) {
                case 1 -> gestionLibro.createLibro(getIdDatosLibro());
                case 2 -> gestionLibro.readLibro(getIdLibro());
                case 3 -> gestionLibro.readAllLibros();
                case 4 -> gestionLibro.updateLibro(getIdDatosLibro());
                case 5 -> gestionLibro.deleteLibro(getIdLibro());
                case -1 -> salida = true;
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    /**
     * Método que obtiene los datos necesarios para crear un nuevo libro con  el ID.
     * @return Objeto libro.
     */
    private static Libro getIdLibro() {
        k.nextLine();
        System.out.println("Ingrese el ISBN del libro");
        String isbn = k.nextLine();
        return new Libro(isbn, "Título", "Autor");
    }

    /**
     * Método que obtiene los datos necesarios para crear un nuevo libro con todos los datos.
     * @return Objeto libro.
     */
    private static Libro getIdDatosLibro() {
        k.nextLine();
        System.out.println("Ingrese el ISBN del libro");
        String isbn = k.nextLine();
        System.out.println("Ingrese el título del libro");
        String tituloLibro = k.nextLine();
        System.out.println("Ingrese el autor del libro");
        String autorLibro = k.nextLine();
        return new Libro(isbn, tituloLibro, autorLibro);
    }

    /**
     * Método que ejecuta el menú principal.
     */
    public static void run() {
        menu();
    }
}
