package modelo.gestion;

import modelo.dao.DAOEjemplar;
import modelo.dto.Ejemplar;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestionEjemplar {
    private ArrayList<Ejemplar> listaEjemplars;
    private DAOEjemplar daoEjemplar;

    /**
     * Constructor de la clase.
     */
    public GestionEjemplar() {
        listaEjemplars = new ArrayList<>();
        daoEjemplar = new DAOEjemplar();
        try {
            listaEjemplars = daoEjemplar.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que crea un nuevo ejemplar en la BD, y lo añade a la lista de ejemplares de la memoria.
     * @param ejemplar Objeto ejemplar a crear.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     */
    public Integer createEjemplar(Ejemplar ejemplar) {
        try {
            if(daoEjemplar.create(ejemplar) == 1) {
                listaEjemplars.add(ejemplar);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al crear el ejemplar." + e.getMessage());
        }
        return -1;
    }

    /**
     * Método que lee un ejemplar de la BD.
     * @param ejemplar Objeto ejemplar a leer.
     * @return 1 si se ha encontrado el ejemplar, -1 en caso contrario.
     */
    public Integer readEjemplar(Ejemplar ejemplar) {
        Ejemplar ejemplarDB = null;
        try {
            ejemplarDB = daoEjemplar.read(ejemplar);
            if(listaEjemplars.contains(ejemplarDB)) {
                System.out.println(ejemplarDB);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("No se ha encontrado el ejemplar." + e.getMessage());
            throw new RuntimeException(e);
        }
        return -1;
    }

    /**
     * Método que lee todos los ejemplares de la BD.
     * @return 1 si se han leido todos los ejemplares, -1 en caso contrario.
     */
    public Integer readAllEjemplares() {
        listaEjemplars.forEach(ejemplar -> System.out.println(ejemplar));
        return 1;
    }

    /**
     * Método que actualiza un ejemplar en la BD y en la lista de ejemplares de la memoria.
     * @param ejemplar Objeto ejemplar a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     */
    public Integer updateEjemplar(Ejemplar ejemplar) {
        try {
            if(daoEjemplar.update(ejemplar) == 1) {
                listaEjemplars.removeIf(l -> l.getIsbn().equals(ejemplar.getIsbn()));
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el ejemplar." + e.getMessage());
        }
        return -1;
    }

    /* 
     * Método que elimina un ejemplar de la BD y de la lista de ejemplares de la memoria.
     * @param ejemplar Objeto ejemplar a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     */
    public Integer deleteEjemplar(Ejemplar ejemplar) {
        try {
            if(daoEjemplar.delete(ejemplar) == 1) {
                listaEjemplars.remove(ejemplar);
                return 1;
            }
        } catch (SQLException e) {
                System.out.println("Error al eliminar el ejemplar." + e.getMessage());
        }
                return -1;
    }

    /**
     * Método que verifica si el ejemplar está disponible para ser prestado.
     * @param ejemplar Objeto ejemplar.
     * @return Verdadero si el ejemplar está disponible, falso en caso contrario.
     */
    public boolean verificarEstadoEjemplar(Ejemplar ejemplar) {
        if (ejemplar != null && ejemplar.getEstado().equals("Disponible")) {
            return true;
        }
        System.out.println("El ejemplar no está disponible para ser prestado.");
        return false;
    }

    /**
     * Método que obtiene un ejemplar por su ID.
     * @param id ID del ejemplar.
     * @return Objeto ejemplar, si se encuentra. Null en caso contrario.
     */
    public Ejemplar getEjemplarById(Integer id) {
        try {
            return daoEjemplar.findEjemplarById(id);
        } catch (SQLException e) {
            System.out.println("Error obteniendo el ejemplar: " + e.getMessage());
        }
        return null;
    }

    /**
     * Método que obtiene la lista de ejemplares de la memoria.
     * @return Lista de ejemplares.
     */
    public ArrayList<Ejemplar> getListaEjemplares() {
        return listaEjemplars;
    }

    /**
     * Método que imprime el stock de ejemplares. 
     * Solo imprime los ejemplares que están disponibles.
     */
    public void getStock() {
        listaEjemplars.stream()
                .filter(ejemplar -> "Disponible".equals(ejemplar.getEstado()))
                .forEach(System.out::println);
    }

}
