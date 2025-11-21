package dao.usuario;

import model.Usuario;

import java.util.List;

public interface UsuarioDAO {
    void addUsuario(Usuario usuario) throws Exception;
    List<Usuario> getUsuarios() throws Exception;
    Usuario getUsuarioById(int id) throws Exception;
    void updateUsuario(Usuario usuario) throws Exception;
    void deleteUsuario(int id) throws Exception;

}
