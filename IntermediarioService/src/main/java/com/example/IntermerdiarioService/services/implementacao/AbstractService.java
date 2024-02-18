/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermerdiarioService.services.implementacao;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;


/**
 *
 * @author ivandro.sousa
 */
@Service
public abstract class AbstractService<E, K> 
{
    @Autowired
    protected JpaRepository<E, K> repository;

    public Repository<E, K> getRepository() {
        return repository;
    }
    
    public List<E> findAll() {
        return this.repository.findAll();
    } 
    
    public Page<E> findPagina( Pageable pageable ) {
        return this.repository.findAll( pageable );
    }
    
    public Optional<E> findById( K id ) {
        return this.repository.findById(id);
    }
    
    public E criar( E entidade ) {
        if ( entidade == null )
            throw new NullPointerException("Entidade a ser registrada está nula.");
        return this.repository.save(entidade);
    }
    
    public E editar( K id, E entidade ) {
        if ( this.findById(id).isEmpty() )
            throw new EntityNotFoundException("Não foi possível encontrar a entidade procurada.");
        return this.repository.save(entidade);
    }
    
    public E eliminar( K id ) 
    {
        Optional<E> entidade = this.findById(id);
        if ( entidade.isEmpty() )
            return entidade.orElse(null);
        this.repository.deleteById(id);
        return entidade.get();
    }
}
