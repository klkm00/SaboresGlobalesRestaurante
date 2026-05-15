package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length =10, nullable = false)
    private String rut;
    @Column(nullable = false)
    private String nombres;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false)
    private String correo;
    @ManyToOne
    @JoinColumn(name = "rol")
    private Rol rol;
}

