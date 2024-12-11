package modelo.gestion;

import modelo.dao.DAOLibro;
import modelo.dto.Libro;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestionLibro {
    private ArrayList<Libro> listaLibros;
    private DAOLibro daoLibro;

    /**
     * Constructor de la clase.
     */
    public GestionLibro() {
        listaLibros = new ArrayList<>();
        daoLibro = new DAOLibro();
        try {
            listaLibros = daoLibro.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que crea un nuevo libro en la BD, y lo añade a la lista de libros de la memoria.
     * @param libro Objeto libro.
     * @return 1 si se ha creado correctamente, -1 en caso contrario.
     */
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

    /**
     * Método que lee un libro de la BD.
     * @param libro Objeto libro a leer.
     * @return 1 si se ha encontrado el libro, -1 en caso contrario.
     */
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

    /**
     * Método que lee todos los libros de la BD.
     * @return 1 si se han leido todos los libros, -1 en caso contrario.
     */
    public Integer readAllLibros() {
        listaLibros.forEach(libro -> System.out.println(libro));
        return 1;
    }

    /**
     * Método que actualiza un libro en la BD y en la lista de libros de la memoria.
     * @param libro Objeto libro a actualizar.
     * @return 1 si se ha actualizado correctamente, -1 en caso contrario.
     */
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

    /**
     * Método que elimina un libro de la BD y de la lista de libros de la memoria.
     * @param libro Objeto libro a eliminar.
     * @return 1 si se ha eliminado correctamente, -1 en caso contrario.
     */
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

    /**
     * Método que obtiene la lista de libros de la memoria.
     * @return Lista de libros.
     */
    public ArrayList<Libro> getListaLibros() {
        return listaLibros;
    }
}
