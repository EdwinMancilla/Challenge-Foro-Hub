package challenge.forohub.demo.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(Long id,
                                 String titulo,
                                 String mensaje,
                                 LocalDateTime fechaCreacion,
                                 String autorLogin,
                                 String curso,
                                 String estado) {



    public DatosListadoTopico(Topico topico) {
        this(topico.getId(),
             topico.getTitulo(),
             topico.getMensaje(),
             topico.getFechaCreacion(),
             topico.getAutor().getLogin(),
             topico.getCurso().name(),
             topico.getEstado());
    }
}
