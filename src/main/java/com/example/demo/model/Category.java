package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
    private String avatar
            = "https://firebasestorage.googleapis.com/v0/b/vuongcuti-6ce58.appspot.com/o/logometube.jpg?alt=media&token=40cddf41-2a5a-4b9c-ade7-eb54cc22e55a";
    @ManyToOne
    @JsonIgnore
    private User user;
    @Transient
    private List<Video> videoList;
}
