package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByRole(RoleEntity role);

    UserEntity findByIdAndRole(Long id, RoleEntity role);

    UserEntity findByEmailAndRole(String email, RoleEntity role);

    UserEntity findByNameContains(String name);

    List<UserEntity> findByNameContainsAndRole(String name, RoleEntity role);

    Page<UserEntity> findAllByRole(RoleEntity role, Pageable pageable);

    List<UserEntity> findAllByDepartment(DepartmentEntity department);
}
