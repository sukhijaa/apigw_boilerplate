package hyperdew.authservice.userRegistry;

import hyperdew.authservice.appRegistry.ApplicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findById(String id);

    UserModel findByUserName(String userName);

    UserModel findByApplicationModelAndUserName(ApplicationModel applicationModel, String userName);
}
