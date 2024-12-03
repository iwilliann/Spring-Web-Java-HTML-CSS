package com.projetojavapoo.zwjuice.servico;

import com.projetojavapoo.zwjuice.entidades.Usuario;
import com.projetojavapoo.zwjuice.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServico implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder codificadorSenha;

    public UsuarioServico(UsuarioRepositorio usuarioRepositorio, PasswordEncoder codificadorSenha) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.codificadorSenha = codificadorSenha;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuarioNome = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return org.springframework.security.core.userdetails.User
                .withUsername(usuarioNome.getUsername())
                .password(usuarioNome.getSenha())
                .authorities(usuarioNome.getAuthorities())
                .build();
    }

    public void salvarUser(Usuario user) {
        user.setSenha(codificadorSenha.encode(user.getSenha()));
        usuarioRepositorio.save(user);
    }

    public Usuario encontrarPorUsername(String username) {
        return usuarioRepositorio.findByUsername(username).orElse(null);
    }

    public boolean verificarCredenciais(String username, String senha) {
        Usuario usuario = encontrarPorUsername(username);
        if (usuario != null) {
            return codificadorSenha.matches(senha, usuario.getSenha());
        }
        return false;
    }

    public boolean verificarUsuarioExistente(String usuario) {
        return usuarioRepositorio.findByUsername(usuario).isPresent();
    }

    public void salvarUsuario(Usuario usuario) {
        if (verificarUsuarioExistente(usuario.getUsername())) {
            throw new IllegalArgumentException("Usuário já cadastrado!");
        }
        usuario.setSenha(codificadorSenha.encode(usuario.getSenha()));
        usuarioRepositorio.save(usuario);
    }

    public Map<String, Long> obterEstatisticasDeGenero() {
        List<Object[]> resultados = usuarioRepositorio.countUsuariosPorGeneroAgrupado();
        Map<String, Long> generos = new HashMap<>();

        for (Object[] resultado : resultados) {
            String categoria = (String) resultado[0];
            Long quantidade = (Long) resultado[1];
            generos.put(categoria, quantidade);
        }

        return generos;
    }
}
