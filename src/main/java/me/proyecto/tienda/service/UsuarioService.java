package me.proyecto.tienda.service;

import me.proyecto.tienda.model.Usuario;
import me.proyecto.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UsuarioService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Usuario guardar(Usuario usuario){
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new IllegalArgumentException("Email ya existe");
        }

        usuario.setContrasena(hashearContrasena(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long idUsuario){
        usuarioRepository.deleteById(idUsuario);
    }

    private String hashearContrasena(String contrasena){
        return passwordEncoder.encode(contrasena);
    }
}
