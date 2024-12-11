/*
 * File: Conexion.java
 * Project: CRUD_EquiposJugadores
 * File Created: Tuesday, 12th November 2024 1:36:42 PM
 * Author: Lucas Villa (k4ts0v@protonmail.com)
 * -----
 * Last Modified: Tuesday, 12th November 2024 7:56:09 PM
 * Modified By: Lucas Villa (k4ts0v@protonmail.com)
 */

package modelo.conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexion {
    EntityManagerFactory emf; // EntityManagerFactory. Maneja los Entitymanager.
    static EntityManager em; // EntityManager. Maneja las conexiones a la base de datos.

    /**
     * Constructor de la clase.
     * Crea un EntityManagerFactory y un EntityManager.
     */
    public Conexion() {
        emf = Persistence.createEntityManagerFactory("persistence");
        em = emf.createEntityManager();
    }

    /**
     * Método que obtiene el EntityManager.
     * @return EntityManager.
     */
    public static EntityManager getConexion() {
        if (em == null) {
            Conexion conexion = new Conexion();
        }
        return em;
    }

    /**
     * Método que cierra las conexiones a la base de datos.
     */
    public void close() {
        em.close();
        emf.close();
    }
}
