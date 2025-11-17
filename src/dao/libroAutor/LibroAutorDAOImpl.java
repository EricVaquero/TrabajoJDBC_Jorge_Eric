package dao.libroAutor;

import dao.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LibroAutorDAOImpl implements LibroAutorDAO {

    @Override
    public void addRelacion(int idLibro, int idAutor) throws Exception {
        String sql = "INSERT INTO libro_autor (id_libro, id_autor) VALUES (?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            ps.setInt(2, idAutor);
            ps.executeUpdate();
            System.out.println("DAO: Relación Libro-Autor insertada -> LibroID: " + idLibro + ", AutorID: " + idAutor);
        }
    }

    @Override
    public void deleteRelacion(int idLibro, int idAutor) throws Exception {
        String sql = "DELETE FROM libro_autor WHERE id_libro=? AND id_autor=?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            ps.setInt(2, idAutor);
            ps.executeUpdate();
            System.out.println("DAO: Relación Libro-Autor eliminada -> LibroID: " + idLibro + ", AutorID: " + idAutor);
        }
    }

    @Override
    public List<Integer> getAutoresDeLibro(int idLibro) throws Exception {
        String sql = "SELECT id_autor FROM libro_autor WHERE id_libro=?";
        List<Integer> autores = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    autores.add(rs.getInt("id_autor"));
                }
            }
        }
        return autores;
    }

    @Override
    public List<Integer> getLibrosDeAutor(int idAutor) throws Exception {
        String sql = "SELECT id_libro FROM libro_autor WHERE id_autor=?";
        List<Integer> libros = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAutor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(rs.getInt("id_libro"));
                }
            }
        }
        return libros;
    }
}

