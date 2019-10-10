import Clases.Articulo;

import javax.naming.ldap.Control;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Controladora implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Controladora controladora;

    private ArrayList<Articulo> misArticulos;

    public Controladora(){
        this.misArticulos = new ArrayList<>();
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
        for(int i = misArticulos.size(); i >= 0; i--) {
            reverse.add(misArticulos.get(i));
        }
        return reverse;
    }

}
