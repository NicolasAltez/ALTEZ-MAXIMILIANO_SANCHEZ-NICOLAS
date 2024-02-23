package com.backend.parcial.service.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.service.IDontologoService;

import java.util.List;

public class OdontologoServiceImpl implements IDontologoService {

    private IDao<Odontologo> odontologoIDao;

    public OdontologoServiceImpl(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }
    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoIDao.guardar(odontologo);
    }

    @Override
    public List<Odontologo> buscarTodosLosOdontologos() {
        return odontologoIDao.buscarTodos();
    }
}
