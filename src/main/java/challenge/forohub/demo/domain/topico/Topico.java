package challenge.forohub.demo.domain.topico;


import challenge.forohub.demo.domain.curso.CursoEnum;
import challenge.forohub.demo.domain.respuesta.Respuesta;
import challenge.forohub.demo.domain.usuarios.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "status")
    private String estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @Enumerated(EnumType.STRING)
    @Column(name = "curso")
    private CursoEnum curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas;


    public Topico(@Valid DatosNuevoTopico datos, Usuario autor, CursoEnum curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
        this.curso = curso;
        this.estado = "NO_RESPONDIDO"; // Asignar un estado por defecto
    }
}
