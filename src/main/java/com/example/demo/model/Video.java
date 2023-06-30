package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String vLink;

    private LocalDate date = LocalDate.now();
    private boolean status = true;
    @ManyToOne
    @NotNull
    private Channel channel;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_like",
            joinColumns = @JoinColumn(name = "video_id"), inverseJoinColumns = @JoinColumn(name = "user_id")
    ,uniqueConstraints = @UniqueConstraint(columnNames = {"video_id","user_id"}))
    private List<User> likeList = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @NotNull
    @JoinTable(name = "video_cat",
            joinColumns = @JoinColumn(name = "video_id"), inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"video_id","category_id"}))
    private List<Category> categoryList = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    private List<Comment> commentList = new ArrayList<>();

    @PositiveOrZero
    private int views = 0;
    @NotNull
    @NotBlank
    private String avatar;

    public Video(String vName, String vLink, String avatar, List<Category> categoryList) {
        this.name = vName;
        this.vLink = vLink;
        this.avatar = avatar;
        this.categoryList = categoryList;
    }
}
