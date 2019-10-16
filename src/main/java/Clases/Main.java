package Clases;

import static spark.Spark.staticFileLocation;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/publico");
        new Rutas().manejoRutas();
    }
}
