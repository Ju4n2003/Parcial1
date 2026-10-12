package com.quindioexpress.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ListaEnlazada<T> implements Coleccion<T>, Iterable<T> {

    private static class Nodo<T> {

        private T elemento;
        private Nodo<T> siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
        }
    }

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamaño;

    public ListaEnlazada() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);

        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo;
        }

        tamaño++;
    }

    @Override
    public boolean eliminar(T elemento) {
        if (estaVacia()) {
            return false;
        }

        // Caso 1: el elemento está en la cabeza.
        if (Objects.equals(cabeza.elemento, elemento)) {
            cabeza = cabeza.siguiente;
            tamaño--;

            if (cabeza == null) {
                cola = null;
            }

            return true;
        }

        // Caso 2: el elemento está en otra posición.
        Nodo<T> actual = cabeza;

        while (actual.siguiente != null) {

            if (Objects.equals(actual.siguiente.elemento, elemento)) {

                actual.siguiente = actual.siguiente.siguiente;

                if (actual.siguiente == null) {
                    cola = actual;
                }

                tamaño--;
                return true;
            }

            actual = actual.siguiente;
        }

        return false;
    }

    @Override
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException(
                    "Indice fuera de los limites de la lista: " + indice
            );
        }

        Nodo<T> actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }

        return actual.elemento;
    }

    @Override
    public int tamaño() {
        return tamaño;
    }

    @Override
    public boolean estaVacia() {
        return tamaño == 0;
    }

    public T obtenerPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException(
                    "La lista esta vacia."
            );
        }

        return cabeza.elemento;
    }

    public T obtenerUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException(
                    "La lista esta vacia."
            );
        }

        return cola.elemento;
    }

    public void limpiar() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {

            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {

                if (!hasNext()) {
                    throw new NoSuchElementException(
                            "No hay mas elementos en la lista."
                    );
                }

                T elemento = actual.elemento;
                actual = actual.siguiente;

                return elemento;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder("[");
        Nodo<T> actual = cabeza;

        while (actual != null) {
            resultado.append(actual.elemento);

            if (actual.siguiente != null) {
                resultado.append(", ");
            }

            actual = actual.siguiente;
        }

        resultado.append("]");

        return resultado.toString();
    }
}
