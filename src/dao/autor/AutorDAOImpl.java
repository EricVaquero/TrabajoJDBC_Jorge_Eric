package dao.autor;

import dao.ConnectionManager;
import model.Autor;
import model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO{
    @Override
    public void addAutor(Autor autor) throws Exception {
        String sql = "INSERT INTO autor (nombre) VALUES (?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, autor.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) autor.setId(rs.getInt(1));
            }

            System.out.println("DAO: Autor insertado -> " + autor);
        }
    }

    @Override
    public List<Autor> getAllAutores() throws Exception {
        String sql = "SELECT id, nombre FROM autor";
        List<Autor> lista = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Autor(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return lista;
    }

    @Override
    public Autor getAutorById(int id) throws Exception {
        String sql = "SELECT id, nombre FROM autor WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Autor(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        }
        return null;
    }

    @Override
    public void updateAutor(Autor autor) throws Exception {
        String sql = "UPDATE autor SET nombre=? WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, autor.getNombre());
            ps.setInt(2, autor.getId());
            ps.executeUpdate();
            System.out.println("DAO: Autor actualizado -> " + autor);
        }

    }

    @Override
    public void deleteAutor(int id) throws Exception {
        String sql = "DELETE FROM autor WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("DAO: Autor eliminado (id=" + id + ")");
        }
    }
}
