package hyperdew.authservice.securityConfig;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityImpl(String s) {
        this.authority = s;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
