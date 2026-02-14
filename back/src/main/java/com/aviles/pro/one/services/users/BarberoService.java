package com.aviles.pro.one.services.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.aviles.pro.one.models.users.Barbero;
import com.aviles.pro.one.repositories.user.BarberoRepository;
import com.aviles.pro.one.utils.service.ServiceAbstract;

@Service
public class BarberoService extends ServiceAbstract<Barbero,Long> {
    
    private final BarberoRepository barberoRepository;

    public BarberoService(BarberoRepository barberoRepository) {
        this.barberoRepository = barberoRepository;
    }

    @Override
    protected JpaRepository<Barbero, Long> getRepository() {
        return this.barberoRepository;
    }

   
}
