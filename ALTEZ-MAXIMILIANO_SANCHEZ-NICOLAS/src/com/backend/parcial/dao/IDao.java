package com.backend.parcial.dao;

import java.util.List;

public interface IDao<T> {
    T guardar(T t);
    List<T> buscarTodos();

}
