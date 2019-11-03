package Clases;
import java.util.ArrayList;

import static spark.Spark.*;

public class Filtros {
    public void aplicarFiltros(){
        before("/menu", (request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null){
                System.out.println("El usuario es null");
                response.redirect("/login");
            }
            else{
                response.header("Usuario", usuario.getUsername());
            }
        });

        before("/menu/*", (request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null){
                response.redirect("/login");
            }
            else{
                response.header("Usuario", usuario.getUsername());
            }
        });

        before("/login", (request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario!=null){
                response.redirect("/menu");
            }
        });

        before("/register", (request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario!=null){
                response.redirect("/menu");
            }
        });

        before("/createPost", (request, response) -> {
            String title = request.queryParams("postTitle");
            String body = request.queryParams("postContent");
            ArrayList<Etiqueta> tags = Controladora.getInstance().divideTags(request.queryParams("tags"));
            Articulo art = new Articulo(title, body, request.session(true).attribute("usuario"));
            if (Controladora.getInstance().validateArticle(art))
            {
                response.redirect("/createPost");
            }
        });
    }

}
