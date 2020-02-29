package Clases;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.commons.codec.digest.DigestUtils;
import services.*;
import spark.Session;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
            Controladora.getInstance().getMisComentarios().remove(comentario);
            //response.redirect("menu/" + idPost);
            //return "";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulo", art);
            attributes.put("listaComentarios", art.getListaComentarios());
            attributes.put("loggedUser", request.session(true).attribute("usuario"));
            return getPlantilla(configuration, attributes, "post.ftl");
        });

        Spark.get("/deletePost/:idPost", (request, response) -> {
           long id = Long.parseLong(request.params("idPost"));
           Articulo art = Controladora.getInstance().buscarArticulo(id);
           new InterArticleServices().borrarTodaEtiquetaDeArticulo(art);
           new InterArticleServices().borrarTodoComentarioArticulo(art);
           new ArticleServices().borrarArticulo(id);
           for(Comentario comentario : art.getListaComentarios()){
               Controladora.getInstance().getMisComentarios().remove(comentario);
           }
           for(Etiqueta e : art.getListaEtiquetas()){
               Controladora.getInstance().getMisEtiquetas().remove(e);
           }
           Controladora.getInstance().getMisArticulos().remove(art);
            response.redirect("/menu");
            return "";
        });

        Spark.get("/menu/:id", (request, response) -> {
            long id = Long.parseLong(request.params("id"));
            Articulo articulo = Controladora.getInstance().buscarArticulo(id);
            //System.out.println(articulo.getId());
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
            String categoria = request.queryParams("postCategory");
            body =  body.replace("\n", "").replace("\r", "");
            ArrayList<Etiqueta> tags = Controladora.getInstance().divideTags(request.queryParams("tags"));
            System.out.println(request.queryParams("postCategory"));
            Articulo art = new Articulo(title, body, request.session(true).attribute("usuario"), categoria);
            if (Controladora.getInstance().validateArticle(art))
            {
                art.setListaEtiquetas(tags);
                new ArticleServices().crearArticulo(art);
                for (Etiqueta etq: tags
                ) {
                    if (Controladora.getInstance().buscarEtqPorContenido(etq.getEtiqueta()) == null)
                    {
                        etq.setId(Controladora.getInstance().getMisEtiquetas().size()+1);
                        Controladora.getInstance().getMisEtiquetas().add(etq);
                        new TagServices().crearEtiqueta(etq);
                    }
                    new InterArticleServices().nuevaEtiquetaAlArticulo(art, etq);
                }
            }
            response.redirect("/menu");
            return "";
        });

        Spark.post("/updatePost/:idArticle", (request, response) -> {
            String title = request.queryParams("postTitle");
            String body = request.queryParams("postContent");
            ArrayList<Etiqueta> tags = Controladora.getInstance().divideTags(request.queryParams("tags"));
            long id = Long.parseLong(request.params("idArticle"));
            Articulo art = Controladora.getInstance().buscarArticulo(id);
            art.setTitulo(title);
            art.setCuerpo(body);
            art.setFecha(Date.valueOf(LocalDate.now().toString()));
            new ArticleServices().actualizarArticulo(art);
            for (Etiqueta e: art.getListaEtiquetas()
                 ) {
                new InterArticleServices().borrarEtiquetaDeArticulo(art, e);
            }
            art.setListaEtiquetas(tags);
            for (Etiqueta etq: tags
                 ) {
                Etiqueta tag;
                if(!Controladora.getInstance().tagExistence(etq))
                {
                    tag = new Etiqueta(etq.getEtiqueta());
                    long idEtq = 0;
                    if(Controladora.getInstance().getMisEtiquetas().size() != 0){
                        idEtq = Controladora.getInstance().getMisEtiquetas().get(Controladora.getInstance().getMisEtiquetas().size()-1).getId()+1;
                        tag.setId(idEtq);
                    }
                    Controladora.getInstance().getMisEtiquetas().add(tag);
                    new TagServices().crearEtiqueta(tag);
                }
                else
                {
                    tag = Controladora.getInstance().buscarEtqPorContenido(etq.getEtiqueta());
                }

                new InterArticleServices().nuevaEtiquetaAlArticulo(art, tag);
            }
            response.redirect("/menu/" + id);
            return " ";
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
            String hashedPassword = DigestUtils.md5Hex(password);
            if(!Controladora.getInstance().validatePassword(username, hashedPassword)){
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

            if(remember != null){
                response.cookie("usuario_id", usuario.getId(), 604800000);
            }
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
            String hashedPassword = DigestUtils.md5Hex(password);
            Usuario usuario = new Usuario(username, nombre, hashedPassword, false);
            usuario.setId(UUID.randomUUID().toString());
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
            response.removeCookie("usuario_id");
            response.redirect("/menu");
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
