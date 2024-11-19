package com.acm.apirestful.persistence.entity;

import com.acm.apirestful.persistence.entity.enums.EnumRol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column(name = "idRole",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private EnumRol rol;

}
