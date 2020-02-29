package Clases;

import services.ArticleServices;
import services.ComentServices;
import services.TagServices;
import services.UserServices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Controladora implements Serializable {
    Usuario usuario1 = new Usuario("Zycotec01", "Marcos", "hola123", true);
    /**Articulo articulo1 = new Articulo(1, "La vida de Marcos", "Este es un articulo escrito por Marcos", usuario1);
    Articulo articulo2 = new Articulo(2, "La vida de Luis", "Este es un articulo escrito por Luis", usuario1);
    Articulo articulo3 = new Articulo(3, "La vida de Saul", "I’m honestly quite impressed with this episode so far, especially considering it’s a Whisperers only and we won’t (I’m assuming) see any of the other main cast, except maybe in the final moments.\n" +
            "\n" +
            "I feel like the cinematography and sound got much better too, which is really making the show more visually appealing.", usuario1);**/

    private static final long serialVersionUID = 1L;
    private static Controladora controladora;
    private ArrayList<Usuario> misUsuarios;
    private ArrayList<Articulo> misArticulos;
    private ArrayList<Comentario> misComentarios;
    private ArrayList<Etiqueta> misEtiquetas;

    public Controladora(){
        this.misArticulos = new ArrayList<>();
        this.misUsuarios = new ArrayList<>();
        this.misComentarios = new ArrayList<>();
        this.misEtiquetas = new ArrayList<>();
    }

    public static Controladora getInstance() {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }

    public void setData(){
        this.misUsuarios = new UserServices().listaUsuarios();
        this.misArticulos = new ArticleServices().listaArticulos();
        this.misComentarios = new ComentServices().listaComentarios();
        this.misEtiquetas = new TagServices().listaEtiquetas();
    }

    public List<Articulo> reverseArticulos(){
        List<Articulo> reverse = new ArrayList<>();
        for(int i = misArticulos.size()-1; i >= 0; i--) {
            reverse.add(misArticulos.get(i));
        }
        return reverse;
    }

    public Articulo buscarArticulo(long id){
        for(Articulo articulo : Controladora.getInstance().getMisArticulos()){
            if(articulo.getId() == id){
                return articulo;
            }
        }
        return null;
    }

    public Usuario buscarAutor(String userName)
    {
        System.out.println(userName);
        Usuario usuario = null;
        for (Usuario usu: Controladora.getInstance().getMisUsuarios()
             ) {
            if (usu.getUsername().equalsIgnoreCase(userName))
            {
                usuario = usu;
            }
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorID(String id){
        for(Usuario usuario : Controladora.getInstance().getMisUsuarios()){
            if(usuario.getId().equalsIgnoreCase(id)){
                return usuario;
            }
        }
        return null;
    }

    public Comentario buscarComentario(long id)
    {
        Comentario coment = null;

        for (Comentario com: Controladora.getInstance().getMisComentarios()
             ) {
            if (com.getId() == id)
            {
                coment = com;
            }
        }

        return coment;
    }

    public Etiqueta buscarEtiqueta(long id)
    {
        Etiqueta etiq = null;

        for (Etiqueta et: Controladora.getInstance().getMisEtiquetas()
        ) {
            if (et.getId() == id)
            {
                etiq = et;
            }
        }

        return etiq;
    }

    public boolean tagExistence(Etiqueta etq)
    {
        boolean flag = false;

        for (Etiqueta etiqueta: Controladora.getInstance().getMisEtiquetas()
             ) {
            if(etiqueta.getEtiqueta().equalsIgnoreCase(etq.getEtiqueta()))
            {
                flag = true;
            }
        }

        return flag;
    }

    public boolean validateArticle(Articulo art)
    {
        boolean flag = true;

        for (Articulo articulo: Controladora.getInstance().getMisArticulos()
        ) {
            if(art.getTitulo().equalsIgnoreCase(articulo.getTitulo()) || art.getCuerpo().equalsIgnoreCase(articulo.getCuerpo()))
            {
                flag = false;
            }
        }

        return flag;
    }

    public boolean validateUser(String username){
        for(Usuario usuario : Controladora.getInstance().getMisUsuarios()){
            if(usuario.getUsername().equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }

    public boolean validatePassword(String username, String password){
        for(Usuario usuario : Controladora.getInstance().getMisUsuarios()){
            if(usuario.getUsername().equalsIgnoreCase(username) && usuario.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Etiqueta> divideTags(String tagsinput)
    {
        ArrayList<Etiqueta> tags = new ArrayList<>();
        String[] ntags = tagsinput.split(" ");
        for (String tag: ntags) {
            Etiqueta etq = new Etiqueta(tag);
            long id = 0;
            if(getMisEtiquetas().size() != 0){
                id = Controladora.getInstance().getMisEtiquetas().get(Controladora.getInstance().getMisEtiquetas().size()-1).getId()+1;
            }
            System.out.println("El id del etiqueta es: " + id);
            etq.setId(id);
            tags.add(etq);
        }
        return tags;
    }

    public Etiqueta buscarEtqPorContenido(String cont)
    {
        Etiqueta etq = null;

        for (Etiqueta e: Controladora.getInstance().getMisEtiquetas()
             ) {
            if(e.getEtiqueta().equalsIgnoreCase(cont))
            {
                etq = e;
                break;
            }
        }

        return etq;
    }

    public boolean buscarEtqDeArticulo(Articulo art, Etiqueta etiqueta)
    {
        boolean flag = false;

        for (Etiqueta etq: art.getListaEtiquetas()
             ) {
            if (etiqueta.getEtiqueta().equalsIgnoreCase(etq.getEtiqueta()))
            {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public ArrayList<Usuario> getMisUsuarios() {
        return misUsuarios;
    }

    public ArrayList<Articulo> getMisArticulos(){
        return this.misArticulos;
    }

    public ArrayList<Comentario> getMisComentarios() {
        return misComentarios;
    }

    public ArrayList<Etiqueta> getMisEtiquetas() {
        return misEtiquetas;
    }
}
