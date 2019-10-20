package Clases;

import Clases.Articulo;
import Clases.Controladora;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


import static spark.route.HttpMethod.get;

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
            int id = Integer.parseInt(request.params("id"));
            Articulo articulo = Controladora.getInstance().buscarArticulo(id);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("articulo", articulo);
           return getPlantilla(configuration, attributes, "post.ftl");
        });

    }

    public StringWriter getPlantilla(Configuration configuration, Map<String, Object> model, String templatePath) throws IOException, TemplateException {
        Template plantillaPrincipal = configuration.getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        plantillaPrincipal.process(model, writer);
        return writer;
    }
}
