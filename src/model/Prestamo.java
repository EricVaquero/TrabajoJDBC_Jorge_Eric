package model;

import java.sql.Date;

public class Prestamo {

    private int id;
    private Date fechaInicio;
    private Date fechaFin;
    private int usuarioId;
    private int libroId;

    public Prestamo() {}

    public Prestamo(int id, Date fechaInicio, Date fechaFin, int usuarioId, int libroId) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuarioId = usuarioId;
        this.libroId = libroId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getLibroId() {
        return libroId;
    }

    public void setLibroId(int libroId) {
        this.libroId = libroId;
    }

    @Override
    public String toString() {
        return "Prestamo " + id + ":" +
                "\nFechaInicio: " + fechaInicio +
                "\nFechaFin: " + fechaFin;
    }
}
