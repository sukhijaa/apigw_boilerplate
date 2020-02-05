package hyperdew.apigw.authorization;

import hyperdew.apigw.utilities.ConfigConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String userName;
    private String appSecret;
    private Date tokenExpiry;

    @TimeToLive
    public long getTTL() {
        long[] vals = {tokenExpiry.getTime() - new Date().getTime(), ConfigConstants.REDIS_DEFAULT_EXPIRY};
        return NumberUtils.max(vals);
    }

}
