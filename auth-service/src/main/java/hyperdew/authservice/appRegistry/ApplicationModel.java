package hyperdew.authservice.appRegistry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
// TODO : Rename to Account
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ApplicationModel {

    @Id
    @GeneratedValue
    private long id;

    private String applicationName;

    private String applicationDisplayName;

}
