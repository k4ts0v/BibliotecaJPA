package modelo.menus;

import modelo.dto.Usuario;
import modelo.gestion.GestionUsuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuGestionUsuarios {
    static Scanner k =  new Scanner(System.in);
    static GestionUsuario gestionUsuario = new GestionUsuario();
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static void prtMenu() {
        System.out.println("""
                1 - Añadir un usuario
                2 - Mostrar un usuario
                3 - Mostrar todos los usuarios
                4 - Actualizar un usuario
                5 - Eliminar un usuario
                -1 - Salir
                """);
    }

    private static void menu() {
        boolean salida = false;
        while (!salida) {
            prtMenu();
            int opcion = k.nextInt();
            switch (opcion) {
                case 1 -> gestionUsuario.createUsuario(getDatosUsuario());
                case 2 -> gestionUsuario.readUsuario(getIdUsuario());
                case 3 -> gestionUsuario.readAllUsuarios();
                case 4 -> gestionUsuario.updateUsuario(getIdDatosUsuario());
                case 5 -> gestionUsuario.deleteUsuario(getIdUsuario());
                case -1 -> salida = true;
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    private static Usuario getDatosUsuario() {
        k.nextLine();
        System.out.print("Ingrese el DNI del usuario:");
        String dni = k.nextLine();
        System.out.print("Ingrese el nombre del usuario");
        String nombre = k.nextLine();
        System.out.print("Ingrese el email del usuario");
        String email = k.nextLine();
        System.out.print("Ingrese la contraseña del usuario");
        String password = k.nextLine();
        System.out.print("Ingrese el tipo de usuario");
        String tipo = k.nextLine();
        System.out.println("Introduzca la fecha máxima de penalización");
        String penalizacionHastaStr = k.nextLine();
        LocalDate penalizacionHasta = penalizacionHastaStr.isEmpty() ? null : LocalDate.parse(penalizacionHastaStr, dtf);
        return new Usuario(dni, nombre, email, password, tipo, penalizacionHasta);
    }

    private static Usuario getIdUsuario() {
        k.nextLine();
        System.out.print("Ingrese el ID del usuario");
        int id = k.nextInt();
        return new Usuario(id, "", "", "", "", "", null);
    }

    private static Usuario getIdDatosUsuario() {
        k.nextLine();
        System.out.print("Ingrese el ID del usuario");
        int id = k.nextInt();
        k.nextLine();
        System.out.print("Ingrese el DNI del usuario:");
        String dni = k.nextLine();
        System.out.print("Ingrese el nombre del usuario");
        String nombre = k.nextLine();
        System.out.print("Ingrese el email del usuario");
        String email = k.nextLine();
        System.out.print("Ingrese la contraseña del usuario");
        String password = k.nextLine();
        System.out.print("Ingrese el tipo de usuario");
        String tipo = k.nextLine();
        String penalizacionHastaStr = k.nextLine();
        LocalDate penalizacionHasta = penalizacionHastaStr.isEmpty() ? null : LocalDate.parse(penalizacionHastaStr, dtf);
        return new Usuario(id, dni, nombre, email, password, tipo, penalizacionHasta);
    }

    public static void run() {
        menu();
    }
}
