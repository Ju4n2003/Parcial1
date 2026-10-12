package com.quindioexpress.service;

import com.quindioexpress.algorithm.AnalisisPaquetes;
import com.quindioexpress.comparator.ComparadoresPaquete;
import com.quindioexpress.model.EstadoPaquete;
import com.quindioexpress.model.Paquete;
import com.quindioexpress.model.Repartidor;
import com.quindioexpress.structure.ListaEnlazada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class CentroDistribucion {

    // Conserva el orden original de registro.
    private final List<Paquete> paquetesRegistrados;

    // Consulta rapida de paquetes por codigo.
    private final Map<String, Paquete> paquetesPorCodigo;

    // Destinos unicos, sin necesidad de ordenarlos.
    private final Set<String> destinosUnicos;

    // Destinos unicos ordenados alfabeticamente.
    private final TreeSet<String> destinosOrdenados;

    // Destino -> paquetes registrados para ese destino.
    private final Map<String, List<Paquete>> paquetesPorDestino;

    // Identificacion -> repartidor.
    private final Map<String, Repartidor> repartidoresPorId;

    // Cola de despacho de paquetes pendientes.
    private final PriorityQueue<Paquete> colaDespacho;

    // Historial de paquetes entregados.
    private final ListaEnlazada<Paquete> historialEntregas;

    public CentroDistribucion() {

        paquetesRegistrados = new ArrayList<>();

        paquetesPorCodigo = new HashMap<>();

        destinosUnicos = new HashSet<>();

        destinosOrdenados = new TreeSet<>(
                String.CASE_INSENSITIVE_ORDER
        );

        paquetesPorDestino = new HashMap<>();

        repartidoresPorId = new HashMap<>();

        colaDespacho = new PriorityQueue<>(
                ComparadoresPaquete.POR_PRIORIDAD
        );

        historialEntregas = new ListaEnlazada<>();
    }

    // --------------------------------------------------
    // REGISTRAR PAQUETE
    // --------------------------------------------------

    public void registrarPaquete(Paquete paquete) {

        Objects.requireNonNull(
                paquete,
                "El paquete no puede ser nulo."
        );

        String codigo = paquete.getCodigo();

        if (paquetesPorCodigo.containsKey(codigo)) {
            throw new IllegalArgumentException(
                    "Ya existe un paquete con el codigo: " + codigo
            );
        }

        if (paquete.getEstado() != EstadoPaquete.PENDIENTE) {
            throw new IllegalArgumentException(
                    "Solo se pueden registrar paquetes pendientes."
            );
        }

        String destino = paquete.getDestino();

        String claveDestino = buscarClaveDestino(destino);

        if (claveDestino == null) {
            claveDestino = destino;
        }

        // Actualizar el registro principal.
        paquetesRegistrados.add(paquete);
        paquetesPorCodigo.put(codigo, paquete);

        // Registrar el destino.
        destinosUnicos.add(claveDestino);
        destinosOrdenados.add(claveDestino);

        // Registrar el paquete asociado a su destino.
        paquetesPorDestino
                .computeIfAbsent(
                        claveDestino,
                        clave -> new ArrayList<>()
                )
                .add(paquete);

        // Incorporarlo a la cola de despacho.
        colaDespacho.offer(paquete);
    }

    // --------------------------------------------------
    // REGISTRAR REPARTIDOR
    // --------------------------------------------------

    public void registrarRepartidor(Repartidor repartidor) {

        Objects.requireNonNull(
                repartidor,
                "El repartidor no puede ser nulo."
        );

        String identificacion = repartidor.getIdentificacion();

        if (repartidoresPorId.containsKey(identificacion)) {
            throw new IllegalArgumentException(
                    "Ya existe un repartidor con la identificacion: "
                            + identificacion
            );
        }

        repartidoresPorId.put(identificacion, repartidor);
    }

    // --------------------------------------------------
    // CONSULTAR PAQUETE POR CODIGO
    // --------------------------------------------------

    public Paquete buscarPaquete(String codigo) {

        Objects.requireNonNull(
                codigo,
                "El codigo no puede ser nulo."
        );

        return paquetesPorCodigo.get(codigo);
    }

    // --------------------------------------------------
    // CONSULTAR REPARTIDOR POR IDENTIFICACION
    // --------------------------------------------------

    public Repartidor buscarRepartidor(String identificacion) {

        Objects.requireNonNull(
                identificacion,
                "La identificacion no puede ser nula."
        );

        return repartidoresPorId.get(identificacion);
    }

    // --------------------------------------------------
    // OBTENER PAQUETES EN ORDEN DE REGISTRO
    // --------------------------------------------------

    public List<Paquete> obtenerPaquetesRegistrados() {

        return Collections.unmodifiableList(
                new ArrayList<>(paquetesRegistrados)
        );
    }

    // --------------------------------------------------
    // OBTENER DESTINOS ORDENADOS
    // --------------------------------------------------

    public Set<String> obtenerDestinosOrdenados() {

        return Collections.unmodifiableSet(
                new TreeSet<>(destinosOrdenados)
        );
    }

    // --------------------------------------------------
    // OBTENER PAQUETES POR DESTINO
    // --------------------------------------------------

    public List<Paquete> obtenerPaquetesPorDestino(String destino) {

        Objects.requireNonNull(
                destino,
                "El destino no puede ser nulo."
        );

        String claveDestino = buscarClaveDestino(destino);

        if (claveDestino == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(
                new ArrayList<>(
                        paquetesPorDestino.get(claveDestino)
                )
        );
    }

    // --------------------------------------------------
    // OBTENER SIGUIENTE PAQUETE PARA DESPACHAR
    // --------------------------------------------------

    public Paquete obtenerSiguientePaquete() {
        return colaDespacho.peek();
    }

    // --------------------------------------------------
    // ASIGNAR PAQUETE A REPARTIDOR
    // --------------------------------------------------

    public void asignarPaquete(
            String codigoPaquete,
            String identificacionRepartidor) {

        Paquete paquete = buscarPaquete(codigoPaquete);

        if (paquete == null) {
            throw new IllegalArgumentException(
                    "No existe el paquete: " + codigoPaquete
            );
        }

        Repartidor repartidor = buscarRepartidor(
                identificacionRepartidor
        );

        if (repartidor == null) {
            throw new IllegalArgumentException(
                    "No existe el repartidor: "
                            + identificacionRepartidor
            );
        }

        if (paquete.getEstado() != EstadoPaquete.PENDIENTE) {
            throw new IllegalStateException(
                    "El paquete no esta pendiente."
            );
        }

        if (!repartidor.estaDisponible()) {
            throw new IllegalStateException(
                    "El repartidor no esta disponible."
            );
        }

        if (!paquete.getDestino().equalsIgnoreCase(repartidor.getZona())) {
            throw new IllegalArgumentException(
                    "La zona del repartidor no coincide con el destino."
            );
        }

        // El paquete debe estar en la cola antes de asignarlo.
        if (!colaDespacho.contains(paquete)) {
            throw new IllegalStateException(
                    "El paquete no se encuentra en la cola de despacho."
            );
        }

        // Actualizar el estado del paquete y del repartidor.
        paquete.asignarA(repartidor);

        // Los paquetes asignados dejan de estar pendientes.
        colaDespacho.remove(paquete);
    }

    // --------------------------------------------------
    // REGISTRAR ENTREGA
    // --------------------------------------------------

    public void registrarEntrega(String codigoPaquete) {

        Paquete paquete = buscarPaquete(codigoPaquete);

        if (paquete == null) {
            throw new IllegalArgumentException(
                    "No existe el paquete: " + codigoPaquete
            );
        }

        if (paquete.getEstado() != EstadoPaquete.ASIGNADO) {
            throw new IllegalStateException(
                    "Solo se pueden entregar paquetes asignados."
            );
        }

        // Registrar la entrega en el historial.
        historialEntregas.agregar(paquete);

        // Cambiar el estado y liberar al repartidor.
        paquete.registrarEntrega();
    }

    // --------------------------------------------------
    // CONSULTAR HISTORIAL DE ENTREGAS
    // --------------------------------------------------

    public ListaEnlazada<Paquete> obtenerHistorialEntregas() {
        return historialEntregas;
    }

    // --------------------------------------------------
    // BUSQUEDA BINARIA POR CODIGO
    // --------------------------------------------------

    public Paquete buscarPaqueteBinario(String codigo) {

        Objects.requireNonNull(
                codigo,
                "El codigo no puede ser nulo."
        );

        List<Paquete> ordenados = new ArrayList<>(
                paquetesRegistrados
        );

        Collections.sort(ordenados);

        return AnalisisPaquetes.busquedaBinaria(
                ordenados,
                codigo
        );
    }

    // --------------------------------------------------
    // PESO TOTAL POR DESTINO
    // --------------------------------------------------

    public double calcularPesoTotalPorDestino(String destino) {

        Objects.requireNonNull(
                destino,
                "El destino no puede ser nulo."
        );

        List<Paquete> paquetes = obtenerPaquetesPorDestino(destino);

        return AnalisisPaquetes.pesoTotalPorDestino(
                paquetes,
                destino
        );
    }

    // --------------------------------------------------
    // CONTAR PAQUETES POR PRIORIDAD MINIMA
    // --------------------------------------------------

    public int contarPaquetesPorPrioridad(int prioridadMinima) {

        return AnalisisPaquetes.contarPorPrioridad(
                paquetesRegistrados,
                prioridadMinima
        );
    }

    // --------------------------------------------------
    // ENCONTRAR PAQUETE MAS PESADO
    // --------------------------------------------------

    public Paquete obtenerPaqueteMasPesado() {

        return AnalisisPaquetes.paqueteMasPesado(
                paquetesRegistrados
        );
    }

    // --------------------------------------------------
    // CONSULTAR CANTIDADES
    // --------------------------------------------------

    public int cantidadPaquetesRegistrados() {
        return paquetesRegistrados.size();
    }

    public int cantidadRepartidoresRegistrados() {
        return repartidoresPorId.size();
    }

    public int cantidadDestinos() {
        return destinosUnicos.size();
    }

    public int cantidadPaquetesPendientes() {
        return colaDespacho.size();
    }

    public int cantidadEntregas() {
        return historialEntregas.tamaño();
    }

    // --------------------------------------------------
    // BUSCAR NOMBRE CANONICO DEL DESTINO
    // --------------------------------------------------

    private String buscarClaveDestino(String destino) {

        for (String destinoRegistrado : destinosUnicos) {

            if (destinoRegistrado.equalsIgnoreCase(destino)) {
                return destinoRegistrado;
            }
        }

        return null;
    }
}