package com.stefano.parktestapi.repository;

import com.stefano.parktestapi.entity.Cliente;
import com.stefano.parktestapi.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c")
    Page<ClienteProjection> findAllPegeable(Pageable pageable);

    Cliente findByUsuarioId(Long id);
}
