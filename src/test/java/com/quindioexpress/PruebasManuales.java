package com.quindioexpress;

import com.quindioexpress.model.EstadoPaquete;
import com.quindioexpress.model.Paquete;
import com.quindioexpress.model.Repartidor;
import com.quindioexpress.service.CentroDistribucion;

public class PruebasManuales {

    public static void main(String[] args) {

        CentroDistribucion centro = new CentroDistribucion();

        // Registrar repartidores.
        centro.registrarRepartidor(
                new Repartidor("R01", "Carlos", "Armenia", true));

        centro.registrarRepartidor(
                new Repartidor("R02", "Laura", "Pereira", true));

        // Registrar paquetes.
        Paquete p1 = new Paquete(
                "P101", "Armenia", 5.0, 5, 20);

        Paquete p2 = new Paquete(
                "P105", "Armenia", 3.0, 3, 40);

        Paquete p3 = new Paquete(
                "P110", "Pereira", 8.0, 4, 25);

        centro.registrarPaquete(p1);
        centro.registrarPaquete(p2);
        centro.registrarPaquete(p3);

        // Prueba 1: cantidad de paquetes.
        comprobar(
                centro.cantidadPaquetesRegistrados() == 3,
                "Registro de paquetes"
        );

        // Prueba 2: cantidad de repartidores.
        comprobar(
                centro.cantidadRepartidoresRegistrados() == 2,
                "Registro de repartidores"
        );

        // Prueba 3: destinos únicos.
        comprobar(
                centro.cantidadDestinos() == 2,
                "Registro de destinos"
        );

        // Prueba 4: búsqueda por código.
        comprobar(
                centro.buscarPaquete("P105") == p2,
                "Busqueda por codigo"
        );

        // Prueba 5: búsqueda binaria.
        comprobar(
                centro.buscarPaqueteBinario("P110") == p3,
                "Busqueda binaria"
        );

        // Prueba 6: peso total para Armenia.
        comprobar(
                Math.abs(
                        centro.calcularPesoTotalPorDestino("Armenia") - 8.0
                ) < 0.000001,
                "Peso total por destino"
        );

        // Prueba 7: contar prioridad mínima 4.
        comprobar(
                centro.contarPaquetesPorPrioridad(4) == 2,
                "Conteo por prioridad"
        );

        // Prueba 8: paquete más pesado.
        comprobar(
                centro.obtenerPaqueteMasPesado() == p3,
                "Paquete mas pesado"
        );

        // Prueba 9: asignación.
        centro.asignarPaquete("P101", "R01");

        comprobar(
                p1.getEstado() == EstadoPaquete.ASIGNADO,
                "Estado despues de asignar"
        );

        comprobar(
                !centro.buscarRepartidor("R01").estaDisponible(),
                "Repartidor ocupado despues de asignar"
        );

        // Prueba 10: entrega.
        centro.registrarEntrega("P101");

        comprobar(
                p1.getEstado() == EstadoPaquete.ENTREGADO,
                "Estado despues de entregar"
        );

        comprobar(
                centro.buscarRepartidor("R01").estaDisponible(),
                "Repartidor disponible despues de entregar"
        );

        comprobar(
                centro.cantidadEntregas() == 1,
                "Historial de entregas"
        );

        // Prueba 11: rechazar códigos duplicados.
        boolean duplicadoRechazado = false;

        try {
            centro.registrarPaquete(
                    new Paquete("P101", "Armenia", 2.0, 1, 50)
            );
        } catch (IllegalArgumentException e) {
            duplicadoRechazado = true;
        }

        comprobar(
                duplicadoRechazado,
                "Rechazo de codigos duplicados"
        );

        System.out.println("\nTodas las pruebas terminaron correctamente.");
    }

    private static void comprobar(
            boolean condicion,
            String nombrePrueba) {

        if (!condicion) {
            throw new AssertionError(
                    "FALLO: " + nombrePrueba
            );
        }

        System.out.println("OK: " + nombrePrueba);
    }
}