package dao.usuario;

import dao.ConnectionManager;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void addUsuario(Usuario usuario) throws Exception {

        String sql = "INSERT INTO usuario (NOMBRE) VALUES (?)";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getNombre());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
            System.out.println("Usuario agreagado correctamente: " + usuario);
        }

    }

    @Override
    public List<Usuario> getUsuarios() throws Exception {

        String sql = "SELECT id, nombre FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt(1), rs.getString(2)));
            }

        }

        return usuarios;
    }

    @Override
    public Usuario getUsuarioById(int id) throws Exception {
        String sql = "SELECT nombre FROM usuario WHERE id = ?";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Usuario usuario = new Usuario(rs.getInt(1), rs.getString(2));
                }
            }
        }

        return null;
    }

    @Override
    public void updateUsuario(Usuario usuario) throws Exception {
        String sql = "UPDATE usuario SET nombre = ? WHERE id = ?";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, usuario.getNombre());
            ps.setInt(2, usuario.getId());
            ps.executeUpdate();
            System.out.println("Usuario actualizado correctamente: " + usuario);
        }

    }

    @Override
    public void deleteUsuario(int id) throws Exception {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Usuario eliminado correctamente: " + id);
        }

    }

}
