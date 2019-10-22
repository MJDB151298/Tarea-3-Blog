package Clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.StringTokenizer;

public class Articulo {
    private long id;
    private String titulo;
    private String cuerpo;
    private Usuario autor;
    private Date fecha;
    private ArrayList<Comentario> listaComentarios;
    private ArrayList<Etiqueta> listaEtiquetas;
    private String cuerpoResumido;

    public Articulo(){

    }

    public Articulo(String titulo, String cuerpo, Usuario autor){
        this.id = 0;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = Date.valueOf(LocalDate.now());
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
    public ArrayList<Etiqueta> getListaEtiquetas() { return listaEtiquetas; }
    public ArrayList<Comentario> getListaComentarios() {
        return listaComentarios;
    }
    public String getCuerpoResumido(){
        cuerpoResumido = "";
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
