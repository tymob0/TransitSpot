package nl.fontys.TransitSpot.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.fontys.TransitSpot.Collections.UserCollection;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.DTO.UserRoleDTO.UserRoleDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserCollection users;
    @Autowired
    public UserController(UserCollection users){this.users = users;}
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok().body(users.getUsers());
    }
    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String email){return ResponseEntity.ok().body(users.getUser(email));}
    @GetMapping("/check")
    public ResponseEntity<?> getCheck(){

        return ResponseEntity.ok().body("");
    }
    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO user){
        user.setID(UUID.randomUUID());
        UserDTO response = users.saveUser(user);
        users.addRoleToUser(user.getEmail(), "ROLE_USER");
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/changePassword/{pass_old}/{pass_new}")
    public ResponseEntity<?> changePassword(@PathVariable @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$") String pass_old,
                                            @PathVariable @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$") String pass_new){
        UserDTO userDTO = users.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        UUID ID = userDTO.getID();
        if(users.UpdatePassword(ID, pass_old, pass_new)==true){
            return ResponseEntity.ok().body(true);
        }
        else return ResponseEntity.badRequest().body(false);

    }
    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDTO user = users.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch(Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        }
        else{
            throw new RuntimeException("Refresh token is missing");
        }

    }




}
