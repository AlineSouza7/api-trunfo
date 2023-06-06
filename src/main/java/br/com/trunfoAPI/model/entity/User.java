package br.com.trunfoAPI.model.entity;

import br.com.trunfoAPI.model.enums.TypeUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    private String username;
    private String profileImage;
    private String password;
}
