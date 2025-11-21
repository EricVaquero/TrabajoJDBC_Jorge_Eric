package service;

import dao.autor.AutorDAO;
import dao.libro.LibroDAO;
import dao.libroAutor.LibroAutorDAO;
import dao.usuario.UsuarioDAO;
import dao.prestamo.PrestamoDAO;

import model.Libro;
import model.Autor;
import model.Usuario;
import model.Prestamo;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

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

    public void registrarLibro(String titulo, String isbn, String nombreAutor) {
        try {
            Libro libro = new Libro(0, titulo, isbn);
            libroDAO.addLibro(libro);

            Autor autorExistente = null;
            List<Autor> listaAutores = listarAutores();
            for (Autor a : listaAutores) {
                if (a.getNombre().equalsIgnoreCase(nombreAutor)) {
                    autorExistente = a;
                    break;
                }
            }

            if (autorExistente == null) {
                autorExistente = new Autor(0, nombreAutor);
                autorDAO.addAutor(autorExistente);
            }

            libroAutorDAO.addRelacion(libro.getId(), autorExistente.getId());

            System.out.println("Libro creado y autor asignado: " + libro + " -> " + autorExistente);

        } catch (Exception e) {
            System.err.println("Error al registrar libro con autor: " + e.getMessage());
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

    public void registrarUsuario(Usuario usuario) {
        try {
            usuarioDAO.addUsuario(usuario);
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public List<Usuario> listarUsuarios() {
        try {
            return usuarioDAO.getUsuarios();
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            return List.of();
        }
    }

    public void actualizarUsuario(int id, Usuario usuario) {
        try {
            Usuario usuario1 = usuarioDAO.getUsuarioById(id);
            if (usuario1 != null) {
                usuario1.setNombre(usuario.getNombre());
                usuarioDAO.updateUsuario(usuario1);
            } else {
                System.out.println("Error al obtener el usuario con id= " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(int id) {
        try {
            usuarioDAO.deleteUsuario(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public void añadirPrestamo() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("======Prestamo=======");

            List<Usuario> listaUsuarios = usuarioDAO.getUsuarios();

            if (listaUsuarios.isEmpty()) {
                System.out.println("No hay usuarios. Cree uno nuevo");
            } else {
                System.out.println("Usuarios encontrados");
                for (Usuario usuario : listaUsuarios) {
                    System.out.println(usuario.getId() + " - " + usuario.getNombre());
                }
            }

            System.out.println("Introduce el id del usuario al que desea crear un prestamo (0 para crear un usuario nuevo)");
            int idUsuario = sc.nextInt();
            Usuario usuario = new Usuario();

            if (idUsuario == 0) {
                System.out.println("Dime el nombre del usuario: ");
                usuario.setNombre(sc.next());
                usuarioDAO.addUsuario(usuario);
                idUsuario = usuario.getId();
                System.out.println("Usuario creado con id: " + idUsuario);
            } else {
                usuario = usuarioDAO.getUsuarioById(idUsuario);
                if (usuario == null) {
                    System.out.println("No existe el usuario con id: " + idUsuario);
                    return;
                }
                System.out.println("Usuario seleccionado");
            }

            List<Libro> listaLibros = libroDAO.getAllLibros();

            if (listaLibros.isEmpty()) {
                System.out.println("No hay lista de libros. Cree un nuevo");
            } else {
                System.out.println("Libros encontrados");
                for (Libro libro : listaLibros) {
                    System.out.println(libro.getId() + " - " + libro.getTitulo());
                }
            }
            System.out.println("Introduce el id del libro: ");
            int idLibro = sc.nextInt();
            sc.nextLine();
            Libro libro = new Libro();

            if (idLibro == 0) {
                System.out.println("Dime el titulo del libro: ");
                libro.setTitulo(sc.nextLine());
                System.out.println("Dime el ISBN del libro: ");
                libro.setIsbn(sc.nextLine());
                libroDAO.addLibro(libro);
                idLibro = libro.getId();
                System.out.println("Libro creado con id: " + idLibro);
            } else {
                libro = libroDAO.getLibroById(idLibro);
                if (libro == null) {
                    System.out.println("No existe el libro con id: " + idLibro);
                    return;
                }
                System.out.println("Libro seleccionado");
            }

            Prestamo prestamo = new Prestamo();
            prestamo.setUsuarioId(idUsuario);
            prestamo.setLibroId(idLibro);

            System.out.println("Dime la fecha de inicio del prestamo: ");
            prestamo.setFechaInicio(Date.valueOf(sc.nextLine()));

            System.out.println("Dime la fecha de finaliza del prestamo: ");
            prestamo.setFechaFin(Date.valueOf(sc.nextLine()));

            prestamoDAO.addPrestamo(prestamo);

            System.out.println("Prestamo creado ");

        } catch (Exception e) {
            System.err.println("Error al crear el prestamo: " + e.getMessage());
        }
    }

    public List<Prestamo> listarPrestamos() {
        try {
            return prestamoDAO.getPrestamos();
        } catch (Exception e) {
            System.err.println("Error al obtener prestamos: " + e.getMessage());
            return List.of();
        }
    }

    public void actualizarPrestamo(int id, String fechaInicio, String fechaFinalizacion) {
        try {
            Prestamo prestamo = prestamoDAO.getPrestamoById(id);
            if (prestamo != null) {
                prestamo.setFechaInicio(Date.valueOf(fechaInicio));
                prestamo.setFechaFin(Date.valueOf(fechaFinalizacion));
                prestamoDAO.updatePrestamo(prestamo);
            } else {
                System.out.println("Error al encontrar el prestamo con id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener prestamo: " + e.getMessage());
        }
    }

    public void eliminarPrestamo(int id) {
        try {
            prestamoDAO.deletePrestamo(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar el prestamo con id: " + e.getMessage());
        }
    }
}
