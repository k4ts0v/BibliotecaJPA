package modelo.gestion;

import modelo.dao.DAOPrestamo;
import modelo.dto.Prestamo;
import modelo.dto.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestionPrestamo {
    private ArrayList<Prestamo> listaPrestamos;
    private ArrayList<Prestamo> listaPrestamosUsuario;
    private DAOPrestamo daoPrestamo;
    private GestionEjemplar gestionEjemplar;
    private GestionUsuario gestionUsuario;


    /**
     * Constructor de la clase.
     */
    public GestionPrestamo() {
        listaPrestamos = new ArrayList<>();
        listaPrestamosUsuario = new ArrayList<>();
        daoPrestamo = new DAOPrestamo();
        gestionEjemplar = new GestionEjemplar();
        gestionUsuario = new GestionUsuario();
        try {
            listaPrestamos = daoPrestamo.readAll();
        } catch (Exception e) {
            System.out.println("Ocurrió un error leyendo el contenido de la base de datos");
        }
    }

    /**
     * Método que crea un nuevo préstamo en la BD, y lo añade a la lista de préstamos de la memoria.
     * @param prestamo Objeto prestamo a crear.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     */
    public Integer createPrestamo(Prestamo prestamo) {
        try {
            if(verificarNumPrestamos(prestamo.getUsuario()) && !gestionUsuario.verificarPenalizacion(prestamo.getUsuario()) && gestionEjemplar.verificarEstadoEjemplar(prestamo.getEjemplar())) {
                if (daoPrestamo.create(prestamo) == 1) {
                    listaPrestamos.add(prestamo);
                    prestamo.getEjemplar().setEstado("Prestado");
                    return 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al crear el préstamo." + e.getMessage());
        }
        return -1;
    }


    /**
     * Método que lee un préstamo de la BD.
     * @param prestamo Objeto prestamo a leer.
     * @return 1 si se ha encontrado el préstamo, -1 en caso contrario.
     */
    public Integer readPrestamo(Prestamo prestamo) {
        Prestamo prestamoDB = null;
        try {
            prestamoDB = daoPrestamo.read(prestamo);
            if(listaPrestamos.contains(prestamoDB)) {
                System.out.println(prestamoDB);
                return 1;
            }
        } catch (Exception e) {
            System.out.println("No se ha encontrado el préstamo." + e.getMessage());
        }
        return -1;
    }

    /**
     * Método que lee todos los préstamos de la BD.
     * @return 1 si se han leido todos los préstamos, -1 en caso contrario.
     */
    public Integer readAllPrestamos() {
        listaPrestamos.forEach(prestamo -> System.out.println(prestamo));
        return 1;
    }
    public Integer readAllPrestamosByUsuario(Usuario usuario) {
        System.out.println(usuario.toString());
        try {
            listaPrestamosUsuario = daoPrestamo.readAllByUsuario(usuario);
                listaPrestamosUsuario.forEach(prestamo -> System.out.println(prestamo));
                return 1;
        } catch (Exception e) {
            System.out.println("No se han podido leer los préstamos." + e.getMessage());
        }
        return -1;
    }

    /**
     * Método que actualiza un préstamo en la BD y en la lista de préstamos de la memoria.
     * @param prestamo Objeto prestamo a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     */
    public Integer updatePrestamo(Prestamo prestamo) {
        try {
            if(daoPrestamo.update(prestamo) == 1) {
                listaPrestamos.removeIf(l -> l.getId().equals(prestamo.getId()));
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el préstamo." + e.getMessage());
        }
        return -1;
    }

    /**
     * Método que elimina un préstamo de la BD y de la lista de préstamos de la memoria.
     * @param prestamo Objeto prestamo a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     */
    public Integer deletePrestamo(Prestamo prestamo) {
        try {
            if(daoPrestamo.delete(prestamo) == 1) {
                listaPrestamos.remove(prestamo);
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el préstamo." + e.getMessage());
        }
        return -1;
    }

    /**
     * Método que verifica si un usuario no ha superado el límite de préstamos.
     * @param usuario Objeto usuario del que se quiere verificar el número de préstamos.
     * @return Verdadero si el usuario no ha superado el límite de préstamos, falso en caso contrario.
     */
    public boolean verificarNumPrestamos(Usuario usuario) {
        if (daoPrestamo.verificarNumPrestamos(usuario.getId())) {
            return true;
        } else {
            System.out.println("El usuario ha superado el límite de préstamos");
            return false;
        }
    }

    /**
     * Método que obtiene la lista de préstamos de la memoria.
     * @return Lista de préstamos.
     */
    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    /**
     * Método que calcula la penalización del usuario y se la añade.
     * @param prestamo Objeto prestamo.
     */
    public void setPenalizacionUsuario(Prestamo prestamo) {
        System.out.println(prestamo.getUsuario().toString());
        int prestamosFueraPlazo = 0;
        try {
            listaPrestamosUsuario = daoPrestamo.readAllByUsuario(prestamo.getUsuario());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Prestamo p : listaPrestamosUsuario) {
            if(p.getFechaDevolucion() != null) {
                if (p.getFechaDevolucion().isAfter(p.getFechaInicio().plusDays(15))) {
                    prestamosFueraPlazo++;
                }
            }
        }
        if (prestamosFueraPlazo > 0 && prestamosFueraPlazo < 3) {
            prestamo.getUsuario().setPenalizacionHasta(LocalDate.now().plusDays(15L * prestamosFueraPlazo));
        } else {
            prestamo.getUsuario().setPenalizacionHasta(LocalDate.now().plusDays(15L * 3));
        }
    }

    /** Método que devuelve un préstamo. 
     * Pone la fecha de devolución en la fecha actual y actualiza el estado del ejemplar. 
     * Actualiza la penalización del usuario.
     * @param prestamo Objeto prestamo.
     */
    public void devolverPrestamo(Prestamo prestamo) {
        try {
            prestamo = daoPrestamo.read(prestamo);
            prestamo.setFechaDevolucion(LocalDate.now());
            prestamo.getEjemplar().setEstado("Disponible");
            daoPrestamo.update(prestamo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setPenalizacionUsuario(prestamo);
        gestionUsuario.updateUsuario(prestamo.getUsuario());
    }
}
