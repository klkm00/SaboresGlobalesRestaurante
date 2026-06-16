package com.auth.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.Model.Rol;



public interface RolRepository extends JpaRepository<Rol, Long> {
}
