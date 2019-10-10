package Clases;

import java.util.ArrayList;
import java.util.Date;

public class Articulo {
    public long id;
    public String titulo;
    public String cuerpo;
    public Usuario autor;
    public Date fecha;
    ArrayList<Comentario> listaComentarios;
    ArrayList<Etiqueta> listaEtiquetas;

    public Articulo(){

    }

    public Articulo(long id, String titulo, String cuerpo, Usuario autor){
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = new Date(System.currentTimeMillis());
        this.listaComentarios = new ArrayList<>();
        this.listaEtiquetas = new ArrayList<>();
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
    public ArrayList<Comentario> getListaComentarios() {
        return listaComentarios;
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
