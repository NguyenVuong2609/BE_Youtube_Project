package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String pName;
    private boolean status = true;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "userId")
    private User user;
    @ManyToMany
    @JoinTable(name = "playlist_video",
            joinColumns = @JoinColumn(name = "p_id"), inverseJoinColumns = @JoinColumn(name = "v_id"))
    private List<Video> videoList = new ArrayList<>();
}
