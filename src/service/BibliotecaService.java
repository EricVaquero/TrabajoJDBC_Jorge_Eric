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

    public void asignarAutorALibro(int idLibro, int idAutor) {
        try {
            libroAutorDAO.addRelacion(idLibro, idAutor);
            System.out.println("Service: autor asignado al libro");
        } catch (Exception e) {
            System.err.println("Error al asignar autor al libro: " + e.getMessage());
        }
    }

    public void eliminarAutorDeLibro(int idLibro, int idAutor) {
        try {
            libroAutorDAO.deleteRelacion(idLibro, idAutor);
            System.out.println("Service: autor quitado del libro");
        } catch (Exception e) {
            System.err.println("Error al quitar autor del libro: " + e.getMessage());
        }
    }

    public List<Autor> listarAutoresDeLibro(int idLibro) {
        try {
            List<Integer> idsAutores = libroAutorDAO.getAutoresDeLibro(idLibro);
            List<Autor> autores = new java.util.ArrayList<>();

            for (int idAutor : idsAutores) {
                Autor autor = autorDAO.getAutorById(idAutor);
                if (autor != null) autores.add(autor);
            }

            System.out.println("Autores del libro " + idLibro + ":");
            for (Autor a : autores) {
                System.out.println(" - " + a);
            }

            return autores;
        } catch (Exception e) {
            System.err.println("Error al listar autores del libro: " + e.getMessage());
            return List.of();
        }
    }

    public List<Libro> listarLibrosDeAutor(int idAutor) {
        try {
            List<Integer> idsLibros = libroAutorDAO.getLibrosDeAutor(idAutor);
            List<Libro> libros = new java.util.ArrayList<>();

            for (int idLibro : idsLibros) {
                Libro libro = libroDAO.getLibroById(idLibro);
                if (libro != null) libros.add(libro);
            }

            System.out.println("Libros del autor " + idAutor + ":");
            for (Libro l : libros) {
                System.out.println(" - " + l);
            }

            return libros;
        } catch (Exception e) {
            System.err.println("Error al listar libros del autor: " + e.getMessage());
            return List.of();
        }
    }
}