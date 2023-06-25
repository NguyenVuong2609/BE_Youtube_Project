package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 300)
    private String content;
    @ManyToOne
    @NotNull
    private User owner;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(columnDefinition = "datetime default(curdate())")
    private LocalDate date = LocalDate.now();
}
