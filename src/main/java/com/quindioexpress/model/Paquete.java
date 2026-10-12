package com.quindioexpress.model;

import java.util.Objects;

public class Paquete implements Comparable<Paquete> {

    private final String codigo;
    private final String destino;
    private final double peso;
    private final int prioridad;
    private final int tiempoEstimado;

    private EstadoPaquete estado;
    private Repartidor repartidorAsignado;

    public Paquete(
            String codigo,
            String destino,
            double peso,
            int prioridad,
            int tiempoEstimado) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El codigo del paquete no puede ser nulo ni vacio."
            );
        }

        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException(
                    "El destino no puede ser nulo ni vacio."
            );
        }

        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser mayor que cero."
            );
        }

        if (prioridad < 1 || prioridad > 5) {
            throw new IllegalArgumentException(
                    "La prioridad debe estar entre 1 y 5."
            );
        }

        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException(
                    "El tiempo estimado debe ser mayor que cero."
            );
        }

        this.codigo = codigo.trim();
        this.destino = destino.trim();
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempoEstimado = tiempoEstimado;

        this.estado = EstadoPaquete.PENDIENTE;
        this.repartidorAsignado = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public Repartidor getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public void asignarA(Repartidor repartidor) {

        Objects.requireNonNull(
                repartidor,
                "El repartidor no puede ser nulo."
        );

        if (estado != EstadoPaquete.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se pueden asignar paquetes pendientes."
            );
        }

        if (!repartidor.estaDisponible()) {
            throw new IllegalStateException(
                    "El repartidor no esta disponible."
            );
        }

        if (!destino.equalsIgnoreCase(repartidor.getZona())) {
            throw new IllegalArgumentException(
                    "La zona del repartidor no coincide con el destino."
            );
        }

        this.repartidorAsignado = repartidor;
        this.estado = EstadoPaquete.ASIGNADO;

        repartidor.setDisponible(false);
    }

    public void registrarEntrega() {

        if (estado != EstadoPaquete.ASIGNADO) {
            throw new IllegalStateException(
                    "Solo se pueden entregar paquetes asignados."
            );
        }

        this.estado = EstadoPaquete.ENTREGADO;

        repartidorAsignado.setDisponible(true);
    }

    @Override
    public int compareTo(Paquete otro) {

        Objects.requireNonNull(
                otro,
                "El paquete que se va a comparar no puede ser nulo."
        );

        return this.codigo.compareTo(otro.codigo);
    }

    @Override
    public boolean equals(Object objeto) {

        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Paquete)) {
            return false;
        }

        Paquete otro = (Paquete) objeto;

        return codigo.equals(otro.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "codigo='" + codigo + '\'' +
                ", destino='" + destino + '\'' +
                ", peso=" + peso +
                ", prioridad=" + prioridad +
                ", tiempoEstimado=" + tiempoEstimado +
                ", estado=" + estado +
                '}';
    }
}