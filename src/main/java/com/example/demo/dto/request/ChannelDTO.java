package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO {
    private Long id;
    @NotBlank
    @NotNull
    private String chName;
    @NotBlank
    @NotNull
    private String avatar;
}
