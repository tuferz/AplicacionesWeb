package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByApellido(String apellido);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    boolean existsByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmailAndContrasena(String email, String contrasena);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByApellidoContainingIgnoreCase(String apellido);
    Page<Usuario> findAll(Pageable pageable);
}
