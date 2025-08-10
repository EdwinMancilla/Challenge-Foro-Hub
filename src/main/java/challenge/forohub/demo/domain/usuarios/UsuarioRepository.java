package challenge.forohub.demo.domain.usuarios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login); // Busca un usuario por su login y devuelve un objeto UserDetails, que es una interfaz de Spring Security que contiene los detalles del usuario, como su nombre de usuario, contraseña, roles, etc. Esto permite que el servicio de autenticación pueda cargar los detalles del usuario para la autenticación.


    boolean existsByLogin(String login);
}
