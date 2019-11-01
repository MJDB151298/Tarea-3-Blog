package Clases;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import services.*;
import spark.Session;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rutas {
    public void manejoRutas()
    {
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        //configuration.setClassForTemplateLoading(this.getClass(), "/");
        try {
            configuration.setDirectoryForTemplateLoading(new File(
                    "src/main/java/resources/spark/template/freemarker"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Spark.get("/menu", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("listaArticulos", Controladora.getInstance().reverseArticulos());
            attributes.put("loggedUser", request.session(true).attribute("usuario"));
            return getPlantilla(configuration, attributes, "index.ftl");
        });

        Spark.get("/autores", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("listaUsuarios", Controladora.getInstance().getMisUsuarios());
            return getPlantilla(configuration, attributes, "autor.ftl");
        });

        Spark.post("/deleteComment/:idPost/:idComment", (request, response) -> {
            InterArticleServices articleServices = new InterArticleServices();
            long idPost = Long.parseLong(request.params("idPost"));
            Articulo art = Controladora.getInstance().buscarArticulo(idPost);
            long idComment = Long.parseLong(request.params("idComment"));
            Comentario comentario = Controladora.getInstance().buscarComentario(idComment);
            articleServices.borrarComentarioDeArticulo(art, comentario);
            art.getListaComentarios().remove(comentario);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulo", art);
            attributes.put("listaComentarios", art.getListaComentarios());
            attributes.put("loggedUser", request.session(true).attribute("usuario"));
            return getPlantilla(configuration, attributes, "post.ftl");
        });

        Spark.get("/menu/:id", (request, response) -> {
            long id = Long.parseLong(request.params("id"));
            Articulo articulo = Controladora.getInstance().buscarArticulo(id);
            System.out.println(articulo.getId());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulo", articulo);
            attributes.put("listaComentarios", articulo.getListaComentarios());
            attributes.put("loggedUser", request.session(true).attribute("usuario"));
           return getPlantilla(configuration, attributes, "post.ftl");
        });

        Spark.post("/makeAutor/:username", (request, response) -> {
            String username = request.params("username");
            Usuario usu = Controladora.getInstance().buscarAutor(username);
            boolean valor;
            if(request.queryParams("checkAutor") != null)
            {
                valor = true;
            }
            else{
                valor=false;
            }
            usu.setAutor(valor);
            new UserServices().actualizarUsuario(usu);
            response.redirect("/autores");
            return "";
        });

        Spark.get("/saveComment/:id", (request, response) -> {
            String id = request.params("id");
            String comentario = request.queryParams("commentContent");
            Articulo articulo = Controladora.getInstance().buscarArticulo(Long.parseLong(id));
            Session session=request.session(true);
            Usuario usuario = session.attribute("usuario");
            Comentario newComentario = new Comentario(comentario, usuario, articulo);

            new ComentServices().crearComentario(newComentario);
            new InterArticleServices().nuevoComentarioAlArticulo(articulo, newComentario);
            response.redirect("/menu/"+id);
            return "";
        });

        Spark.post("/createPost", (request, response) -> {
            String title = request.queryParams("postTitle");
            String body = request.queryParams("postContent");
            ArrayList<Etiqueta> tags = Controladora.getInstance().divideTags(request.queryParams("tags"));
            Articulo art = new Articulo(title, body, request.session(true).attribute("usuario"));
            art.setListaEtiquetas(tags);
            new ArticleServices().crearArticulo(art);
            for (Etiqueta etq: tags
                 ) {
                if (new TagServices().getEtiqueta(etq.getId()) == null)
                {
                    Controladora.getInstance().getMisEtiquetas().add(etq);
                    new TagServices().crearEtiqueta(etq);
                }
                new InterArticleServices().nuevaEtiquetaAlArticulo(art, etq);
            }
            response.redirect("/menu");
            return "";
        });

        /**
         * Metodos Get y Post para logearse.
         */
        Spark.get("/login", (request, response) -> {
            String warningText = "";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("warningText", warningText);
            return getPlantilla(configuration, attributes, "login.ftl");
        });

        Spark.post("/login", (request, response) -> {
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            if(!Controladora.getInstance().validatePassword(username, password)){
                String warningText = "Usuario o contrasena incorrectos.";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return getPlantilla(configuration, attributes, "login.ftl");
            }

            Session session=request.session(true);
            Usuario usuario = Controladora.getInstance().buscarAutor(username);
            session.attribute("usuario", usuario);
            System.out.println("Hola");
            String remember = request.queryParams("remember");

            /**if(remember != null){

                System.out.println("waaaaaasaaaa");
                response.cookie("/menu", username, "usuario", 36000, false);
            }**/
            response.redirect("/menu");
            return "";
        });

        /**
         *Metodos Get y Post para registrarse.
         */
        Spark.get("/register", (request, response) -> {
            String warningText = "";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("warningText", warningText);
            return getPlantilla(configuration, attributes, "register.ftl");
        });

        Spark.post("/register", (request, response) -> {
            String nombre = request.queryParams("first_name") + " " + request.queryParams("last_name");
            String username = request.queryParams("username");
            if(!Controladora.getInstance().validateUser(username)){
                String warningText = "Usuario ya existe";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return getPlantilla(configuration, attributes, "register.ftl");
            }
            String password = request.queryParams("password");
            String confirmPassword = request.queryParams("confirm_password");
            if(!password.equals(confirmPassword)){
                String warningText = "Las contraseñas no coinciden";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return getPlantilla(configuration, attributes, "register.ftl");
            }
            Usuario usuario = new Usuario(username, nombre, password, false);
            new UserServices().crearUsuario(usuario);
            Session session=request.session(true);
            session.attribute("usuario", usuario);
            response.redirect("/menu");
            return "";
        });

        /**
         * Metodo get para terminar la sesion.
         */
        Spark.get("/disconnect", (request, response) -> {
            Session session=request.session(true);
            session.invalidate();
            response.redirect("/login");
            return "";
        });

    }

    public StringWriter getPlantilla(Configuration configuration, Map<String, Object> model, String templatePath) throws IOException, TemplateException {
        Template plantillaPrincipal = configuration.getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        plantillaPrincipal.process(model, writer);
        return writer;
    }
}
