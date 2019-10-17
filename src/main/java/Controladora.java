import Clases.Articulo;
import Clases.Usuario;

import javax.naming.ldap.Control;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Controladora implements Serializable {
    Usuario usuario1 = new Usuario("Zycotec01", "Marcos", "hola123", true);
    Articulo articulo1 = new Articulo(1, "La vida de Marcos", "Este es un articulo escrito por Marcos", usuario1);
    Articulo articulo2 = new Articulo(2, "La vida de Luis", "Este es un articulo escrito por Luis", usuario1);
    Articulo articulo3 = new Articulo(3, "La vida de Saul", "I’m honestly quite impressed with this episode so far, especially considering it’s a Whisperers only and we won’t (I’m assuming) see any of the other main cast, except maybe in the final moments.\n" +
            "\n" +
            "I feel like the cinematography and sound got much better too, which is really making the show more visually appealing.", usuario1);

    private static final long serialVersionUID = 1L;
    private static Controladora controladora;

    private ArrayList<Articulo> misArticulos;

    public Controladora(){
        this.misArticulos = new ArrayList<>();
        getMisArticulos().add(articulo1);
        getMisArticulos().add(articulo2);
        getMisArticulos().add(articulo3);
    }

    public static Controladora getInstance() {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }

    public ArrayList<Articulo> getMisArticulos(){
        return this.misArticulos;
    }

    public ArrayList<Articulo> reverseArticulos(){
        ArrayList<Articulo> reverse = new ArrayList<>();
        for(int i = misArticulos.size()-1; i >= 0; i--) {
            reverse.add(misArticulos.get(i));
        }
        return reverse;
    }

    public Articulo buscarArticulo(int id){
        for(Articulo articulo : Controladora.getInstance().getMisArticulos()){
            if(articulo.getId() == id){
                return articulo;
            }
        }
        return null;
    }

}
