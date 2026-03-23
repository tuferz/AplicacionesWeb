package me.proyecto.tienda.controller;

import me.proyecto.tienda.dto.LoginRequest;
import me.proyecto.tienda.dto.LoginResponse;
import me.proyecto.tienda.model.Usuario;
import me.proyecto.tienda.service.UsuarioService;
import me.proyecto.tienda.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar usuario
            Usuario usuario = usuarioService.autenticar(
                loginRequest.getNombreUsuario(),
                loginRequest.getContrasena()
            );
            
            // Generar token JWT
            String token = jwtUtil.generateToken(usuario.getId(), usuario.getNombreUsuario());
            
            // Construir respuesta
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .tipo("Bearer")
                    .usuarioId(usuario.getId())
                    .nombreUsuario(usuario.getNombreUsuario())
                    .email(usuario.getEmail())
                    .mensaje("Login exitoso")
                    .build();
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            LoginResponse errorResponse = LoginResponse.builder()
                    .mensaje("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            LoginResponse errorResponse = LoginResponse.builder()
                    .mensaje("Error interno del servidor")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

