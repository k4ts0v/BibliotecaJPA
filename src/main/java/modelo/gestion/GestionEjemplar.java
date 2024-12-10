package modelo.gestion;

import modelo.dao.DAOEjemplar;
import modelo.dto.Ejemplar;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestionEjemplar {
    private ArrayList<Ejemplar> listaEjemplars;
    private DAOEjemplar daoEjemplar;

    public GestionEjemplar() {
        listaEjemplars = new ArrayList<>();
        daoEjemplar = new DAOEjemplar();
        try {
            listaEjemplars = daoEjemplar.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public Integer readAllEjemplares() {
        listaEjemplars.forEach(ejemplar -> System.out.println(ejemplar));
        return 1;
    }

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

    public boolean verificarEstadoEjemplar(Ejemplar ejemplar) {
        if (ejemplar != null && ejemplar.getEstado().equals("Disponible")) {
            return true;
        }
        System.out.println("El ejemplar no está disponible para ser prestado.");
        return false;
    }

    public Ejemplar getEjemplarById(Integer id) {
        try {
            return daoEjemplar.findEjemplarById(id);
        } catch (SQLException e) {
            System.out.println("Error obteniendo el ejemplar: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Ejemplar> getListaEjemplares() {
        return listaEjemplars;
    }

    public void getStock() {
        listaEjemplars.stream()
                .filter(ejemplar -> "Disponible".equals(ejemplar.getEstado()))
                .forEach(System.out::println);
    }

}
