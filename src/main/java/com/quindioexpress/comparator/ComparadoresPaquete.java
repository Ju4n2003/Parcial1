package com.quindioexpress.comparator;

import com.quindioexpress.model.Paquete;
import java.util.Comparator;

public class ComparadoresPaquete {

    private ComparadoresPaquete() {
        // Evita crear objetos de esta clase.
    }

    // Prioridad mayor primero.
    public static final Comparator<Paquete> POR_PRIORIDAD =
            Comparator.comparingInt(Paquete::getPrioridad)
                    .reversed()
                    .thenComparing(Paquete::getCodigo);

    // Paquetes de mayor peso primero.
    public static final Comparator<Paquete> POR_PESO =
            Comparator.comparingDouble(Paquete::getPeso)
                    .reversed()
                    .thenComparing(Paquete::getCodigo);

    // Menor tiempo estimado primero.
    public static final Comparator<Paquete> POR_TIEMPO =
            Comparator.comparingInt(Paquete::getTiempoEstimado)
                    .thenComparing(Paquete::getCodigo);
}