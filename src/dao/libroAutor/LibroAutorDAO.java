package dao.libroAutor;

import model.LibroAutor;

import java.util.List;

public interface LibroAutorDAO {
    void addRelacion(int idLibro, int idAutor) throws Exception;
    void deleteRelacion(int idLibro, int idAutor) throws Exception;
    List<Integer> getAutoresDeLibro(int idLibro) throws Exception;
    List<Integer> getLibrosDeAutor(int idAutor) throws Exception;
}

