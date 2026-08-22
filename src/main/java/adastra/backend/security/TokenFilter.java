package adastra.backend.security;

import adastra.backend.entities.User;
import adastra.backend.exceptions.UnauthorizedException;
import adastra.backend.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private JwtTools jwtTools;
    private UsersService usersService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'authorization header nel formato Bearer ");


        String accessToken = authHeader.replace("Bearer ", "");
        System.out.println(accessToken);


        this.jwtTools.verifyToken(accessToken);


        UUID userId = this.jwtTools.extractIdFromToken(accessToken);
        User authUser = this.usersService.findById(userId);


        Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request, response); // Se non mettiamo questo non arriveremo mai al controller

    }

    @Override


    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/**", request.getServletPath());


    }
}
