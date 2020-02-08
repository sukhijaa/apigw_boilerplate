package hyperdew.authservice.securityConfig;

import hyperdew.authservice.exception.AuthenticationFailed;
import hyperdew.authservice.userRegistry.UserModel;
import hyperdew.authservice.userRegistry.UserRepository;
import hyperdew.authservice.utils.RequestContextUtils;
import hyperdew.authservice.utils.SpringContextProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BasicAccessTokenAuthentication extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(BasicAccessTokenAuthentication.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final JWTTokenUtil jwtTokenUtil = SpringContextProvider.getBean(JWTTokenUtil.class);
        final UserRepository userRepository = SpringContextProvider.getBean(UserRepository.class);

        String jwtToken = Optional.of(RequestContextUtils.getAccessTokenFromRequest(httpServletRequest))
                .orElseThrow(() -> new AuthenticationFailed("Failed in BasicAccessTokenAuthentication. No Access Token Found"));

        String userId = null;

        try {
            userId = Optional.ofNullable(jwtTokenUtil.getIdFromToken(jwtToken)).orElse(StringUtils.EMPTY);
        } catch (IllegalArgumentException e) {
            logger.info("Unable to get username from JWT Token. Possibly token is not signed properly");
        } catch (ExpiredJwtException e) {
            logger.info("JWT Token has expired. Please refresh the token");
        }

        if (userId != null && !userId.isEmpty()) {

            UserModel userDetails = userRepository.findById(Long.parseLong(userId));

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(userDetails.getUserRole()));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                logger.debug("Role Assigned to current user : " + userDetails.getUserRole());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
