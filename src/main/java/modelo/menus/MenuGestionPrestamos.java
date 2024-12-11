package modelo.menus;

import modelo.dto.Ejemplar;
import modelo.dto.Prestamo;
import modelo.dto.Libro;
import modelo.dto.Usuario;
import modelo.gestion.GestionEjemplar;
import modelo.gestion.GestionPrestamo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import modelo.gestion.GestionUsuario;

public class MenuGestionPrestamos {
    static Scanner k =  new Scanner(System.in);
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static GestionPrestamo gestionPrestamo = new GestionPrestamo();
    static GestionUsuario gestionUsuario = new GestionUsuario();
    static GestionEjemplar gestionEjemplar = new GestionEjemplar();

    /**
     * Método que imprime el menú principal.
     */
    private static void prtMenu() {
        System.out.println("""
                1 - Añadir un prestamo
                2 - Mostrar un prestamo
                3 - Mostrar todos los prestamos
                4 - Editar un prestamo
                5 - Devolver un préstamo
                6 - Eliminar un prestamo
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
                case 1 -> gestionPrestamo.createPrestamo(getDatosPrestamo());
                case 2 -> gestionPrestamo.readPrestamo(getIdPrestamo());
                case 3 -> gestionPrestamo.readAllPrestamos();
                case 4 -> gestionPrestamo.updatePrestamo(getIdDatosPrestamo());
                case 5 -> gestionPrestamo.devolverPrestamo(getIdPrestamo());
                case 6 -> gestionPrestamo.deletePrestamo(getIdPrestamo());
                case -1 -> salida = true;
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    /**
     * Método que obtiene los datos necesarios para crear un nuevo préstamo con todos los datos, exceptuando el ID.
     * @return Objeto préstamo.
     */
    private static Prestamo getDatosPrestamo() {
        System.out.println("Ingrese el ID del usuario");
        Integer idUsuario = k.nextInt();
        System.out.println("Ingrese el ID del ejemplar");
        Integer idEjemplar = k.nextInt();
        k.nextLine();
        System.out.println("Ingrese la fecha de inicio del prestamo (DD/MM/YYYY)");
        String fechaInicio = k.nextLine();
        System.out.println("Ingrese la fecha de finalización del prestamo (DD/MM/YYYY) en caso de que exista.");
        String fechaFinStr = k.nextLine();
        LocalDate fechaFin = fechaFinStr.isEmpty() ? null : LocalDate.parse(fechaFinStr, dtf);
        return new Prestamo(gestionUsuario.getUsuarioById(idUsuario), gestionEjemplar.getEjemplarById(idEjemplar), LocalDate.parse(fechaInicio, dtf), fechaFin);
    }

    /**
     * Método que obtiene los datos necesarios para crear un nuevo préstamo con el ID.
     * @return Objeto préstamo.
     */
    private static Prestamo getIdPrestamo() {
        System.out.println("Ingrese el ID del prestamo");
        Integer id = k.nextInt();
        return new Prestamo(id, new Usuario(), new Ejemplar(), LocalDate.now(), null);
    }

    /**
     * Método que obtiene los datos necesarios para crear un nuevo préstamo con todos los datos.
     * @return Objeto préstamo.
     */
    private static Prestamo getIdDatosPrestamo() {
        System.out.println("Ingrese el ID del prestamo");
        Integer id = k.nextInt();
        System.out.println("Ingrese el ID del usuario");
        Integer idUsuario = k.nextInt();
        System.out.println("Ingrese el ID del ejemplar");
        Integer idEjemplar = k.nextInt();
        k.nextLine();
        System.out.println("Ingrese la fecha de inicio del prestamo (DD/MM/YYYY)");
        String fechaInicio = k.nextLine();
        System.out.println("Ingrese la fecha de finalización del prestamo (DD/MM/YYYY) en caso de que exista.");
        String fechaFinStr = k.nextLine();
        LocalDate fechaFin = fechaFinStr.isEmpty() ? null : LocalDate.parse(fechaFinStr, dtf);
        return new Prestamo(id, gestionUsuario.getUsuarioById(idUsuario), gestionEjemplar.getEjemplarById(idEjemplar), LocalDate.parse(fechaInicio, dtf), fechaFin);
    }

    /**
     * Método que ejecuta el menú principal.
     */
    public static void run() {
        menu();
    }
}
