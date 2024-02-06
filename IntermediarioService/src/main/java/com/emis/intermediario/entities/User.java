/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emis.intermediario.entities;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 *
 * @author jussyleitecode
 */
@Entity
@Table(catalog = "emis", schema = "public")
@NamedQueries(
{
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByPkUser", query = "SELECT u FROM User u WHERE u.pkUser = :pkUser"),
    @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE u.role = :role")
})
public class User implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pk_user", nullable = false)
    private Integer pkUser;
    @Column(length = 2147483647)
    private String login;
    @Column(length = 2147483647)
    private String password;
    @Column(length = 2147483647)
    private String role;

    public User()
    {
    }

    public User(Integer pkUser)
    {
        this.pkUser = pkUser;
    }

    public Integer getPkUser()
    {
        return pkUser;
    }

    public void setPkUser(Integer pkUser)
    {
        this.pkUser = pkUser;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (pkUser != null ? pkUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User))
        {
            return false;
        }
        User other = (User) object;
        if ((this.pkUser == null && other.pkUser != null) || (this.pkUser != null && !this.pkUser.equals(other.pkUser)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.emis.intermediario.entities.User[ pkUser=" + pkUser + " ]";
    }
    
}
