package com.aviles.pro.one.services.servicios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.aviles.pro.one.models.servicios.Servicio;
import com.aviles.pro.one.repositories.servicios.ServicioRepository;
import com.aviles.pro.one.utils.service.ServiceAbstract;

@Service
public class ServiciosService extends ServiceAbstract<Servicio, Long> {
    private final ServicioRepository serviciosRepository;

    public ServiciosService(ServicioRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    @Override
    protected JpaRepository<Servicio, Long> getRepository() {
        return this.serviciosRepository;
    }
    
    
}
