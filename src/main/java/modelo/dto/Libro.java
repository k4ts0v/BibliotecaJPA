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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        // Quitar guiones, si tiene.
        if (isbn.matches(".*-.*")) {
            isbn = isbn.replace("-", "");
        }
        if (validarISBN(isbn)) {
            this.isbn = isbn;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Libro() {}

    public Libro(String isbn, String titulo, String autor) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
    }

    public Libro(String autor, String titulo) {
        setAutor(autor);
        setTitulo(titulo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(getIsbn(), libro.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIsbn());
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }

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