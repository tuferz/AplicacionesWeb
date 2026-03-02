package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Direccion;
import me.proyecto.tienda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByUsuario(Usuario usuario);
    Optional<Direccion> findByUsuarioAndEsPredeterminada(Usuario usuario, Boolean esPredeterminada);
}

