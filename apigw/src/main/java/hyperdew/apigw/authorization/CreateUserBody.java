package hyperdew.apigw.authorization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserBody {
    private String userName;
    private String password;
}
