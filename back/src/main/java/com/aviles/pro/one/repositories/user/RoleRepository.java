package com.aviles.pro.one.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aviles.pro.one.models.users.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
