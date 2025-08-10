package challenge.forohub.demo.domain.topico;

import challenge.forohub.demo.domain.curso.CursoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosNuevoTopico(@NotBlank String titulo,
                               @NotBlank String mensaje,
                               @NotNull String autorLogin,
                               @NotNull CursoEnum curso) {

    public DatosNuevoTopico(Topico topico) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getAutor().getLogin(), topico.getCurso());
    }
}
