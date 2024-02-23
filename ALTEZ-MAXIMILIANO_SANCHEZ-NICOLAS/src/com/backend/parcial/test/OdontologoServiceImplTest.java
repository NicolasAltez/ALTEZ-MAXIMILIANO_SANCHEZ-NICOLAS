package com.backend.parcial.test;

import com.backend.parcial.dao.impl.OdontologoDaoMemoriaImpl;
import com.backend.parcial.dao.impl.OndotologoDaoH2Impl;
import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.service.impl.OdontologoServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OdontologoServiceImplTest {

    private OdontologoServiceImpl odontologoService;

    @Test
    public void cuandoSeGuardaSatisfactoriamenteUnOdontologoEnH2(){
        odontologoService = new OdontologoServiceImpl(new OndotologoDaoH2Impl());

        Odontologo odontologo = new Odontologo("AE2132132", "Nicolas","Sanchez");

        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        assertNotNull(odontologoGuardado);
        assertEquals(odontologo.getNumeroDeMatricula(), odontologoGuardado.getNumeroDeMatricula());
        assertEquals(odontologo.getNombre(), odontologoGuardado.getNombre());
        assertEquals(odontologo.getApellido(), odontologoGuardado.getApellido());
    }

    @Test
    public void cuandoNoSeGuardaSatisfactoriamenteUnOdontologoEnH2(){
        odontologoService = new OdontologoServiceImpl(new OndotologoDaoH2Impl());

        Odontologo odontologo = new Odontologo(null, "Nicolas","Sanchez");
        Odontologo odontologoNoGuardado = odontologoService.guardarOdontologo(odontologo);

        assertNull(odontologoNoGuardado);
    }

    @Test
    public void cuandoSeBuscanTodosLosOdontologosEnH2(){
        odontologoService = new OdontologoServiceImpl(new OndotologoDaoH2Impl());

        List<Odontologo> odontologos = odontologoService.buscarTodosLosOdontologos();

        assertEquals(2, odontologos.size());
        assertEquals(odontologos.getFirst().getNombre(), "Maximiliano");
        assertEquals(odontologos.getLast().getNombre(), "Nicolas");
    }

    @Test
    public void cuandoSeGuardaSatisfactoriamenteUnOdontologoEnMemoria(){
        odontologoService = new OdontologoServiceImpl(new OdontologoDaoMemoriaImpl(new ArrayList<>()));

        Odontologo odontologo = new Odontologo("AE2132132", "Maximiliano","Altez");

        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        assertNotNull(odontologoGuardado);

        assertEquals(odontologo.getNumeroDeMatricula(), odontologoGuardado.getNumeroDeMatricula());
        assertEquals(odontologo.getNombre(), odontologoGuardado.getNombre());
        assertEquals(odontologo.getApellido(), odontologoGuardado.getApellido());
    }

    @Test
    public void cuandoSeBuscanTodosLosOdontologosEnMemoria(){

        Odontologo odontologo = new Odontologo("AE2132132", "Maximiliano","Altez");
        Odontologo odontologo2 = new Odontologo("AE2132132", "Nicolas","Sanchez");

        List<Odontologo> odontologoList = List.of(odontologo, odontologo2);

        odontologoService = new OdontologoServiceImpl(new OdontologoDaoMemoriaImpl(odontologoList));

        List<Odontologo> odontologos = odontologoService.buscarTodosLosOdontologos();

        assertEquals(2, odontologos.size());
        assertEquals(odontologos.getFirst(),odontologo);
        assertEquals(odontologos.getLast(),odontologo2);
    }

    @Test
    public void cuandoSeBuscanTodosLosOdontologsEnMemoriaYNoHayNinguno(){
        odontologoService = new OdontologoServiceImpl(new OdontologoDaoMemoriaImpl(new ArrayList<>()));
        List<Odontologo> odontologos = odontologoService.buscarTodosLosOdontologos();
        assertTrue(odontologos.isEmpty());
    }

}