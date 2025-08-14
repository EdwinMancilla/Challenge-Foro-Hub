package challenge.forohub.demo.controller;

import challenge.forohub.demo.domain.respuesta.*;
import challenge.forohub.demo.domain.topico.TopicoRepository;
import challenge.forohub.demo.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private TopicoRepository topicoRepo;

    @Transactional
    @PostMapping

    public ResponseEntity crearRespuesta(@RequestBody DatosNuevaRespuesta datos){
        var usuario = usuarioRepo.findById(datos.autorId());
        if(usuario.isEmpty()){
            return ResponseEntity.badRequest().body("Usuario No Encontrado");
        }

        var topico = topicoRepo.findById(datos.topicoId());
        if (topico .isEmpty()) {
            return ResponseEntity.badRequest().body("Tópico No Encontrado");
        }

        var respuesta = new Respuesta(datos.mensaje(), usuario.get(), topico.get());
        respuestaRepo.save(respuesta);

        return ResponseEntity.ok(new DatosListadoRespuesta(respuesta));

    }


// METODO PARA LISTAR RESPUESTAS POR TÓPICO
    @GetMapping("/topico/{topicoId}")
    public ResponseEntity<Page<DatosListadoRespuesta>> listarRespuestasPorTopico(
            @PathVariable Long topicoId,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {

        Page<Respuesta> pageRespuestas = respuestaRepo.findByTopicoId(topicoId, paginacion);

        Page<DatosListadoRespuesta> pageDto = pageRespuestas.map(DatosListadoRespuesta::new);

        return ResponseEntity.ok(pageDto);
    }

    //METODO PARA ACTUALIZAR UNA RESPUESTA
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarRespuesta(
            @PathVariable Long id,
            @RequestBody DatosActualizarRespuesta datos
    ) {
        var respuestaOpt = respuestaRepo.findById(id);

        if (respuestaOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Respuesta no encontrada");
        }

        var respuesta = respuestaOpt.get();
        respuesta.setMensaje(datos.mensaje()); // Actualiza solo el mensaje
        respuestaRepo.save(respuesta);

        return ResponseEntity.ok(new DatosListadoRespuesta(respuesta));
    }


    //METODO PARA ELIMINAR UNA RESPUESTA
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarRespuesta(@PathVariable Long id) {
        if (!respuestaRepo.existsById(id)) {
            return ResponseEntity.status(404).body("Respuesta no encontrada");
        }
        respuestaRepo.deleteById(id);
        return ResponseEntity.ok("Respuesta eliminada correctamente");
    }
}
