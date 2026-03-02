package me.proyecto.tienda.model;

import jakarta.persistence.*;
import lombok.*;
import me.proyecto.tienda.model.enums.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false, unique = true)
    private Orden orden;

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    @Column(name = "numero_seguimiento")
    private String numeroSeguimiento;

    @Column(name = "fecha_envio", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_entrega_estimada")
    private LocalDateTime fechaEntregaEstimada;

    @Column(name = "fecha_entrega_real")
    private LocalDateTime fechaEntregaReal;

    @Column(name = "direccion_envio")
    private String direccionEnvio;
}
