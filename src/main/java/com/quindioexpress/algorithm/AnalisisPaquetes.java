package com.quindioexpress.algorithm;

import com.quindioexpress.model.Paquete;

import java.util.List;
import java.util.Objects;

public class AnalisisPaquetes {

    private AnalisisPaquetes() {
        // Evita crear objetos de esta clase.
    }

    // --------------------------------------------------
    // 1. BUSQUEDA BINARIA POR CODIGO
    // La lista debe estar ordenada por codigo ascendente.
    // --------------------------------------------------

    public static Paquete busquedaBinaria(
            List<Paquete> paquetes,
            String codigo) {

        Objects.requireNonNull(paquetes, "La lista no puede ser nula.");
        Objects.requireNonNull(codigo, "El codigo no puede ser nulo.");

        int izquierda = 0;
        int derecha = paquetes.size() - 1;

        while (izquierda <= derecha) {

            int medio = izquierda + (derecha - izquierda) / 2;

            Paquete paquete = paquetes.get(medio);

            int comparacion = paquete.getCodigo().compareTo(codigo);

            if (comparacion == 0) {
                return paquete;
            }

            if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }

        return null;
    }

    // --------------------------------------------------
    // 2. PESO TOTAL POR DESTINO (RECURSIVO)
    // --------------------------------------------------

    public static double pesoTotalPorDestino(
            List<Paquete> paquetes,
            String destino) {

        Objects.requireNonNull(paquetes, "La lista no puede ser nula.");
        Objects.requireNonNull(destino, "El destino no puede ser nulo.");

        return sumarPesos(paquetes, destino, 0);
    }

    private static double sumarPesos(
            List<Paquete> paquetes,
            String destino,
            int indice) {

        if (indice >= paquetes.size()) {
            return 0;
        }

        Paquete paquete = paquetes.get(indice);

        double pesoActual = 0;

        if (paquete.getDestino().equalsIgnoreCase(destino)) {
            pesoActual = paquete.getPeso();
        }

        return pesoActual + sumarPesos(
                paquetes,
                destino,
                indice + 1
        );
    }

    // --------------------------------------------------
    // 3. CONTAR PAQUETES CON PRIORIDAD MINIMA (RECURSIVO)
    // --------------------------------------------------

    public static int contarPorPrioridad(
            List<Paquete> paquetes,
            int prioridadMinima) {

        Objects.requireNonNull(paquetes, "La lista no puede ser nula.");

        if (prioridadMinima < 1 || prioridadMinima > 5) {
            throw new IllegalArgumentException(
                    "La prioridad minima debe estar entre 1 y 5."
            );
        }

        return contarPorPrioridad(
                paquetes,
                prioridadMinima,
                0
        );
    }

    private static int contarPorPrioridad(
            List<Paquete> paquetes,
            int prioridadMinima,
            int indice) {

        if (indice >= paquetes.size()) {
            return 0;
        }

        int cantidadActual = 0;

        if (paquetes.get(indice).getPrioridad() >= prioridadMinima) {
            cantidadActual = 1;
        }

        return cantidadActual + contarPorPrioridad(
                paquetes,
                prioridadMinima,
                indice + 1
        );
    }

    // --------------------------------------------------
    // 4. PAQUETE DE MAYOR PESO (DIVIDE Y VENCERAS)
    // --------------------------------------------------

    public static Paquete paqueteMasPesado(List<Paquete> paquetes) {

        Objects.requireNonNull(paquetes, "La lista no puede ser nula.");

        if (paquetes.isEmpty()) {
            return null;
        }

        return buscarMasPesado(
                paquetes,
                0,
                paquetes.size() - 1
        );
    }

    private static Paquete buscarMasPesado(
            List<Paquete> paquetes,
            int izquierda,
            int derecha) {

        // Caso base: solo hay un paquete.
        if (izquierda == derecha) {
            return paquetes.get(izquierda);
        }

        // Dividir el intervalo en dos partes.
        int medio = izquierda + (derecha - izquierda) / 2;

        Paquete paqueteIzquierdo = buscarMasPesado(
                paquetes,
                izquierda,
                medio
        );

        Paquete paqueteDerecho = buscarMasPesado(
                paquetes,
                medio + 1,
                derecha
        );

        // Comparar los resultados de ambas partes.
        if (paqueteIzquierdo.getPeso() >= paqueteDerecho.getPeso()) {
            return paqueteIzquierdo;
        }

        return paqueteDerecho;
    }
}