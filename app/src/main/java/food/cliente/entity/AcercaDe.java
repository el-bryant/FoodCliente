package food.cliente.entity;

/**
 * By: El Bryant
 */

public class AcercaDe {
    String idAcercaDe;
    String titulo;
    String contenido;

    public AcercaDe(String idAcercaDe, String titulo, String contenido) {
        this.idAcercaDe = idAcercaDe;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getIdAcercaDe() {
        return idAcercaDe;
    }

    public void setIdAcercaDe(String idAcercaDe) {
        this.idAcercaDe = idAcercaDe;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
