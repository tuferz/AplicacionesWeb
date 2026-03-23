package me.proyecto.tienda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    
    private String token;
    private String tipo;
    private Long usuarioId;
    private String nombreUsuario;
    private String email;
    private String mensaje;
}

