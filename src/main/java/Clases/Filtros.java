package Clases;
import static spark.Spark.before;

public class Filtros {
    public void aplicarFiltros(){
//        before("/menu", (request, response) -> {
//            Usuario usuario=request.session(true).attribute("usuario");
//            if(usuario==null){
//                System.out.println("El usuario es null");
//                response.redirect("/login");
//            }
//            else{
//                response.header("Usuario", usuario.getUsername());
//            }
//        });
//
//        before("/menu/*", (request, response) -> {
//            Usuario usuario=request.session(true).attribute("usuario");
//            if(usuario==null){
//                response.redirect("/login");
//            }
//            else{
//                response.header("Usuario", usuario.getUsername());
//            }
//        });

        before((request, response) -> {
            Usuario usuario = request.session().attribute("usuario");
            String id = request.cookie("usuario_id");
            if(id != null && usuario == null){
                Usuario userLog = Controladora.getInstance().buscarUsuarioPorID(id);
                request.session(true).attribute("usuario", userLog);
            }
        });

        before("/login", (request, response) -> {
            Usuario usuario=request.session().attribute("usuario");
            if(usuario!=null){
                response.redirect("/menu");
            }
        });

        before("/register", (request, response) -> {
            Usuario usuario=request.session().attribute("usuario");
            if(usuario!=null){
                response.redirect("/menu");
            }
        });

    }

}
