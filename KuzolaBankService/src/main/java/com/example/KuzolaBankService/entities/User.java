/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import com.example.KuzolaBankService.enums.UserRole;
import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;

@Table()
@Entity(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente", nullable = true)
    @ManyToOne(optional = true)
    private Cliente fkCliente;

    @JoinColumn(name = "fk_funcionario", referencedColumnName = "pk_funcionario", nullable = true)
    @ManyToOne(optional = true)
    private Funcionario fkFuncionario;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
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
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        if (this.role == UserRole.ADMIN)
        {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getUsername()
    {
        return login;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", fkCliente=" + "" +
                '}';
    }
}
