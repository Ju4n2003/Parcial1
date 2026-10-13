
package com.quindioexpress.comparator;

import com.quindioexpress.model.Paquete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparacionBusquedaPaquetes {

    public static Paquete buscarEnLista(
            List<Paquete> paquetes, String codigo) {

        for (Paquete paquete : paquetes) {
            if (paquete.getCodigo().equals(codigo)) {
                return paquete;
            }
        }

        return null;
    }

    public static void probar(int cantidad) {

        List<Paquete> lista = new ArrayList<>();
        Map<String, Paquete> mapa = new HashMap<>();


        for (int i = 0; i < cantidad; i++) {

            String codigo = String.format("P%06d", i);

            Paquete paquete = new Paquete(
                    codigo,
                    "Armenia",
                    2.5,
                    3,
                    30
            );

            lista.add(paquete);
            mapa.put(paquete.getCodigo(), paquete);
        }


        String codigoBuscado =
                String.format("P%06d", cantidad - 1);

        int repeticiones = 100;

        long inicioLista = System.nanoTime();

        for (int i = 0; i < repeticiones; i++) {
            buscarEnLista(lista, codigoBuscado);
        }

        long tiempoLista =
                System.nanoTime() - inicioLista;


        long inicioMapa = System.nanoTime();

        for (int i = 0; i < repeticiones; i++) {
            mapa.get(codigoBuscado);
        }

        long tiempoMapa =
                System.nanoTime() - inicioMapa;

        System.out.println("\nCantidad de paquetes: " + cantidad);

        System.out.printf(
                "ArrayList: %.3f ms%n",
                tiempoLista / 1_000_000.0
        );

        System.out.printf(
                "HashMap:   %.3f ms%n",
                tiempoMapa / 1_000_000.0
        );

        System.out.println(
                "Paquete encontrado: "
                        + (mapa.get(codigoBuscado) != null)
        );
    }

    public static void main(String[] args) {

        probar(30);
        probar(500_000);
    }
}
