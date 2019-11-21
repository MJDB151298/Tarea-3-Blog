package Clases;

import org.apache.commons.codec.digest.DigestUtils;
import services.*;

import java.sql.SQLException;
import java.util.UUID;

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

        if(Controladora.getInstance().getMisUsuarios().size() == 0){
            Usuario usuario1 = new Usuario("Admin", "Admin", DigestUtils.md5Hex("admin"), true);
            usuario1.setId(UUID.randomUUID().toString());
            UserServices us = new UserServices();
            us.crearUsuario(usuario1);
        }



        ArticleServices as = new ArticleServices();

        InterArticleServices interArticleServices = new InterArticleServices();
        interArticleServices.agregarComentariosYEtiquetasAlArticulo();

        for(Articulo articulo : Controladora.getInstance().getMisArticulos()){
            System.out.println("El articulo " + articulo.getId() + "tiene: " + articulo.getListaEtiquetas().size() + " Etiquetas");
        }



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
