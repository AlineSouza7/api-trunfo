package br.com.trunfoAPI.model.dto;

import br.com.trunfoAPI.model.enums.TypeUser;
import lombok.*;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private TypeUser typeUser;
    private String username;
    private File linkProfile;
    private String password;
}
