package com.openclassrooms.mddapi.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.openclassrooms.mddapi.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * This method is used to filter internal requests. It extracts the JWT from the
     * request, validates the JWT,
     * loads the user details from the username extracted from the JWT, creates an
     * authentication object,
     * and sets it in the security context.
     *
     * @param request     This is the HttpServletRequest object that contains the
     *                    request details.
     * @param response    This is the HttpServletResponse object that is used to
     *                    send the response.
     * @param filterChain This is the FilterChain that is used to invoke the next
     *                    filter in the chain.
     * @throws ServletException This exception is thrown if the request for the POST
     *                          could not be handled.
     * @throws IOException      This exception is thrown if an input or output error
     *                          is detected when the servlet handles this request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * This method is used to parse the JWT from the HttpServletRequest.
     * It retrieves the "Authorization" header from the request, checks if it has
     * text and starts with "Bearer ",
     * and if so, it extracts the JWT from the header.
     *
     * @param request This is the HttpServletRequest object that contains the
     *                request details.
     * @return String This returns the JWT if it exists and is valid, or null
     *         otherwise.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
