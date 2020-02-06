package hyperdew.authservice.appRegistry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationModel, Long> {

    ApplicationModel findById(String id);

    ApplicationModel findByApplicationName(String appName);

}
