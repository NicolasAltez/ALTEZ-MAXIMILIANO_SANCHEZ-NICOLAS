package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;

import java.util.List;

public class OdontologoDaoMemoriaImpl implements IDao<Odontologo> {

    private final Logger LOG = Logger.getLogger(OdontologoDaoMemoriaImpl.class);

    private List<Odontologo> listaOdontologos;

    public OdontologoDaoMemoriaImpl(List<Odontologo> listaOdontologos){
        this.listaOdontologos = listaOdontologos;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odontologoGuardado = crearOdontologo(odontologo);
        listaOdontologos.add(odontologo);
        LOG.info("Odontologo guardado con exito" + odontologoGuardado);
        return odontologoGuardado;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        LOG.info("Odontologos encontrados: " + listaOdontologos);
        return listaOdontologos;
    }

    private Odontologo crearOdontologo(Odontologo odontologo){
        int id = listaOdontologos.size() + 1;
        return new Odontologo(id, odontologo.getNumeroDeMatricula(), odontologo.getNombre(), odontologo.getApellido());
    }
}
