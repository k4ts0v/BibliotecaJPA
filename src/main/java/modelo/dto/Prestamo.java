package modelo.dto;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private modelo.dto.Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ejemplar_id", nullable = false)
    private Ejemplar ejemplar;

    @Column(name = "fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fechaDevolucion")
    private LocalDate fechaDevolucion;

    /**
     * Método que obtiene el ID del préstamo.
     * @return ID del préstamo.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método que establece el ID del préstamo.
     * @param id ID del préstamo.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método que obtiene el usuario del préstamo.
     * @return Usuario del préstamo.
     */
    public modelo.dto.Usuario getUsuario() {
        return usuario;
    }

    /**
     * Método que establece el usuario del préstamo.
     * @param usuario Usuario del préstamo.
     */
    public void setUsuario(modelo.dto.Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Método que obtiene el ejemplar del préstamo.
     * @return Ejemplar del préstamo.
     */
    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    /**
     * Método que establece el ejemplar del préstamo.
     * @param ejemplar Ejemplar del préstamo.
     */
    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    /**
     * Método que obtiene la fecha de inicio del préstamo.
     * @return Fecha de inicio del préstamo.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método que establece la fecha de inicio del préstamo.
     * @param fechaInicio Fecha de inicio del préstamo.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método que obtiene la fecha de devolución del préstamo.
     * @return Fecha de devolución del préstamo.
     */
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Método que establece la fecha de devolución del préstamo.
     * @param fechaDevolucion Fecha de devolución del préstamo.
     */
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        if(fechaDevolucion == null) {
            this.fechaDevolucion = null;
        } else {
            this.fechaDevolucion = fechaDevolucion;
        }
    }

    /**
     * Constructor vacío de un préstamo.
     */
    public Prestamo() {}

    /**
     * Constructor de un préstamo con el ID.
     * @param id ID del préstamo.
     * @param usuario Usuario del préstamo.
     * @param ejemplar Ejemplar del préstamo.
     * @param fechaInicio Fecha de inicio del préstamo.
     * @param fechaDevolucion Fecha de devolución del préstamo.
     */
    public Prestamo(Integer id, Usuario usuario, Ejemplar ejemplar, LocalDate fechaInicio, LocalDate fechaDevolucion) {
        setId(id);
        setUsuario(usuario);
        setEjemplar(ejemplar);
        setFechaInicio(fechaInicio);
        setFechaDevolucion(fechaDevolucion);
    }

    /**
     * Constructor de un préstamo sin ID.
     * @param usuario Usuario del préstamo.
     * @param ejemplar Ejemplar del préstamo.
     * @param fechaInicio Fecha de inicio del préstamo.
     * @param fechaDevolucion Fecha de devolución del préstamo.
     */
    public Prestamo(Usuario usuario, Ejemplar ejemplar, LocalDate fechaInicio, LocalDate fechaDevolucion) {
        setUsuario(usuario);
        setEjemplar(ejemplar);
        setFechaInicio(fechaInicio);
        setFechaDevolucion(fechaDevolucion);
    }

    /**
     * Método que compara dos préstamos.
     * @param o Objeto préstamo.
     * @return Verdadero si son iguales, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(getId(), prestamo.getId());
    }

    /**
     * Método que obtiene el hashcode del préstamo, basándose en su ID.
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
        return "Prestamo{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", ejemplar=" + ejemplar +
                ", fechaInicio=" + fechaInicio +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}