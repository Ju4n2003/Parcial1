package com.quindioexpress.app;

import com.quindioexpress.model.Paquete;
import com.quindioexpress.model.Repartidor;
import com.quindioexpress.service.CentroDistribucion;

public class Main {

    public static void main(String[] args) {

        CentroDistribucion centro = new CentroDistribucion();

        // --------------------------------------------
        // 1. REGISTRAR REPARTIDORES
        // --------------------------------------------

        centro.registrarRepartidor(
                new Repartidor("R01", "Carlos", "Armenia", true));

        centro.registrarRepartidor(
                new Repartidor("R02", "Laura", "Pereira", true));

        centro.registrarRepartidor(
                new Repartidor("R03", "Andres", "Calarca", true));

        centro.registrarRepartidor(
                new Repartidor("R04", "Sofia", "Montenegro", true));

        centro.registrarRepartidor(
                new Repartidor("R05", "Daniel", "Circasia", true));

        centro.registrarRepartidor(
                new Repartidor("R06", "Valentina", "Armenia", true));

        // --------------------------------------------
        // 2. REGISTRAR 30 PAQUETES
        // --------------------------------------------

        centro.registrarPaquete(
                new Paquete("P105", "Armenia", 4.5, 5, 30));

        centro.registrarPaquete(
                new Paquete("P102", "Pereira", 2.3, 3, 45));

        centro.registrarPaquete(
                new Paquete("P118", "Calarca", 8.0, 4, 25));

        centro.registrarPaquete(
                new Paquete("P111", "Montenegro", 1.5, 2, 60));

        centro.registrarPaquete(
                new Paquete("P130", "Circasia", 6.2, 5, 20));

        centro.registrarPaquete(
                new Paquete("P107", "Armenia", 3.1, 4, 35));

        centro.registrarPaquete(
                new Paquete("P125", "Pereira", 9.4, 2, 50));

        centro.registrarPaquete(
                new Paquete("P109", "Calarca", 2.7, 1, 70));

        centro.registrarPaquete(
                new Paquete("P140", "Montenegro", 5.6, 3, 40));

        centro.registrarPaquete(
                new Paquete("P115", "Circasia", 7.8, 4, 25));

        centro.registrarPaquete(
                new Paquete("P103", "Armenia", 1.2, 2, 55));

        centro.registrarPaquete(
                new Paquete("P122", "Pereira", 4.8, 5, 30));

        centro.registrarPaquete(
                new Paquete("P135", "Calarca", 6.5, 3, 45));

        centro.registrarPaquete(
                new Paquete("P114", "Montenegro", 2.0, 1, 80));

        centro.registrarPaquete(
                new Paquete("P128", "Circasia", 3.9, 4, 35));

        centro.registrarPaquete(
                new Paquete("P101", "Armenia", 10.5, 5, 15));

        centro.registrarPaquete(
                new Paquete("P119", "Pereira", 2.8, 2, 65));

        centro.registrarPaquete(
                new Paquete("P145", "Calarca", 4.1, 5, 20));

        centro.registrarPaquete(
                new Paquete("P106", "Montenegro", 8.7, 3, 40));

        centro.registrarPaquete(
                new Paquete("P133", "Circasia", 1.8, 2, 60));

        centro.registrarPaquete(
                new Paquete("P117", "Armenia", 5.3, 3, 45));

        centro.registrarPaquete(
                new Paquete("P124", "Pereira", 7.1, 4, 25));

        centro.registrarPaquete(
                new Paquete("P108", "Calarca", 3.6, 2, 50));

        centro.registrarPaquete(
                new Paquete("P138", "Montenegro", 9.2, 5, 18));

        centro.registrarPaquete(
                new Paquete("P112", "Circasia", 2.4, 1, 75));

        centro.registrarPaquete(
                new Paquete("P126", "Armenia", 6.8, 4, 28));

        centro.registrarPaquete(
                new Paquete("P104", "Pereira", 1.6, 1, 90));

        centro.registrarPaquete(
                new Paquete("P143", "Calarca", 5.9, 3, 38));

        centro.registrarPaquete(
                new Paquete("P116", "Montenegro", 4.3, 4, 32));

        centro.registrarPaquete(
                new Paquete("P132", "Circasia", 11.0, 5, 12));

        // --------------------------------------------
        // 3. MOSTRAR RESUMEN DEL CENTRO
        // --------------------------------------------

        System.out.println("==================================");
        System.out.println(" CENTRO DE DISTRIBUCION");
        System.out.println("==================================");

        System.out.println(
                "Paquetes registrados: "
                        + centro.cantidadPaquetesRegistrados());

        System.out.println(
                "Repartidores registrados: "
                        + centro.cantidadRepartidoresRegistrados());

        System.out.println(
                "Destinos registrados: "
                        + centro.cantidadDestinos());

        System.out.println(
                "Paquetes pendientes: "
                        + centro.cantidadPaquetesPendientes());

        // --------------------------------------------
        // 4. MOSTRAR DESTINOS ORDENADOS
        // --------------------------------------------

        System.out.println("\nDestinos alfabeticamente:");

        for (String destino : centro.obtenerDestinosOrdenados()) {
            System.out.println("- " + destino);
        }

        // --------------------------------------------
        // 5. BUSCAR UN PAQUETE
        // --------------------------------------------

        System.out.println("\nBusqueda por codigo:");

        Paquete encontrado = centro.buscarPaquete("P118");

        if (encontrado != null) {
            System.out.println(encontrado);
        } else {
            System.out.println("Paquete no encontrado.");
        }

        // --------------------------------------------
        // 6. BUSQUEDA BINARIA
        // --------------------------------------------

        System.out.println("\nBusqueda binaria:");

        Paquete encontradoBinario =
                centro.buscarPaqueteBinario("P130");

        System.out.println(encontradoBinario);

        // --------------------------------------------
        // 7. CALCULAR PESO TOTAL POR DESTINO
        // --------------------------------------------

        System.out.println("\nPeso total para Armenia:");

        System.out.println(
                centro.calcularPesoTotalPorDestino("Armenia")
                        + " kg");

        // --------------------------------------------
        // 8. CONTAR PAQUETES POR PRIORIDAD
        // --------------------------------------------

        System.out.println("\nPaquetes con prioridad >= 4:");

        System.out.println(
                centro.contarPaquetesPorPrioridad(4));

        // --------------------------------------------
        // 9. PAQUETE MAS PESADO
        // --------------------------------------------

        System.out.println("\nPaquete mas pesado:");

        System.out.println(centro.obtenerPaqueteMasPesado());

        // --------------------------------------------
        // 10. MOSTRAR SIGUIENTE PAQUETE
        // --------------------------------------------

        System.out.println("\nSiguiente paquete para despacho:");

        System.out.println(centro.obtenerSiguientePaquete());

        // --------------------------------------------
        // 11. ASIGNAR Y ENTREGAR UN PAQUETE
        // --------------------------------------------

        System.out.println("\nAsignando paquete P105 a R01...");

        centro.asignarPaquete("P105", "R01");

        System.out.println(
                "Estado del paquete: "
                        + centro.buscarPaquete("P105").getEstado());

        System.out.println(
                "Disponibilidad de Carlos: "
                        + centro.buscarRepartidor("R01").estaDisponible());

        System.out.println("\nRegistrando entrega de P105...");

        centro.registrarEntrega("P105");

        System.out.println(
                "Estado final: "
                        + centro.buscarPaquete("P105").getEstado());

        System.out.println(
                "Disponibilidad de Carlos: "
                        + centro.buscarRepartidor("R01").estaDisponible());

        System.out.println(
                "Entregas registradas: "
                        + centro.cantidadEntregas());

        System.out.println("\nPrograma finalizado.");
    }
}