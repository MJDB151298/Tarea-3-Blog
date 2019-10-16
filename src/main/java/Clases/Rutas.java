package Clases;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Session;
import static spark.Spark.*;

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
            Template plantillaPrincipal = configuration.getTemplate("index.html");
            StringWriter writer = new StringWriter();
            plantillaPrincipal.process(null, writer);
            return writer;
        });
    }
}
