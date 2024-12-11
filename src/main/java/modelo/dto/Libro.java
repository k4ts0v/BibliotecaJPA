package modelo.dto;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    private String autor;

    /**
     * Método que obtiene el ISBN del libro.
     * @return ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Método que establece el ISBN del libro.
     * @param isbn ISBN del libro.
     */
    public void setIsbn(String isbn) {
        // Quitar guiones, si tiene.
        if (isbn.matches(".*-.*")) {
            isbn = isbn.replace("-", "");
        }
        if (validarISBN(isbn)) {
            this.isbn = isbn;
        }
    }

    /**
     * Método que obtiene el título del libro.
     * @return Título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Método que establece el título del libro.
     * @param titulo Título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Método que obtiene el autor del libro.
     * @return Autor del libro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Método que establece el autor del libro.
     * @param autor Autor del libro.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Constructor vacío de un libro.
     */
    public Libro() {}

    /**
     * Constructor de un libro con ISBN.
     * @param isbn ISBN del libro.
     * @param titulo Título del libro.
     * @param autor Autor del libro.
     */
    public Libro(String isbn, String titulo, String autor) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
    }

    /**
     * Constructor de un libro sin ISBN.
     * @param autor Autor del libro.
     * @param titulo Título del libro.
     */
    public Libro(String autor, String titulo) {
        setAutor(autor);
        setTitulo(titulo);
    }

    /**
     * Método que compara dos libros.
     * @param o Objeto libro.
     * @return Verdadero si son iguales, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(getIsbn(), libro.getIsbn());
    }

    /**
     * Método que obtiene el hashcode del libro, basándose en su ISBN.
     * @return Hashcode del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getIsbn());
    }


    /**
     * Método que imprime el objeto.
     * @return String con la representación del objeto.
     */
    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }

    /**
     * Método que valida si un ISBN es correcto.
     * @param isbn ISBN a validar.
     * @return Verdadero si es correcto, falso en caso contrario.
     */
    public boolean validarISBN(String isbn) {
        // Verificar si el ISBN solo contiene números y tiene 13 dígitos.
        if (isbn.matches("[0-9]{13}")) {
            int sumaTotal = 0;
            // Iterar sobre los 12 primeros caracteres del ISBN,
            for (int i = 0; i < 12; i++) {
                int digit = Character.getNumericValue(isbn.charAt(i));
                // Aplicar pesos: 1 para los números en posiciones impares y 3 para los de posición impar. Se va calculndo la suma total directamente.
                if (i % 2 == 0) {
                    sumaTotal += digit; // Posiciones pares con peso 1.
                } else {
                    sumaTotal += digit * 3; // Posiciones impares con peso 3.
                }
            }
            // Extraer eñ último dígito (de control)
            int digitoControl = Character.getNumericValue(isbn.charAt(12));
            // Verificar si la suma más el dígito son un múltiplo de 10.
            return (sumaTotal + digitoControl) % 10 == 0;
        }
        // Si algo de lo anterior no se cumple, devuelve falso.
        return false;
    }

}