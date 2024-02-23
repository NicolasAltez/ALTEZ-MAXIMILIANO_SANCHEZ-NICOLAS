package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.dbconnection.H2Connection;
import com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OndotologoDaoH2Impl implements IDao<Odontologo> {

    private final Logger LOG = Logger.getLogger(OndotologoDaoH2Impl.class);

    @Override
    public Odontologo guardar(Odontologo odontologo) {

        Connection connection = null;
        Odontologo odontologoRegistrado = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            connection.commit();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS (nombre, apellido, matricula) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNombre());
            preparedStatement.setString(2, odontologo.getApellido());
            preparedStatement.setString(3, odontologo.getNumeroDeMatricula());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                odontologoRegistrado = crearOdontologo(resultSet);
            }

            if (odontologoRegistrado != null) {
                LOG.info("Odontologo registrado con exito: " + odontologoRegistrado);
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOG.info("Se hizo rollback de la transaccion ya que hubo un error al registrar el odontologo");
                } catch (Exception ex) {
                    LOG.error(ex.getMessage());
                }
            }
        } finally {
            if (existeLaConexion(connection)) {
                try {
                    connection.close();
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                odontologos.add(crearOdontologo(resultSet));
            }

            if (!odontologos.isEmpty()) {
                LOG.info("Se encontraron los siguientes odontologos: " + odontologos);
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            if (existeLaConexion(connection)) {
                try {
                    connection.close();
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return odontologos;
    }

    private Odontologo crearOdontologo(ResultSet resultSet) throws Exception {
        return new Odontologo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
    }

    private boolean existeLaConexion(Connection connection) {
        return connection != null;
    }

}
