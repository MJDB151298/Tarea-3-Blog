package Clases;

public class Comentario {
    private long id;
    private String comentario;
    private Usuario autor;
    private Articulo articulo;

    public Comentario(){

    }

    public Comentario(long id, String comentario, Usuario autor, Articulo articulo){
        this.id = id;
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
