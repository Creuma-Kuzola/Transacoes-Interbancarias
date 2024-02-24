/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.example.IntermediarioService.utils.pojos.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 *
 * @author creuma
 */
@Table()
@Entity(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 2147483647)
    private String login;
    @Column(length = 2147483647)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente")
    @ManyToOne
    private Cliente fkCliente;
    @JoinColumn(name = "fk_funcionario", referencedColumnName = "pk_funcionario")
    @ManyToOne
    private Funcionario fkFuncionario;

    public User(Integer id) {
        this.id = id;
    }

    public User(String login, String password, UserRole role, Cliente fkCliente)
    {
        this.login = login;
        this.password = password;
        this.role = role;
        this.fkCliente = fkCliente;
    }

    public User(String login, String password, UserRole role, Funcionario fkFuncionario) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.fkFuncionario = fkFuncionario;
        this.fkCliente = fkCliente;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public Funcionario getFkFuncionario() {
        return fkFuncionario;
    }

    public void setFkFuncionario(Funcionario fkFuncionario) {
        this.fkFuncionario = fkFuncionario;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
        {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}