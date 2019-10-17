package Clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Articulo {
    public int id;
    public String titulo;
    public String cuerpo;
    public Usuario autor;
    public Date fecha;
    public ArrayList<Comentario> listaComentarios;
    public ArrayList<Etiqueta> listaEtiquetas;
    public String cuerpoResumido;

    public Articulo(){

    }

    public Articulo(int id, String titulo, String cuerpo, Usuario autor){
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = new Date(System.currentTimeMillis());
        this.listaComentarios = new ArrayList<>();
        this.listaEtiquetas = new ArrayList<>();
        this.cuerpoResumido = "";
    }

    public int getId() {
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
    public String getCuerpoResumido(){
        StringTokenizer st = new StringTokenizer(getCuerpo());
        if(st.countTokens() > 30){
            int i = 0;
            int spaces = 0;
            while(spaces <= 30){
                cuerpoResumido += cuerpo.charAt(i);
                if(cuerpo.charAt(i) == ' '){
                    spaces++;
                }
                i++;
            }
            cuerpoResumido += "...";
        }
        else{
            cuerpoResumido = getCuerpo();
        }
        return cuerpoResumido;
    }

    public void setId(int id) {
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
