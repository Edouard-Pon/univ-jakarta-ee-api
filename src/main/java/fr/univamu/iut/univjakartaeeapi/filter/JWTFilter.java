package fr.univamu.iut.univjakartaeeapi.filter;

import fr.univamu.iut.univjakartaeeapi.annotation.AdminOnly;
import fr.univamu.iut.univjakartaeeapi.annotation.SubscriberOnly;
import fr.univamu.iut.univjakartaeeapi.annotation.NoJWTFilter;
import fr.univamu.iut.univjakartaeeapi.service.TokenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class JWTFilter implements ContainerRequestFilter {
    @Inject
    private TokenService tokenService;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (resourceInfo.getResourceMethod().isAnnotationPresent(NoJWTFilter.class)) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenService.validateToken(token)) {
                String role = tokenService.getRoleFromToken(token);
                if (resourceInfo.getResourceMethod().isAnnotationPresent(AdminOnly.class) && !"ADMIN".equals(role)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                } else if (resourceInfo.getResourceMethod().isAnnotationPresent(SubscriberOnly.class) && !"SUBSCRIBER".equals(role)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            } else {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
