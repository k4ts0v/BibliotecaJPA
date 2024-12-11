package modelo.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dni", nullable = false, length = 15)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Lob
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "penalizacionHasta")
    private LocalDate penalizacionHasta;

    /**
     * Método que obtiene el ID del usuario.
     * @return ID del usuario.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método que establece el ID del usuario.
     * @param id ID del usuario.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método que obtiene el DNI del usuario.
     * @return DNI del usuario.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Método que establece el DNI del usuario.
     * @param dni DNI del usuario.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Método que obtiene el nombre del usuario.
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que establece el nombre del usuario.
     * @param nombre Nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que obtiene el email del usuario.
     * @return Email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que establece el email del usuario.
     * @param email Email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que obtiene la contraseña del usuario.
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que establece la contraseña del usuario.
     * @param password Contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método que obtiene el tipo del usuario.
     * @return Tipo del usuario.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método que establece el tipo del usuario.
     * @param tipo Tipo del usuario.
     */
    public void setTipo(String tipo) {
        if (tipo.equalsIgnoreCase("Administrador") || tipo.equalsIgnoreCase("Normal")) {
            this.tipo = tipo;
        }
    }

    /**
     * Método que obtiene la fecha de penalización del usuario.
     * @return Fecha de penalización del usuario.
     */
    public LocalDate getPenalizacionHasta() {
        return penalizacionHasta;
    }

    /**
     * Método que establece la fecha de penalización del usuario.
     * @param penalizacionHasta Fecha de penalización del usuario.
     */
    public void setPenalizacionHasta(LocalDate penalizacionHasta) {
        if (penalizacionHasta == null) {
            penalizacionHasta = null;
        } else {
            this.penalizacionHasta = penalizacionHasta;
        }
    }

    /**
     * Constructor vacío de un usuario.
     */
    public Usuario() {}

    /**
     * Constructor de un usuario con el ID.
     * @param id ID del usuario.
     * @param dni DNI del usuario.
     * @param nombre Nombre del usuario.
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     * @param tipo Tipo del usuario.
     * @param penalizacionHasta Fecha de penalización del usuario.
     */
    public Usuario(Integer id, String dni, String nombre, String email, String password, String tipo, LocalDate penalizacionHasta) {
        setId(id);
        setDni(dni);
        setNombre(nombre);
        setEmail(email);
        setPassword(password);
        setTipo(tipo);
        setPenalizacionHasta(penalizacionHasta);
    }

    /**
     * Constructor de un usuario sin ID.
     * @param dni DNI del usuario.
     * @param nombre Nombre del usuario.
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     * @param tipo Tipo del usuario.
     * @param penalizacionHasta Fecha de penalización del usuario.
     */
    public Usuario(String dni, String nombre, String email, String password, String tipo, LocalDate penalizacionHasta) {
        setDni(dni);
        setNombre(nombre);
        setEmail(email);
        setPassword(password);
        setTipo(tipo);
        setPenalizacionHasta(penalizacionHasta);
    }

    /**
     * Método que compara dos usuarios.
     * @param o Objeto usuario.
     * @return Verdadero si son iguales, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getId(), usuario.getId());
    }

    /**
     * Método que obtiene el hashcode del usuario, basándose en su ID.
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
        return "Usuario{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tipo='" + tipo + '\'' +
                ", penalizacionHasta=" + penalizacionHasta +
                '}';
    }
}