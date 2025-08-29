package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "CLUBE_USUARIO")
public class ClubeUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQUENCIAL")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "LOGIN", length = 40, nullable = false)
    private String login;

    @Column(name = "NAME", length = 120)
    private String name;

    @Column(name = "EMAIL", length = 120)
    private String email;

    @Column(name = "ACTIVE")
    private Boolean active;
}
