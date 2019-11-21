package Clases;

import services.*;

import java.sql.SQLException;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class Main {

    public static void main(String[] args) throws SQLException {

        port(8081);

        staticFileLocation("/publico");
        new Rutas().manejoRutas();

        //Aplicando Filtros
        new Filtros().aplicarFiltros();

        //Iniciando el servicio
        BootStrapServices.startDb();

        //Prueba de Conexión.
        DataBaseServices.getInstancia().testConexion();

        BootStrapServices.crearTablas();

        Controladora.getInstance().setData();

        Usuario usuario1 = new Usuario("Zycotec01", "Marcos", "hola123", true);

        UserServices us = new UserServices();
        ArticleServices as = new ArticleServices();

        InterArticleServices interArticleServices = new InterArticleServices();
        interArticleServices.agregarComentariosYEtiquetasAlArticulo();

        //us.crearUsuario(usuario1);

       /** Articulo articulo1 = new Articulo("La vida de Marcos", "Este es un articulo escrito por Marcos", usuario1);
        Articulo articulo2 = new Articulo("La vida de Luis", "Este es un articulo escrito por Luis", usuario1);
        Articulo articulo3 = new Articulo("La vida de Saul", "I’m honestly quite impressed with this episode so far, especially considering it’s a Whisperers only and we won’t (I’m assuming) see any of the other main cast, except maybe in the final moments.\n" +
                "\n" +
                "I feel like the cinematography and sound got much better too, which is really making the show more visually appealing.", usuario1);
        as.crearArticulo(articulo1);
        as.crearArticulo(articulo2);
        as.crearArticulo(articulo3);**/
    }
}
