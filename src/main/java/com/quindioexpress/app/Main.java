package com.quindioexpress.app;

import com.quindioexpress.model.Paquete;
import com.quindioexpress.model.Repartidor;
import com.quindioexpress.service.CentroDistribucion;
import java.util.Scanner;

public class Main {

    private static CentroDistribucion centro = new CentroDistribucion();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarDatos();

        int opcion = -1;
        do {
            mostrarMenu();
            System.out.print("Ingrese una opcion: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                System.out.println();
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero valido.");
                opcion = -1;
            }

            if (opcion != 0) {
                System.out.print("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }

        } while (opcion != 0);

        System.out.println("==================================");
        System.out.println("Programa finalizado exitosamente.");
        System.out.println("==================================");
    }

    private static void mostrarMenu() {
        System.out.println("\n==================================");
        System.out.println("   CENTRO DE DISTRIBUCION QUINDIO ");
        System.out.println("==================================");
        System.out.println("1. Ver resumen del centro");
        System.out.println("2. Ver destinos ordenados");
        System.out.println("3. Buscar paquete por codigo (Secuencial)");
        System.out.println("4. Buscar paquete por codigo (Binaria)");
        System.out.println("5. Calcular peso total por destino");
        System.out.println("6. Contar paquetes por prioridad");
        System.out.println("7. Obtener paquete mas pesado");
        System.out.println("8. Obtener siguiente paquete a despachar");
        System.out.println("9. Asignar paquete a repartidor");
        System.out.println("10. Registrar entrega de paquete");
        System.out.println("11. Registrar nuevo paquete");
        System.out.println("0. Salir");
        System.out.println("==================================");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> mostrarResumen();
            case 2 -> listarDestinos();
            case 3 -> buscarPaqueteSecuencial();
            case 4 -> buscarPaqueteBinario();
            case 5 -> calcularPesoPorDestino();
            case 6 -> contarPorPrioridad();
            case 7 -> verPaqueteMasPesado();
            case 8 -> verSiguientePaquete();
            case 9 -> asignarPaquete();
            case 10 -> registrarEntrega();
            case 11 -> registrarNuevoPaquete();
            case 0 -> {}
            default -> System.out.println("Opcion no valida. Intente de nuevo.");
        }
    }

    // --- METODOS DE CADA OPCION ---

    private static void mostrarResumen() {
        System.out.println("--- RESUMEN DEL CENTRO ---");
        System.out.println("Paquetes registrados: " + centro.cantidadPaquetesRegistrados());
        System.out.println("Repartidores registrados: " + centro.cantidadRepartidoresRegistrados());
        System.out.println("Destinos registrados: " + centro.cantidadDestinos());
        System.out.println("Paquetes pendientes: " + centro.cantidadPaquetesPendientes());
        System.out.println("Entregas registradas: " + centro.cantidadEntregas());
    }

    private static void listarDestinos() {
        System.out.println("--- DESTINOS ALFABETICAMENTE ---");
        for (String destino : centro.obtenerDestinosOrdenados()) {
            System.out.println("- " + destino);
        }
    }

    private static void buscarPaqueteSecuencial() {
        System.out.print("Ingrese el codigo del paquete (ej. P118): ");
        String codigo = scanner.nextLine().trim();
        
        Paquete paquete = centro.buscarPaquete(codigo);
        if (paquete != null) {
            System.out.println("Paquete encontrado:");
            System.out.println(paquete);
        } else {
            System.out.println("No se encontro un paquete con el codigo: " + codigo);
        }
    }

    private static void buscarPaqueteBinario() {
        System.out.print("Ingrese el codigo del paquete (ej. P130): ");
        String codigo = scanner.nextLine().trim();
        
        Paquete paquete = centro.buscarPaqueteBinario(codigo);
        if (paquete != null) {
            System.out.println("Paquete encontrado (Busqueda Binaria):");
            System.out.println(paquete);
        } else {
            System.out.println("No se encontro un paquete con el codigo: " + codigo);
        }
    }

    private static void calcularPesoPorDestino() {
        System.out.print("Ingrese la ciudad/destino: ");
        String destino = scanner.nextLine().trim();
        
        double pesoTotal = centro.calcularPesoTotalPorDestino(destino);
        System.out.println("Peso total para " + destino + ": " + pesoTotal + " kg");
    }

    private static void contarPorPrioridad() {
        System.out.print("Ingrese la prioridad minima a filtrar: ");
        try {
            int prioridad = Integer.parseInt(scanner.nextLine().trim());
            int cantidad = centro.contarPaquetesPorPrioridad(prioridad);
            System.out.println("Total de paquetes con prioridad >= " + prioridad + ": " + cantidad);
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un valor numerico.");
        }
    }

    private static void verPaqueteMasPesado() {
        System.out.println("--- PAQUETE MAS PESADO ---");
        Paquete pesado = centro.obtenerPaqueteMasPesado();
        if (pesado != null) {
            System.out.println(pesado);
        } else {
            System.out.println("No hay paquetes registrados.");
        }
    }

    private static void verSiguientePaquete() {
        System.out.println("--- SIGUIENTE PAQUETE PARA DESPACHO ---");
        Paquete siguiente = centro.obtenerSiguientePaquete();
        if (siguiente != null) {
            System.out.println(siguiente);
        } else {
            System.out.println("No hay paquetes pendientes por despachar.");
        }
    }

    private static void asignarPaquete() {
        System.out.print("Ingrese codigo del paquete (ej. P105): ");
        String codPaquete = scanner.nextLine().trim();
        System.out.print("Ingrese codigo del repartidor (ej. R01): ");
        String codRepartidor = scanner.nextLine().trim();

        try {
            centro.asignarPaquete(codPaquete, codRepartidor);
            System.out.println("El paquete " + codPaquete + " fue asignado a " + codRepartidor + " correctamente.");
        } catch (Exception e) {
            System.out.println("Error al realizar la asignacion: " + e.getMessage());
        }
    }

    private static void registrarEntrega() {
        System.out.print("Ingrese codigo del paquete entregado (ej. P105): ");
        String codPaquete = scanner.nextLine().trim();

        try {
            centro.registrarEntrega(codPaquete);
            System.out.println("Entrega confirmada para el paquete " + codPaquete + ".");
        } catch (Exception e) {
            System.out.println("Error al registrar la entrega: " + e.getMessage());
        }
    }

    private static void registrarNuevoPaquete() {
        System.out.println("--- REGISTRO DE NUEVO PAQUETE ---");

        System.out.print("Codigo del paquete (ej. P150): ");
        String codigo = scanner.nextLine().trim();

        System.out.print("Ciudad de destino: ");
        String destino = scanner.nextLine().trim();

        double peso;
        try {
            System.out.print("Peso en kg (ej. 4.5): ");
            peso = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: El peso debe ser un número valido.");
            return;
        }

        int prioridad;
        try {
            System.out.print("Prioridad (1 a 5): ");
            prioridad = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: La prioridad debe ser un numero entero.");
            return;
        }

        int tiempoEstimado;
        try {
            System.out.print("Tiempo estimado de entrega en minutos (ej. 45): ");
            tiempoEstimado = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: El tiempo estimado debe ser un numero entero.");
            return;
        }

        try {
            Paquete nuevo = new Paquete(codigo, destino, peso, prioridad, tiempoEstimado);
            centro.registrarPaquete(nuevo);
            System.out.println("Paquete " + codigo + " registrado exitosamente en el sistema.");
        } catch (Exception e) {
            System.out.println("Error al registrar paquete: " + e.getMessage());
        }
    }

    // --- REGISTRO INICIAL ---

    private static void inicializarDatos() {
        // Registrar repartidores
        centro.registrarRepartidor(new Repartidor("R01", "Carlos", "Armenia", true));
        centro.registrarRepartidor(new Repartidor("R02", "Laura", "Pereira", true));
        centro.registrarRepartidor(new Repartidor("R03", "Andres", "Calarca", true));
        centro.registrarRepartidor(new Repartidor("R04", "Sofia", "Montenegro", true));
        centro.registrarRepartidor(new Repartidor("R05", "Daniel", "Circasia", true));
        centro.registrarRepartidor(new Repartidor("R06", "Valentina", "Armenia", true));

        // Registrar 30 paquetes
        centro.registrarPaquete(new Paquete("P105", "Armenia", 4.5, 5, 30));
        centro.registrarPaquete(new Paquete("P102", "Pereira", 2.3, 3, 45));
        centro.registrarPaquete(new Paquete("P118", "Calarca", 8.0, 4, 25));
        centro.registrarPaquete(new Paquete("P111", "Montenegro", 1.5, 2, 60));
        centro.registrarPaquete(new Paquete("P130", "Circasia", 6.2, 5, 20));
        centro.registrarPaquete(new Paquete("P107", "Armenia", 3.1, 4, 35));
        centro.registrarPaquete(new Paquete("P125", "Pereira", 9.4, 2, 50));
        centro.registrarPaquete(new Paquete("P109", "Calarca", 2.7, 1, 70));
        centro.registrarPaquete(new Paquete("P140", "Montenegro", 5.6, 3, 40));
        centro.registrarPaquete(new Paquete("P115", "Circasia", 7.8, 4, 25));
        centro.registrarPaquete(new Paquete("P103", "Armenia", 1.2, 2, 55));
        centro.registrarPaquete(new Paquete("P122", "Pereira", 4.8, 5, 30));
        centro.registrarPaquete(new Paquete("P135", "Calarca", 6.5, 3, 45));
        centro.registrarPaquete(new Paquete("P114", "Montenegro", 2.0, 1, 80));
        centro.registrarPaquete(new Paquete("P128", "Circasia", 3.9, 4, 35));
        centro.registrarPaquete(new Paquete("P101", "Armenia", 10.5, 5, 15));
        centro.registrarPaquete(new Paquete("P119", "Pereira", 2.8, 2, 65));
        centro.registrarPaquete(new Paquete("P145", "Calarca", 4.1, 5, 20));
        centro.registrarPaquete(new Paquete("P106", "Montenegro", 8.7, 3, 40));
        centro.registrarPaquete(new Paquete("P133", "Circasia", 1.8, 2, 60));
        centro.registrarPaquete(new Paquete("P117", "Armenia", 5.3, 3, 45));
        centro.registrarPaquete(new Paquete("P124", "Pereira", 7.1, 4, 25));
        centro.registrarPaquete(new Paquete("P108", "Calarca", 3.6, 2, 50));
        centro.registrarPaquete(new Paquete("P138", "Montenegro", 9.2, 5, 18));
        centro.registrarPaquete(new Paquete("P112", "Circasia", 2.4, 1, 75));
        centro.registrarPaquete(new Paquete("P126", "Armenia", 6.8, 4, 28));
        centro.registrarPaquete(new Paquete("P104", "Pereira", 1.6, 1, 90));
        centro.registrarPaquete(new Paquete("P143", "Calarca", 5.9, 3, 38));
        centro.registrarPaquete(new Paquete("P116", "Montenegro", 4.3, 4, 32));
        centro.registrarPaquete(new Paquete("P132", "Circasia", 11.0, 5, 12));
    }
}