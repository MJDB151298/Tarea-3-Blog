package Clases;

public class Etiqueta {
    private long id;
    private String etiqueta;

    public Etiqueta(){

    }

    public Etiqueta(String etiqueta){
        this.etiqueta = etiqueta;
    }

    public long getId() { return id; }
    public String getEtiqueta() { return etiqueta; }

    public void setId(long id) { this.id = id; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }
}
