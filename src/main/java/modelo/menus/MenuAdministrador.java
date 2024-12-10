package modelo.menus;

import modelo.dto.Usuario;
import modelo.gestion.GestionUsuario;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuAdministrador {
    static Scanner k =  new Scanner(System.in);
    MenuGestionUsuarios menu = new MenuGestionUsuarios();
    private static void prtMenu() {
        System.out.println("""
                1 - Gestión de usuarios
                2 - Gestión de libros
                3 - Gestión de ejemplares
                4 - Gestión de préstamos
                -1 - Salir
                """);
    }

    private static void menu() {
        boolean salida = false;
        while (!salida) {
            prtMenu();
            int opcion = k.nextInt();
            switch (opcion) {
                case 1 -> MenuGestionUsuarios.run();
                case 2 -> MenuGestionLibros.run();
                case 3 -> MenuGestionEjemplares.run();
                case 4 -> MenuGestionPrestamos.run();
                case -1 -> salida = true;
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    public static void run() {
        menu();
    }
}
