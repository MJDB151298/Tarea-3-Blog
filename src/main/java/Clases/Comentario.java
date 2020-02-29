package Clases;

public class Comentario {
    private long id;
    private String comentario;
    private Usuario autor;
    private Articulo articulo;

    public Comentario(){

    }

    public Comentario(String comentario, Usuario autor, Articulo articulo){
        if(Controladora.getInstance().getMisComentarios().size() == 0){
            this.id = 1;
        }
        else{
            this.id = Controladora.getInstance().getMisComentarios().
                    get(Controladora.getInstance().getMisComentarios().size()-1).getId()+1;
        }
        this.id = Controladora.getInstance().getMisComentarios().size()+1;
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
    }

    public long getId() {
        return id;
    }
    public String getComentario() {
        return comentario;
    }
    public Usuario getAutor() {
        return autor;
    }
    public Articulo getArticulo() {
        return articulo;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
