package hyperdew.authservice.authentication;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateUserBody {

    private String userName;
    private String password;
}
