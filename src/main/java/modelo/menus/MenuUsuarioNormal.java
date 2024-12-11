package modelo.menus;

import modelo.dto.Ejemplar;
import modelo.dto.Prestamo;
import modelo.dto.Usuario;
import modelo.gestion.GestionEjemplar;
import modelo.gestion.GestionPrestamo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuUsuarioNormal {
    static Scanner k =  new Scanner(System.in);
    static MenuPrincipal menuPrincipal = new MenuPrincipal();
    static GestionPrestamo gestionPrestamo = new GestionPrestamo();
    static GestionEjemplar gestionEjemplar = new GestionEjemplar();
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Método que imprime el menú principal.
     */
    private static void prtMenu() {
        System.out.println("""
                Aquí puede gestionar los préstamos que haya realizado en la biblioteca.
                1 - Hacer un préstamo
                2 - Ver un préstamo
                3 - Ver todos los préstamos
                4 - Editar un préstamo existente
                5 - Borrar un préstamo existente
                6 - Devolver un préstamo
                -1 - Salir
                Seleccione una opción:
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
                case 3 -> gestionPrestamo.readAllPrestamosByUsuario(getUsuarioLogado());
                case 4 -> gestionPrestamo.updatePrestamo(getIdDatosPrestamo());
                case 5 -> gestionPrestamo.deletePrestamo(getIdPrestamo());
                case 6 -> gestionPrestamo.devolverPrestamo(getIdPrestamo());
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
        System.out.println("Ingrese el ID del ejemplar");
        Integer idEjemplar = k.nextInt();
        k.nextLine();
        System.out.println("Ingrese la fecha de inicio del prestamo (DD/MM/YYYY)");
        String fechaInicio = k.nextLine();
        System.out.println("Ingrese la fecha de finalización del prestamo (DD/MM/YYYY) en caso de que exista.");
        String fechaFinStr = k.nextLine();
        LocalDate fechaFin = fechaFinStr.isEmpty() ? null  : LocalDate.parse(fechaFinStr, dtf);
        return new Prestamo(getUsuarioLogado(), gestionEjemplar.getEjemplarById(idEjemplar), LocalDate.parse(fechaInicio, dtf), fechaFin);
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
        k.nextLine();
        System.out.println("Ingrese el ID del ejemplar");
        Integer idEjemplar = k.nextInt();
        k.nextLine();
        System.out.println("Ingrese la fecha de inicio del prestamo (DD/MM/YYYY)");
        String fechaInicio = k.nextLine();
        System.out.println("Ingrese la fecha de finalización del prestamo (DD/MM/YYYY) en caso de que exista.");
        String fechaFinStr = k.nextLine();
        LocalDate fechaFin = fechaFinStr.isEmpty() ? null  : LocalDate.parse(fechaFinStr, dtf);
        return new Prestamo(id, getUsuarioLogado(), gestionEjemplar.getEjemplarById(idEjemplar), LocalDate.parse(fechaInicio, dtf), fechaFin);
    }

    /**
     * Método que obtiene el usuario logueado.
     * @return Objeto usuario, que contiene el usuario que ha iniciado sesión.
     */
    private static Usuario getUsuarioLogado() {
        return MenuPrincipal.getUsuarioLogado();
    }

    /**
     * Método que ejecuta el menú principal.
     */
    public static void run() {
        menu();
    }
}
