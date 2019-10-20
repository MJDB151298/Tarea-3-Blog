package Clases;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import services.ComentServices;
import services.InterArticleServices;
import services.UserServices;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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
            return getPlantilla(configuration, attributes, "index.ftl");
        });

        Spark.get("/menu/:id", (request, response) -> {
            long id = Long.parseLong(request.params("id"));
            Articulo articulo = Controladora.getInstance().buscarArticulo(id);
            System.out.println(articulo.getId());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulo", articulo);
            attributes.put("listaComentarios", articulo.getListaComentarios());
           return getPlantilla(configuration, attributes, "post.ftl");
        });

        Spark.get("/saveComment/:id", (request, response) -> {
            String id = request.params("id");
            String comentario = request.queryParams("commentContent");
            Articulo articulo = Controladora.getInstance().buscarArticulo(Long.parseLong(id));
            Usuario usuario = new UserServices().getUsuario("Zycotec01");
            Comentario newComentario = new Comentario(comentario, usuario, articulo);

            new ComentServices().crearComentario(newComentario);
            new InterArticleServices().nuevoComentarioAlArticulo(articulo, newComentario);
            response.redirect("/menu/"+id);
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
