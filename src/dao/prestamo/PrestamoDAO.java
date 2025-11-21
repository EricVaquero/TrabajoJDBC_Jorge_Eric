package dao.prestamo;

import model.Prestamo;

import java.util.List;

public interface PrestamoDAO {
    void addPrestamo(Prestamo prestamo) throws Exception;
    List<Prestamo> getPrestamos() throws Exception;
    void getPrestamoTodo()throws Exception;
    Prestamo getPrestamoById(int id) throws Exception;
    void updatePrestamo(Prestamo prestamo) throws Exception;
    void deletePrestamo(int id) throws Exception;
}
