package modelo.dto;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    private modelo.dto.Libro isbn;

    @ColumnDefault("'Disponible'")
    @Lob
    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "ejemplar")
    private Set<modelo.dto.Prestamo> prestamos = new LinkedHashSet<>();

    /**
     * Método que obtiene el ID del ejemplar.
     * @return ID del ejemplar.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método que establece el ID del ejemplar.
     * @param id ID del ejemplar.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método que obtiene el ISBN del libro.
     * @return ISBN del libro.
     */
    public modelo.dto.Libro getIsbn() {
        return isbn;
    }

    /**
     * Método que establece el ISBN del libro.
     * @param isbn ISBN del libro.
     */
    public void setIsbn(modelo.dto.Libro isbn) {
        this.isbn = isbn;
    }

    /**
     * Método que obtiene el estado del ejemplar.
     * @return Estado del ejemplar.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Método que establece el estado del ejemplar.
     * @param estado Estado del ejemplar.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Método que obtiene el set de préstamos.
     * @return Set de préstamos.
     */
    public Set<modelo.dto.Prestamo> getPrestamos() {
        return prestamos;
    }

    /**
     * Método que establece el set de préstamos.
     */
    public void setPrestamos() {
        this.prestamos = new LinkedHashSet<>();
    }

    /**
     * Constructor vacío de un ejemplar.
     */
    public Ejemplar() {}

    /**
     * Constructor de un ejemplar sin ID.
     * @param isbn ISBN del libro.
     * @param estado Estado del ejemplar.
     */
    public Ejemplar(Libro isbn, String estado) {
        setIsbn(isbn);
        setEstado(estado);
        setPrestamos();
    }

    /**
     * Constructor de un ejemplar con el ID.
     * @param id ID del ejemplar.
     * @param isbn ISBN del libro.
     * @param estado Estado del ejemplar.
     */
    public Ejemplar(Integer id, Libro isbn, String estado) {
        setId(id);
        setIsbn(isbn);
        setEstado(estado);
        setPrestamos();
    }

    /**
     * Método que compara dos ejemplares.
     * @param o Objeto ejemplar.
     * @return Verdadero si son iguales, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ejemplar ejemplar = (Ejemplar) o;
        return Objects.equals(getId(), ejemplar.getId());
    }

    /**
     * Método que obtiene el hashcode del ejemplar, basándose en su ID.
     * @return Hashcode del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /**
     * Método que imprime el objeto.
     * @return String con la representación del objeto.
     */
    @Override
    public String toString() {
        return "Ejemplar{" +
                "id=" + id +
                ", isbn=" + isbn +
                ", estado='" + estado + '\'' +
                //", prestamos=" + prestamos +
                '}';
    }
}