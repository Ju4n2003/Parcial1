package com.quindioexpress.model;

public class Repartidor {

    private final String identificacion;
    private final String nombre;
    private final String zona;
    private boolean disponible;

    public Repartidor(
            String identificacion,
            String nombre,
            String zona,
            boolean disponible) {

        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException(
                    "La identificacion no puede ser nula ni vacia."
            );
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede ser nulo ni vacio."
            );
        }

        if (zona == null || zona.isBlank()) {
            throw new IllegalArgumentException(
                    "La zona no puede ser nula ni vacia."
            );
        }

        this.identificacion = identificacion.trim();
        this.nombre = nombre.trim();
        this.zona = zona.trim();
        this.disponible = disponible;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getZona() {
        return zona;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", zona='" + zona + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}