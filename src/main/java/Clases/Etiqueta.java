package Clases;

public class Etiqueta {
    private long id;
    private String etiqueta;

    public Etiqueta(){

    }

    public Etiqueta(long id, String etiqueta){
        this.id = id;
        this.etiqueta = etiqueta;
    }

    public long getId() { return id; }
    public String getEtiqueta() { return etiqueta; }

    public void setId(int id) { this.id = id; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }
}
