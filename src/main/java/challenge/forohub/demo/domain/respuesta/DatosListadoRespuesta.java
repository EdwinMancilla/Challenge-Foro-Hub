package challenge.forohub.demo.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListadoRespuesta(Long id,
                                    String mensaje,
                                    String autor,
                                    LocalDateTime fechaCreacion) {

    public DatosListadoRespuesta(Respuesta r){
        this(r.getId(),
                r.getMensaje(),
                r.getAutor().getNombre(),
                r.getFechaCreacion());
    }
}
