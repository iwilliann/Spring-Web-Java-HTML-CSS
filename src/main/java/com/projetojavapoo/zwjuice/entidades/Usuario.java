package com.projetojavapoo.zwjuice.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter mais de 3 caracteres")
    @NotBlank(message = "O campo Usuário não pode estar vazio")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotBlank(message = "O campo Senha não pode estar vazio")
    @Column(name = "senha", nullable = false)
    private String senha;



    @NotBlank(message = "A permissão do usuário é obrigatória")
    @Column(name = "permissao_usuario", nullable = false)
    private String permissaoUsuario;


    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotBlank(message = "O gênero é obrigatório")
    @Size(max = 255, message = "O gênero não pode exceder 255 caracteres")
    @Column(name = "genero", nullable = false, length = 255 )
    private String genero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getPermissaoUsuario() {
        return permissaoUsuario;
    }

    public void setPermissaoUsuario(String permissaoUsuario) {
        this.permissaoUsuario = permissaoUsuario;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + permissaoUsuario);
    }


    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
