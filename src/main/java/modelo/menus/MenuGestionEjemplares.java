package modelo.menus;

import modelo.dto.Ejemplar;
import modelo.dto.Libro;
import modelo.gestion.GestionEjemplar;

import java.util.Scanner;

public class MenuGestionEjemplares {
    static Scanner k =  new Scanner(System.in);
    public static GestionEjemplar gestionEjemplar = new GestionEjemplar();
    private static void prtMenu() {
        System.out.println("""
                1 - Añadir un ejemplar
                2 - Mostrar un ejemplar
                3 - Mostrar todos los ejemplares
                4 - Ver stock
                5 - Actualizar un ejemplar
                6 - Eliminar un ejemplar
                -1 - Salir
                """);
    }

    private static void menu() {
        boolean salida = false;
        while (!salida) {
            prtMenu();
            int opcion = k.nextInt();
            switch (opcion) {
                case 1 -> gestionEjemplar.createEjemplar(getDatosEjemplar());
                case 2 -> gestionEjemplar.readEjemplar(getIdEjemplar());
                case 3 -> gestionEjemplar.readAllEjemplares();
                case 4 -> gestionEjemplar.getStock();
                case 5 -> gestionEjemplar.updateEjemplar(getIdDatosEjemplar());
                case 6 -> gestionEjemplar.deleteEjemplar(getIdEjemplar());
                case -1 -> salida = true;
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    private static Ejemplar getDatosEjemplar() {
        k.nextLine();
        System.out.println("Ingrese el ISBN del libro");
        String isbn = k.nextLine();
        System.out.println("Ingrese el estado del ejemplar");
        String estadoEjemplar = k.nextLine();
        return new Ejemplar(0, new Libro(isbn, "", ""), estadoEjemplar);
    }

    private static Ejemplar getIdEjemplar() {
        System.out.println("Ingrese el ID del ejemplar");
        Integer id = k.nextInt();
        return new Ejemplar(id, new Libro(), "");
    }

    private static Ejemplar getIdDatosEjemplar() {
        System.out.println("Ingrese el ID del ejemplar");
        Integer id = k.nextInt();
        k.nextLine();
        System.out.println("Ingrese el ISBN del libro");
        String isbn = k.nextLine();
        System.out.println("Ingrese el estado del ejemplar");
        String estadoEjemplar = k.nextLine();
        return new Ejemplar(id, new Libro(isbn, "", ""), estadoEjemplar);
    }

    public static void run() {
        menu();
    }
}
