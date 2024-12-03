package com.projetojavapoo.zwjuice.repositorio;

import com.projetojavapoo.zwjuice.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    @Query("SELECT CASE " +
            "WHEN u.genero = 'Masculino' THEN 'Masculino' " +
            "WHEN u.genero = 'Feminino' THEN 'Feminino' " +
            "ELSE 'Outro' END AS categoria, COUNT(u) " +
            "FROM Usuario u " +
            "GROUP BY categoria")
    List<Object[]> countUsuariosPorGeneroAgrupado();

}
