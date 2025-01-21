package com.yourcompany.musicly.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@View(members = "descripcion")
@Getter @Setter
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 50, nullable = false, unique = true)
    @NotBlank(message = "La descripción no puede estar vacía.")
    String descripcion;
}


