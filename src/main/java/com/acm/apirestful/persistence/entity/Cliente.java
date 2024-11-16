package com.acm.apirestful.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@ToString(exclude = {
        "prestamos"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente {

    @Id
    @Column(name = "id_cliente", nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;
    @Setter
    private String nombre;
    @Setter
    private String correo;
    @Setter
    private String telefono;
    @Setter
    private Boolean estadoCuenta;

    /*
     * Relacion bidireccional uno a muchos [cliente (1) <--> prestamo(n)], un cliente tiene varios prestamos,
     * y uno o varios prestamos pertenecen unicamente a un cliente
     */
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Prestamo> prestamos = new ArrayList<>();

    /*
     * Relacion unidireccional uno a uno [cliente(1) --> user(1)], un cliente tiene un user,
     * y un user pertenecen unicamente a un cliente
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_userFK", nullable = false,unique = true)
    private UserEntity user;

    public Cliente() {
    }

    public void addPrestamo(Prestamo prestamo) {
        this.prestamos.add(prestamo);
        prestamo.setCliente(this);
    }

}
