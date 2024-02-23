package com.backend.parcial.service;

import com.backend.parcial.entity.Odontologo;

import java.util.List;

public interface IDontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo);
    List<Odontologo> buscarTodosLosOdontologos();
}
