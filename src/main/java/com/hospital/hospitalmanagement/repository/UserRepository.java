package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByRole(RoleEntity role);

    UserEntity findByIdAndRole(Long id, RoleEntity role);

    UserEntity findByEmailAndRole(String email, RoleEntity role);

    UserEntity findByName(String name);

    UserEntity findByNameAndRole(String name, RoleEntity role);

}
