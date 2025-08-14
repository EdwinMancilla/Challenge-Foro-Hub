package challenge.forohub.demo.controller;

import challenge.forohub.demo.domain.usuarios.DatosListadoUsuario;
import challenge.forohub.demo.domain.usuarios.Usuario;
import challenge.forohub.demo.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

//METODO PARA OBTENER UN USUARIO POR ID
@GetMapping
public ResponseEntity listarUsuarios(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
    Page<Usuario> pageUsuarios = usuarioRepo.findAll(paginacion);
    // Mapear cada Usuario a DatosListadoUsuario
    Page<DatosListadoUsuario> pageDto = pageUsuarios.map(DatosListadoUsuario::new);

    return ResponseEntity.ok(pageDto);
}


}
