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
    @Column(name = "idCliente", nullable = false,unique = true)
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

    public Cliente() {
    }

    public void addPrestamo(Prestamo prestamo) {
        this.prestamos.add(prestamo);
        prestamo.setCliente(this);
    }

}
