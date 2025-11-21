package dao.prestamo;

import dao.ConnectionManager;
import model.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {


    @Override
    public void addPrestamo(Prestamo prestamo) throws Exception {

        String sql = "INSERT INTO prestamo (fechaInicio, fechaFin, usuarioId, libroId) VALUES (?, ?, ?, ?)";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, prestamo.getFechaInicio());
            ps.setDate(2, prestamo.getFechaFin());
            ps.setInt(3, prestamo.getUsuarioId());
            ps.setInt(4, prestamo.getLibroId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    prestamo.setId(rs.getInt(1));
                }
            }
            System.out.println("Usuario agreagado correctamente: " + prestamo);

        }

    }

    @Override
    public List<Prestamo> getPrestamos() throws Exception {

        String sql = "SELECT prestamo.id, fechaInicio, fechaFin, usuarioId, usuario.nombre,libroId, libro.titulo FROM prestamo ";
        List<Prestamo> prestamos = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();){
            while(rs.next()){
                prestamos.add(new Prestamo(
                        rs.getInt(1),
                        rs.getDate(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getInt(5)));
            }
        }

        return prestamos;
    }

    @Override
    public void getPrestamoTodo() throws Exception {
        String sql = "SELECT prestamo.id, fechaInicio, fechaFin, usuarioId, usuario.nombre,libroId, libro.titulo FROM prestamo " +
                " inner join usuario on usuario.id = prestamo.usuarioId " +
                " inner join libro on libro.id = prestamo.libroId";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                int id = rs.getInt("pretamo.id");
                Date fechaInicio = rs.getDate("fechaInicio");
                Date fechaFin = rs.getDate("fechaFin");
                int usuarioId = rs.getInt("usuarioId");
                String nombreUsuario = rs.getString("usuario.nombre");
                int libroId = rs.getInt("libroId");
                String libroTitulo = rs.getString("libro.titulo");

                System.out.println("[" + id + "] " + fechaInicio + " - " + fechaFin +
                                    " - " + usuarioId + ". " + nombreUsuario +
                                    " - " + libroId + ". " + libroTitulo);
            }
        }
    }

    @Override
    public Prestamo getPrestamoById(int id) throws Exception {
        String sql = "SELECT fechaInicio, fechaFin, usuarioId, prestamoId FROM prestamo WHERE id = ?";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Prestamo prestamo = new Prestamo(rs.getInt(1),
                        rs.getDate(2), rs.getDate(3),
                        rs.getInt(4), rs.getInt(5));
            }
        }

        return null;
    }

    @Override
    public void updatePrestamo(Prestamo prestamo) throws Exception {

        String sql = "UPDATE prestamo SET fechaInicio = ?,  fechaFin = ?, usuarioId = ?, libroId = ? WHERE id = ?";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setDate(1, prestamo.getFechaInicio());
            ps.setDate(2, prestamo.getFechaFin());
            ps.setInt(3, prestamo.getUsuarioId());
            ps.setInt(4, prestamo.getLibroId());
            ps.executeUpdate();
            System.out.println("Prestamo actualizado correctamente: " + prestamo);
        }

    }

    @Override
    public void deletePrestamo(int id) throws Exception {
        String sql = "DELETE FROM prestamo WHERE id = ?";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Prestamo eliminado correctamente: " + id);
        }

    }
}
