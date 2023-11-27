package com.openclassrooms.mddapi.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * This method is triggered when an unauthenticated user tries to access a
     * protected resource.
     * It sends an HTTP response with a status code of 401 (Unauthorized) and a JSON
     * body containing the error details.
     *
     * @param request       This is the HttpServletRequest object that contains the
     *                      request details.
     * @param response      This is the HttpServletResponse object that is used to
     *                      send the response.
     * @param authException This is the AuthenticationException object that contains
     *                      the details of the authentication error.
     * @throws IOException      This exception is thrown if an input or output error
     *                          is detected when the servlet handles this request.
     * @throws ServletException This exception is thrown if the request for the POST
     *                          could not be handled.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
