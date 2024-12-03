package com.projetojavapoo.zwjuice.controller;

import com.projetojavapoo.zwjuice.entidades.Usuario;
import com.projetojavapoo.zwjuice.repositorio.UsuarioRepositorio;
import com.projetojavapoo.zwjuice.servico.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@Controller
@RequestMapping("/cadastroUsuario")
public class UsuarioController {

    @Autowired
    private UsuarioServico usuarioServico;


    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        return "cadastroUsuario/index";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute Usuario usuario,
                                   @RequestParam("confirm_senha") String confirmSenha,
                                   Model model) {
        if (!usuario.getSenha().equals(confirmSenha)) {
            model.addAttribute("erro", "As senhas não correspondem.");
            return "cadastroUsuario/index"; // Redireciona para o formulário em caso de erro
        }
        try {
            usuarioServico.salvarUsuario(usuario);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastroUsuario/index";
        }
        return "redirect:/login"; // Redireciona para a página de login após o cadastro
    }


    @GetMapping("/login")
    public String mostrarPaginaDeLogin(Model modelo) {
        modelo.addAttribute("username", new Usuario());
        return "login/login";
    }

    /*
    @PostMapping("/login")
    public String autenticarUsuario(@RequestParam("username") String username,
                                    @RequestParam("senha") String senha,
                                    Model modelo){
        Usuario usuario = usuarioServico.encontrarPorUsername(username);

        if (usuario != null && usuarioServico.verificarCredenciais(username, senha)) {
            return "redirect:/homepage"; // Redireciona para a homepage
        }

        modelo.addAttribute("erro", "Usuário ou senha inválido!");
        return "login/login"; // Corrigir para a rota correta da página de login
    }
    */

    @PostMapping("/api/usuarios")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioServico.encontrarPorUsername(usuario.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }
        usuarioServico.salvarUser(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    public UsuarioController(UsuarioServico usuarioServico) {
        this.usuarioServico = usuarioServico;
    }

    @GetMapping("/generos")
    public ResponseEntity<Map<String, Long>> getGeneroEstatisticas() {
        Map<String, Long> generos = usuarioServico.obterEstatisticasDeGenero();
        return ResponseEntity.ok(generos);
    }
}
