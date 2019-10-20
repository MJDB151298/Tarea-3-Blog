package Clases;

import Clases.Rutas;
import services.BootStrapServices;
import services.DataBaseServices;
import services.UserServices;

import java.sql.SQLException;

import static spark.Spark.staticFileLocation;

public class Main {

    public static void main(String[] args) throws SQLException {
        staticFileLocation("/publico");
        new Rutas().manejoRutas();

        //Iniciando el servicio
        BootStrapServices.startDb();

        //Prueba de Conexión.
        DataBaseServices.getInstancia().testConexion();

        BootStrapServices.crearTablas();

        Usuario usuario1 = new Usuario("Zycotec01", "Marcos", "hola123", true);

        UserServices us = new UserServices();

        us.crearUsuario(usuario1);
    }
}
