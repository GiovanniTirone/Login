package com.example.login2.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login2.auth.services.LoginService;
import com.example.login2.users.entities.User;
import com.example.login2.users.repos.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    private Collection<? extends GrantedAuthority> getAuthorities (User user)
    {
        if(user == null || !user.isActive()) return List.of();
        return user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
                .collect(Collectors.toList());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {

        //validate authorization header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //validate Jwt token
        final String token;
        try{
            token = header.split(" ")[1].trim();
        } catch (Exception e){
            filterChain.doFilter(request,response);
            return;
        }

        DecodedJWT decodedJWT;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(LoginService.SECRET)).build();
            decodedJWT = jwtVerifier.verify(token);
        }catch (Exception e){
            filterChain.doFilter(request,response);
            return;
        }

        //Get user identity and set it on spring security context
        Optional<User> userOpt = userRepository.findById(decodedJWT.getClaim("id").asLong());
        if(userOpt.isEmpty() || !userOpt.get().isActive()){
            filterChain.doFilter(request,response);
            return;
        }

        User user = userOpt.get();
        user.setPassword(null);
        user.setActivationCode(null);
        user.setPwdResetCode(null);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user,null, getAuthorities(user)
                );

        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }


}
