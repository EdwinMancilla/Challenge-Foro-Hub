package challenge.forohub.demo.controller;

import challenge.forohub.demo.domain.curso.CursoEnum;
import challenge.forohub.demo.domain.topico.*;
import challenge.forohub.demo.domain.usuarios.Usuario;
import challenge.forohub.demo.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;

    //METODO PARA REGISTRAR UN NUEVO TÓPICO
    @Transactional
    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosNuevoTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        UserDetails autorDetails = usuarioRepo.findByLogin(datos.autorLogin());

        if (autorDetails == null) {
            return ResponseEntity.badRequest().body("No existe usuario con ese email");
        }

        if (!(autorDetails instanceof Usuario)) {
            return ResponseEntity.badRequest().body("Usuario inválido");
        }

        Usuario autor = (Usuario) autorDetails;

        // Verificar si ya existe un tópico con el mismo título y mensaje
        // Esto es para evitar duplicados
        boolean existeTopico = topicoRepo.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existeTopico) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje");
        }

        Topico topico = new Topico(datos, autor, datos.curso());

        topicoRepo.save(topico);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosNuevoTopico(topico));
    }

    //METODO PARA LISTAR TODOS LOS TÓPICOS

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        var pageTopicos = topicoRepo.findAll(paginacion);

        // Convertir Page<Topico> a Page<DatosListadoTopico>
        var pageDto = pageTopicos.map(DatosListadoTopico::new);

        return ResponseEntity.ok(pageDto);

    }

    //METODO PARA OBTENER LOS 10 TÓPICOS en ORDEN ASCENDENTE POR FECHA DE CREACIÓN
    @GetMapping("/primeros10")
    public ResponseEntity<List<DatosListadoTopico>> listarPrimeros10() {
        List<Topico> topicos = topicoRepo.findTop10ByOrderByFechaCreacionAsc();
        List<DatosListadoTopico> resultado = topicos.stream()
                .map(DatosListadoTopico::new)
                .toList();
        return ResponseEntity.ok(resultado);
    }

    //METODO PARA BUSCAR TÓPICOS POR CURSO Y AÑO
    @GetMapping("/buscar")
    public ResponseEntity<List<DatosListadoTopico>> buscarPorCursoYAnio(
            @RequestParam String curso,
            @RequestParam int anio) {
        CursoEnum cursoEnum;
        try {
            cursoEnum = CursoEnum.valueOf(curso.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // curso no válido
        }

        List<Topico> topicos = topicoRepo.buscarPorCursoYAnio(cursoEnum, anio);
        List<DatosListadoTopico> resultado = topicos.stream()
                .map(DatosListadoTopico::new)
                .toList();

        return ResponseEntity.ok(resultado);
    }


    //METODO PARA DETALLAR UN TÓPICO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopicoPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("El ID debe ser un número válido y mayor que cero.");
        }

        var topicoOpt = topicoRepo.findById(id);
        if (topicoOpt.isPresent()) {
            var dto = new DatosDetalleTopico(topicoOpt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(404).body("No se encontró un tópico con el ID: " + id);
        }
    }

    //METODO PARA ACTUALIZAR UN TÓPICO
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("El ID debe ser un número válido y mayor que cero.");
        }

        Optional<Topico> topicoOpt = topicoRepo.findById(id);
        if (!topicoOpt.isPresent()) {  // Aquí usas isPresent() para validar existencia
            return ResponseEntity.status(404).body("No se encontró un tópico con el ID: " + id);
        }

        Topico topico = topicoOpt.get();

        // Validar si ya existe otro tópico con el mismo título y mensaje para evitar duplicados
        boolean existeTopico = topicoRepo.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existeTopico) {
            Topico topicoConMismoTituloMensaje = topicoRepo.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
            if (topicoConMismoTituloMensaje != null && !topicoConMismoTituloMensaje.getId().equals(id)) {
                return ResponseEntity.badRequest().body("Ya existe otro tópico con el mismo título y mensaje");
            }
        }

        // Actualizar campos permitidos
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setEstado(datos.estado());
        topico.setCurso(datos.curso());

        topicoRepo.save(topico);

        return ResponseEntity.ok("Tópico actualizado correctamente");
    }

    //METODO PARA ELIMINAR UN TÓPICO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("El ID debe ser un número válido y mayor que cero.");
        }

        Optional<Topico> topicoOpt = topicoRepo.findById(id);
        if (!topicoOpt.isPresent()) {
            return ResponseEntity.status(404).body("No se encontró un tópico con el ID: " + id);
        }

        topicoRepo.deleteById(id);
        return ResponseEntity.ok("Tópico eliminado correctamente");
    }


}
