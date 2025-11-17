package service;

import dao.autor.AutorDAO;
import dao.libro.LibroDAO;
import dao.libroAutor.LibroAutorDAO;
import model.Libro;
import model.Autor;

import java.util.List;

public class BibliotecaService {
    private LibroDAO libroDAO;
    private AutorDAO autorDAO;
    private UsuarioDAO usuarioDAO;
    private PrestamoDAO prestamoDAO;
    private LibroAutorDAO libroAutorDAO;

    public BibliotecaService(
            LibroDAO libroDAO,
            AutorDAO autorDAO,
            UsuarioDAO usuarioDAO,
            PrestamoDAO prestamoDAO,
            LibroAutorDAO libroAutorDAO
    ) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
        this.usuarioDAO = usuarioDAO;
        this.prestamoDAO = prestamoDAO;
        this.libroAutorDAO = libroAutorDAO;
    }

    public void registrarLibro(String titulo, String isbn) {
        try {
            Libro libro = new Libro(0, titulo, isbn);
            libroDAO.addLibro(libro);
        } catch (Exception e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
        }
    }

    public List<Libro> listarLibros() {
        try {
            return libroDAO.getAllLibros();
        } catch (Exception e) {
            System.err.println("Error al listar libros: " + e.getMessage());
            return List.of();
        }
    }

    public void cambiarTitulo(int id, String nuevoTitulo) {
        try {
            Libro libro = libroDAO.getLibroById(id);
            if (libro != null) {
                libro.setTitulo(nuevoTitulo);
                libroDAO.updateLibro(libro);
            } else {
                System.out.println("Service: No se encontró el libro con id= " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
        }
    }

    public void eliminarLibro(int id) {
        try {
            libroDAO.deleteLibro(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
        }
    }

    public void registrarAutor(String nombre) {
        try {
            Autor autor = new Autor(0, nombre);
            autorDAO.addAutor(autor);
        } catch (Exception e) {
            System.err.println("Error al registrar autor: " + e.getMessage());
        }
    }

    public List<Autor> listarAutores() {
        try {
            return autorDAO.getAllAutores();
        } catch (Exception e) {
            System.err.println("Error al listar Autores: " + e.getMessage());
            return List.of();
        }
    }

    public void cambiarAutor(int id, String nuevoNombre) {
        try {
            Autor autor = autorDAO.getAutorById(id);
            if (autor != null) {
                autor.setNombre(nuevoNombre);
                autorDAO.updateAutor(autor);
            } else {
                System.out.println("Service: No se encontró el autor con id= " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el autor: " + e.getMessage());
        }
    }

    public void eliminarAutor(int id) {
        try {
            autorDAO.deleteAutor(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar autor: " + e.getMessage());
        }
    }
}
