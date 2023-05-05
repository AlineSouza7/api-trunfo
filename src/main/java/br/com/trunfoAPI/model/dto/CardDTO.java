package br.com.trunfoAPI.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private String linkPicture;
    private String name;
    private Double size;
    private Double weight;
    private Double lifetime;
    private Integer velocity;
}
