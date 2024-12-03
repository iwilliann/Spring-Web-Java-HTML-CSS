package com.projetojavapoo.zwjuice.controller;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/homepage")
    public String homepage() {
        return "homepage/index";
    }

    @GetMapping("/grafico/genero")
    public String exibirGraficoGenero() {
        return "grafico/genero";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login"; // Caminho correto para o arquivo login.html
    }
}

