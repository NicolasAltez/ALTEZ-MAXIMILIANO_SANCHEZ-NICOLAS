package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;

import java.util.List;

public class OdontologoDaoMemoriaImpl implements IDao<Odontologo> {

    private final Logger LOG = Logger.getLogger(OdontologoDaoMemoriaImpl.class);

    private List<Odontologo> odontologoRepository;

    public OdontologoDaoMemoriaImpl(List<Odontologo> odontologoRepository){
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odontologoGuardado = crearOdontologo(odontologo);
        odontologoRepository.add(odontologo);
        LOG.info("Odontologo guardado con exito" + odontologoGuardado);
        return odontologoGuardado;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        LOG.info("Odontologos encontrados: " + odontologoRepository);
        return odontologoRepository;
    }

    private Odontologo crearOdontologo(Odontologo odontologo){
        int id = odontologoRepository.size() + 1;
        return new Odontologo(id, odontologo.getNumeroDeMatricula(), odontologo.getNombre(), odontologo.getApellido());
    }
}
