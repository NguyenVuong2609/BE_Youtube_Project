package com.example.demo.dto.request;

import com.example.demo.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    @NotBlank
    @NotNull
    private String vName;
    @NotBlank
    @NotNull
    private String vLink;
    private boolean status = true;
    @NotNull
    @NotBlank
    private String avatar;
    @NotNull
    private List<Category> categoryList = new ArrayList<>();
}