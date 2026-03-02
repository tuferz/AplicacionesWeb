package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Resena;
import me.proyecto.tienda.model.Producto;
import me.proyecto.tienda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByProducto(Producto producto);
    List<Resena> findByUsuario(Usuario usuario);
    List<Resena> findByProductoOrderByCalificacionDesc(Producto producto);
    List<Resena> findByProductoAndCalificacionGreaterThanEqual(Producto producto, Integer calificacion);
}

