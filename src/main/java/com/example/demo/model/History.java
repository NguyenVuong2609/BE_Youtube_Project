package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "history",
       uniqueConstraints = @UniqueConstraint(columnNames = "owner_id"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @NotNull
    private User owner;
    @ManyToMany
    @JoinTable(name = "history_video",
            joinColumns = @JoinColumn(name = "h_id"), inverseJoinColumns = @JoinColumn(name = "v_id"))
    private List<Video> videoList = new ArrayList<>();
}
