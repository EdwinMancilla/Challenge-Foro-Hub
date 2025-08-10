package challenge.forohub.demo.domain.topico;

import challenge.forohub.demo.domain.curso.CursoEnum;

import java.time.LocalDateTime;

public record DatosDetalleTopico(Long id,
                                 String titulo,
                                 String mensaje,
                                 LocalDateTime fechaCreacion,
                                 String estado,
                                 String autorNombre,
                                 CursoEnum curso) {

    public DatosDetalleTopico(Topico topico) {
        this(topico.getId(),
             topico.getTitulo(),
             topico.getMensaje(),
             topico.getFechaCreacion(),
             topico.getEstado(),
             topico.getAutor().getNombre(),
             topico.getCurso());
    }
}
