package hyperdew.authservice.userRegistry;

import hyperdew.authservice.appRegistry.ApplicationModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue
    private long id;

    private String userName;

    private String password;

    private String displayName;

    private String userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationModel applicationModel;
}
