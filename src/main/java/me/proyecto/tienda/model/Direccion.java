package me.proyecto.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "direcciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank
    private String calle;

    @NotBlank
    private String numero;

    private String apartamento;

    @NotBlank
    private String ciudad;

    @NotBlank
    private String estado;

    @NotBlank
    @Column(name = "codigo_postal")
    private String codigoPostal;

    @NotBlank
    private String pais;

    private String descripcion;

    @Column(name = "es_predeterminada")
    private Boolean esPredeterminada;
}
