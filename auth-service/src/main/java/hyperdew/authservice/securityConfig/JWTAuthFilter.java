package hyperdew.authservice.securityConfig;

import hyperdew.authservice.appRegistry.ApplicationModel;
import hyperdew.authservice.appRegistry.ApplicationRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader("app-secret");

        String appId = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                appId = jwtTokenUtil.getIdFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.info("Unable to get username from JWT Token. Possibly token is not signed properly");
            } catch (ExpiredJwtException e) {
                logger.info("JWT Token has expired. Please refresh the token");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }


        if (appId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            ApplicationModel appDetails = applicationRepository.findById(appId);


            if (jwtTokenUtil.validateToken(jwtToken, appDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        appDetails, null);

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
