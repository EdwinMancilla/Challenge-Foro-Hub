package challenge.forohub.demo.domain.topico;

import challenge.forohub.demo.domain.curso.CursoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(@NotBlank String titulo,
                                    @NotBlank String mensaje,
                                    @NotBlank String estado,
                                    @NotNull CursoEnum curso) {
}
