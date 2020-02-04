package hyperdew.apigw.redis;

import hyperdew.apigw.authorization.AuthResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationCacheUtil {

    @Cacheable(key = "#redisKey", cacheNames = "authCache")
    public AuthResponse getFromCache(String redisKey) {
        return null;
    }

    @CachePut(key = "#redisKey", cacheNames = "authCache")
    public AuthResponse populateCache(String redisKey, AuthResponse authResponse) {
        return authResponse;
    }

    @CacheEvict(key = "#redisKey", cacheNames = "authCache")
    public void removeFromCache(String redisKey) {

    }
}
