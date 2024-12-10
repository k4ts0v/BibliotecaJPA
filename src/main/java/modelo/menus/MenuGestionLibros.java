package modelo.menus;

import modelo.dto.Libro;
import modelo.gestion.GestionLibro;

import java.util.Scanner;

public class MenuGestionLibros {
    static Scanner k =  new Scanner(System.in);
    static GestionLibro gestionLibro = new GestionLibro();
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

    private static Libro getIdLibro() {
        k.nextLine();
        System.out.println("Ingrese el ISBN del libro");
        String isbn = k.nextLine();
        return new Libro(isbn, "Título", "Autor");
    }

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

    public static void run() {
        menu();
    }
}
