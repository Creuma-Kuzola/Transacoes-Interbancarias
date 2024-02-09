/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ucan.edu.utils.enums.UserRole;

/**
 *
 * @author jussyleitecode
 */
@Entity
@Table(catalog = "kuzola_bank", schema = "public")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Conta implements UserDetails
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_conta", nullable = false)
    private Integer pkConta;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    @ColumnDefault("")
    private String login;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String password;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente")
    @ManyToOne
    private Cliente fkCliente;

    @JoinColumn(name = "fk_conta_tipo", referencedColumnName = "pk_conta_tipo")
    @ManyToOne
    private ContaTipo fkContaTipo;

    @JoinColumn(name = "role", referencedColumnName = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Conta(String username, String password, UserRole role)
    {
        this.login = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        if (this.role == UserRole.ADMIN)
        {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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

    public ContaTipo getFkContaTipo() {
        return fkContaTipo;
    }

    public void setFkContaTipo(ContaTipo fkContaTipo) {
        this.fkContaTipo = fkContaTipo;
    }
}
