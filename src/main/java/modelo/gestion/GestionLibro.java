package modelo.gestion;

import modelo.dao.DAOLibro;
import modelo.dto.Libro;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestionLibro {
    private ArrayList<Libro> listaLibros;
    private DAOLibro daoLibro;

    public GestionLibro() {
        listaLibros = new ArrayList<>();
        daoLibro = new DAOLibro();
        try {
            listaLibros = daoLibro.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer createLibro(Libro libro) {
        try {
            if(daoLibro.create(libro) == 1) {
                listaLibros.add(libro);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al crear el libro."  + e.getMessage());
        }
        return -1;
    }

    public Integer readLibro(Libro libro) {
        try {
            Libro libroDB = daoLibro.read(libro);
            if(listaLibros.contains(libroDB)) {
                System.out.println(libroDB);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("No se ha encontrado el libro."  + e.getMessage());
        }
        return -1;
    }

    public Integer readAllLibros() {
        listaLibros.forEach(libro -> System.out.println(libro));
        return 1;
    }

    public Integer updateLibro(Libro libro) {
        try {
            if(daoLibro.update(libro) == 1) {
                listaLibros.removeIf(l -> l.getIsbn().equals(libro.getIsbn()));
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el libro."  + e.getMessage());
        }
        return -1;
    }

    public Integer deleteLibro(Libro libro) {
        try {
            if(daoLibro.delete(libro) == 1) {
                listaLibros.remove(libro);
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el libro."  + e.getMessage());
        }
        return -1;
    }

    public ArrayList<Libro> getListaLibros() {
        return listaLibros;
    }
}
