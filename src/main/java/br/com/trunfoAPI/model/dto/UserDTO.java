package br.com.trunfoAPI.model.dto;

import br.com.trunfoAPI.model.enums.TypeUser;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private TypeUser typeUser;
    private String username;
    private String linkProfile;
    private String password;
}
