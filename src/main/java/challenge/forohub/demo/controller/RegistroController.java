package challenge.forohub.demo.controller;


import challenge.forohub.demo.domain.usuarios.DatosRegistroUsuario;
import challenge.forohub.demo.domain.usuarios.Usuario;
import challenge.forohub.demo.domain.usuarios.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos) {
        var usuario = new Usuario(
                null, // id null para que se genere automáticamente
                datos.nombre(),
                datos.email(),
                datos.login(),
                passwordEncoder.encode(datos.contrasena()),
                null // perfiles, o asigna uno por defecto si quieres
        );



        if(usuarioRepo.existsByLogin(datos.login())){
            return ResponseEntity.badRequest().body("El login ya está en uso");
        }

        usuarioRepo.save(usuario);


        return ResponseEntity.ok().body("Usuario registrado exitosamente");

    }

}
