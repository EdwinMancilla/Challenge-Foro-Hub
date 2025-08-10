package challenge.forohub.demo.domain.topico;

import challenge.forohub.demo.domain.curso.CursoEnum;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);


    //METODO PARA OBTENER LOS 10 TOPICOS EN ORDEN ASCENDENTE POR FECHA DE CREACION
    List<Topico> findTop10ByOrderByFechaCreacionAsc();


    //METODO PARA BUSCAR TOPICOS POR CURSO Y AÑO
    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND FUNCTION('YEAR', t.fechaCreacion) = :anio")
    List<Topico> buscarPorCursoYAnio(@Param("curso") CursoEnum curso, @Param("anio") int anio);


    //METODO PARA DETALLAR UN TÓPICO POR ID
    Topico findByTituloAndMensaje(@NotBlank String titulo, @NotBlank String mensaje);
}
