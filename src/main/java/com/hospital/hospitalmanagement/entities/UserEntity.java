package com.hospital.hospitalmanagement.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
//    @Column(name = "available_from")
//    private LocalTime availableFrom;
//    @Column(name = "available_to")
//    private LocalTime availableTo;

    private String nid;
    private String phoneNumber;

    @OneToOne()
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity department;

    @OneToOne()
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    private RoleEntity role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor",targetEntity = OutpatientEntity.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OutpatientEntity> outpatient;

    private String username;

    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    @PrePersist
    public void prePersist() {
        if(active == null) //We set default value in case if the value is not set yet.
            active = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
