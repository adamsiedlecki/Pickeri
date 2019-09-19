package pl.adamsiedlecki.Pickeri.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class UserRole implements GrantedAuthority {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String role;

    public UserRole(String role) {
        this.role = role;
    }

    public UserRole() {
    }

    @Override
    public String getAuthority() {
        return role;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

