package Clases;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Articulo {
    private long id;
    private String titulo;
    private String cuerpo;
    private Usuario autor;
    private Date fecha;
    private String categoria;
    private ArrayList<Comentario> listaComentarios;
    private ArrayList<Etiqueta> listaEtiquetas;
    private String cuerpoResumido;

    public Articulo(){

    }

    public Articulo(String titulo, String cuerpo, Usuario autor, String categoria){
        if(Controladora.getInstance().getMisArticulos().size() == 0){
            this.id = 1;
        }
        else{
            this.id = Controladora.getInstance().getMisArticulos().
                    get(Controladora.getInstance().getMisArticulos().size()-1).getId()+1;
        }
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = Date.valueOf(LocalDate.now());
        this.categoria = categoria;
        this.listaComentarios = new ArrayList<>();
        this.listaEtiquetas = new ArrayList<>();
        this.cuerpoResumido = "";
    }

    public long getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getCuerpo() {
        return cuerpo;
    }
    public Usuario getAutor() {
        return autor;
    }
    public Date getFecha() {
        return fecha;
    }
    public String getCategoria() { return categoria; }
    public ArrayList<Etiqueta> getListaEtiquetas() { return listaEtiquetas; }
    public ArrayList<Comentario> getListaComentarios() {
        return listaComentarios;
    }
    public String getCuerpoResumido(){
        cuerpoResumido = "";
        if(getCuerpo().length() > 70){
            int i = 0;
            while(i < 70){
                cuerpoResumido += getCuerpo().charAt(i);
                i++;
            }
            cuerpoResumido += 70;
        }
        else{
            cuerpoResumido = getCuerpo();
        }
        return cuerpoResumido;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
    public void setListaComentarios(ArrayList<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }
    public void setListaEtiquetas(ArrayList<Etiqueta> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }
}
