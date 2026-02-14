package com.aviles.pro.one.services.citas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.aviles.pro.one.models.citas.Cita;
import com.aviles.pro.one.repositories.citas.CitaRepository;
import com.aviles.pro.one.utils.service.ServiceAbstract;

@Service
public class CitasService extends ServiceAbstract<Cita, Long> {

    private final CitaRepository citasRepository;

    public CitasService(CitaRepository citasRepository) {
        this.citasRepository = citasRepository;
    }

    @Override
    protected JpaRepository<Cita, Long> getRepository() {
        return this.citasRepository;
    }

    

}
