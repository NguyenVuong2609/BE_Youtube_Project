package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @JsonIgnore
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
    private boolean status = true;
    @Lob
    private String avatar = "https://firebasestorage.googleapis.com/v0/b/vuongcuti-6ce58.appspot.com/o/images.png?alt=media&token=568b3bcc-c852-4669-9cd3-d4684ce4ad70&_gl=1*1jze9dn*_ga*NTk4MDQwMzA2LjE2ODM3NjczNjA.*_ga_CW55HF8NVT*MTY4NjYzMTIzOS4xNC4xLjE2ODY2MzEzMjMuMC4wLjA.";
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    public User(@NotBlank @Size(min = 3, max = 50) String name,
                @NotBlank @Size(min = 3, max = 50) String username,
                @NotBlank @Size(max = 50) @Email String email,
                @NotBlank @Size(min = 6, max = 100) String encode) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = encode;
    }

    public User(Long id,
                @NotBlank @Size(min = 3, max = 50) String name,
                @NotBlank @Size(min = 3, max = 50) String username,
                @NotBlank @Size(max = 50) @Email String email,
                @NotBlank @Size(min = 6, max = 100) String encode,
                Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = encode;
        this.roles = roles;
    }
}
