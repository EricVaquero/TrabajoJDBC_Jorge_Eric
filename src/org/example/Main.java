package org.example;

import dao.autor.AutorDAO;
import dao.autor.AutorDAOImpl;
import dao.libro.LibroDAO;
import dao.libro.LibroDAOImpl;
import dao.libroAutor.LibroAutorDAO;
import dao.libroAutor.LibroAutorDAOImpl;
import dao.prestamo.PrestamoDAO;
import dao.prestamo.PrestamoDAOImpl;
import dao.usuario.UsuarioDAO;
import dao.usuario.UsuarioDAOImpl;
import model.Usuario;
import service.BibliotecaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        LibroDAO libroDAO = new LibroDAOImpl();
        AutorDAO autorDAO = new AutorDAOImpl();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        PrestamoDAO prestamoDAO = new PrestamoDAOImpl();
        LibroAutorDAO libroAutorDAO = new LibroAutorDAOImpl();

        BibliotecaService servicio = new BibliotecaService(libroDAO, autorDAO, usuarioDAO, prestamoDAO, libroAutorDAO);

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENÚ BIBLIOTECA =====");
            System.out.println("1. Crear libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Modificar título de libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Crear usuario");
            System.out.println("6. Listar usuarios");
            System.out.println("7. Crear préstamo");
            System.out.println("8. Listar préstamos");
            System.out.println("9. Modificar préstamo");
            System.out.println("10. Eliminar préstamo");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt(); sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Nombre del autor: ");
                    String nombreAutor = sc.nextLine();
                    servicio.registrarLibro(titulo, isbn, nombreAutor);
                }
                case 2 -> servicio.listarLibros().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID del libro: ");
                    int idLibro = sc.nextInt(); sc.nextLine();
                    System.out.print("Nuevo título: ");
                    String nuevoTitulo = sc.nextLine();
                    servicio.cambiarTitulo(idLibro, nuevoTitulo);
                }
                case 4 -> {
                    System.out.print("ID del libro a eliminar: ");
                    int idLibro = sc.nextInt(); sc.nextLine();
                    servicio.eliminarLibro(idLibro);
                }
                case 5 -> {
                    System.out.print("Nombre del usuario: ");
                    String nombreUsuario = sc.nextLine();
                    servicio.registrarUsuario(new Usuario(0, nombreUsuario));
                }
                case 6 -> servicio.listarUsuarios().forEach(System.out::println);
                case 7 -> servicio.añadirPrestamo();
                case 8 -> servicio.listarPrestamos().forEach(System.out::println);
                case 9 -> {
                    System.out.print("ID del préstamo: ");
                    int idPrestamo = sc.nextInt(); sc.nextLine();
                    System.out.print("Fecha inicio (YYYY-MM-DD): ");
                    String fechaInicio = sc.nextLine();
                    System.out.print("Fecha fin (YYYY-MM-DD): ");
                    String fechaFin = sc.nextLine();
                    servicio.actualizarPrestamo(idPrestamo, fechaInicio, fechaFin);
                }
                case 10 -> {
                    System.out.print("ID del préstamo a eliminar: ");
                    int idPrestamo = sc.nextInt(); sc.nextLine();
                    servicio.eliminarPrestamo(idPrestamo);
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }
}
