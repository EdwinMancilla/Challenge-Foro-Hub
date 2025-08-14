package challenge.forohub.demo.domain.usuarios;

public record DatosListadoUsuario(Long id,
                                  String nombre,
                                  String email,
                                  String login) {

    public DatosListadoUsuario(Usuario u) {
        this(u.getId(), u.getNombre(), u.getEmail(), u.getLogin());
    }
}
