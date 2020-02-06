package hyperdew.authservice.userRegistry;

import hyperdew.authservice.appRegistry.ApplicationModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue
    private String id;

    private String userName;

    private String password;

    private Date sessionExpiry;

    private boolean sessionOpen;

    private String displayName;

    @ManyToOne(fetch = FetchType.EAGER)
    private ApplicationModel applicationModel;
}
