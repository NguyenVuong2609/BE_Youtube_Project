package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "channel", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "chName"
        })})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status = true;
    @NotBlank
    @Size(min = 3, max = 50)
    private String chName;
    @NotNull
    @NotBlank
    private String avatar;
    @NotNull
    @ManyToOne
    private User user;
    @Transient
    @JsonIgnore
    private List<Video> videoList;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT) //! Force all attributes to fetch type we choose
    @JoinTable(name = "follower",
               joinColumns = @JoinColumn(name = "channel_id"), inverseJoinColumns = @JoinColumn(name = "user_id"),
               uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "user_id"}))
    private List<User> followerList = new ArrayList<>();

    public Channel(String chName, String avatar) {
        this.chName = chName;
        this.avatar = avatar;
    }
}
