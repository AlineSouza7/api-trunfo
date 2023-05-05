package br.com.trunfoAPI.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String linkPicture;
    private String name;
    private Double size;
    private Double weight;
    private Double lifetime;
    private Integer velocity;
}
