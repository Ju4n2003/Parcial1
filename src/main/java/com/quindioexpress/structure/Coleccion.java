package com.quindioexpress.structure;

public interface Coleccion<T> {

    void agregar(T elemento);

    boolean eliminar(T elemento);

    T obtener(int indice);

    int tamaño();

    boolean estaVacia();
}