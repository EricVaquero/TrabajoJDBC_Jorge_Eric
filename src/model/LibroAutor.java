package model;

public class LibroAutor {
    private int idLibro;
    private int idAutor;

    public LibroAutor(int idLibro, int idAutor){
        this.idLibro = idLibro;
        this.idAutor = idAutor;
    }

    public int getIdLibro(){
        return idLibro;
    }

    public int getIdAutor() {
        return idAutor;
    }

    //Al ser una clase de "relación interna" no se necesitan setters
    //ni un toString (no se va a pedir imprimir todos los "libroAutor")
}
