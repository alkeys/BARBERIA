package com.aviles.pro.one.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.aviles.pro.one.models.users.Role;
import com.aviles.pro.one.repositories.user.RoleRepository;
import com.aviles.pro.one.utils.service.ServiceAbstract;

@Service
public class RoleService extends ServiceAbstract<Role, Long> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected JpaRepository<Role, Long> getRepository() {
        return roleRepository;
    }

}
