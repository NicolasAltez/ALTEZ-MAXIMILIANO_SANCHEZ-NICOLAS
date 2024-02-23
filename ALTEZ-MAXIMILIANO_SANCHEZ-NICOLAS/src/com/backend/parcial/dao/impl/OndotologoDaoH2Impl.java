package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.dbconnection.H2Connection;
import com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
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

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS (numero_de_matricula, nombre, apellido) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNumeroDeMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());

            preparedStatement.execute();



            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                odontologoRegistrado = new Odontologo(resultSet.getInt(1), odontologo.getNumeroDeMatricula(), odontologo.getNombre(), odontologo.getApellido());
            }

            connection.commit();

            if (odontologoRegistrado != null) {
                LOG.info("Odontologo registrado con exito: " + odontologoRegistrado);
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            if (existeLaConexion(connection)) {
                try {
                    connection.rollback();
                    LOG.info("Se hizo rollback de la transaccion ya que hubo un error al registrar el odontologo");
                } catch (SQLException ex) {
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
    private boolean existeLaConexion(Connection connection) {
        return connection != null;
    }

    private Odontologo crearOdontologo(ResultSet resultSet) throws SQLException {
        return new Odontologo(resultSet.getInt("id"), resultSet.getString("numero_de_matricula"), resultSet.getString("nombre"), resultSet.getString("apellido"));
    }

}
