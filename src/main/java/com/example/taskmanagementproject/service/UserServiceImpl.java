package com.example.taskmanagementproject.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskmanagementproject.DTO.OrganizationDTO;
import com.example.taskmanagementproject.DTO.UserDTO;
import com.example.taskmanagementproject.domain.Organization;
import com.example.taskmanagementproject.domain.Role;
import com.example.taskmanagementproject.domain.User;
import com.example.taskmanagementproject.mapper.UserDTOMapper;
import com.example.taskmanagementproject.mapper.UserMapper;
import com.example.taskmanagementproject.payload.CreateOrganizationPayload;
import com.example.taskmanagementproject.payload.CreateUserPayload;
import com.example.taskmanagementproject.repo.OrganizationRepo;
import com.example.taskmanagementproject.repo.RoleRepo;
import com.example.taskmanagementproject.repo.UserRepo;
import com.example.taskmanagementproject.util.EmailValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationRepo organizationRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

    }

    @Override
    public OrganizationDTO signup(CreateOrganizationPayload payload) throws RuntimeException {

        Collection<Role> roles = new ArrayList<>();
        Collection<User> users = new ArrayList<>();

        Role adminRole = roleRepo.findByName("ROLE_ADMIN");
        roles.add(adminRole);

        if (userRepo.countByUsername(payload.getUsername()) == 0) {
            User user = UserMapper.mapFromCreateOrganizationPayload(payload);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(payload.getPassword()));
            userRepo.save(user);
            users.add(user);

            Organization organization = new Organization();

            organization.setOrganizationName(payload.getOrganizationName());
            organization.setPhone(payload.getPhone());

            if (organizationRepo.countByEmail(payload.getEmail()) == 0) {
                organization.setEmail(payload.getEmail());
            } else {
                throw new IllegalArgumentException("This email was already taken");
            }

            organization.setAddress(payload.getAddress());
            organization.setUsers(users);

            organizationRepo.save(organization);

            return UserDTOMapper.mapToOrganizationDto(user, organization);
        } else {
            log.error("This username was already taken");
            throw new IllegalArgumentException("This username was already taken");
        }


    }

    @Override
    public UserDTO saveUser(CreateUserPayload payload, String bearerToken) throws IllegalArgumentException {
        log.info(" Saving new user {} to the database", payload.getName());

        String token = bearerToken.substring("Bearer ".length());

        Algorithm algorithm = Algorithm.HMAC256("Secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        String username = decodedJWT.getSubject();

        if (Arrays.stream(roles).anyMatch("ROLE_ADMIN"::equals)) {
            if (EmailValidation.patternMatches(payload.getEmail())) {
                User user = new User();

                if (userRepo.countByEmail(payload.getEmail()) == 0) {
                    user.setEmail(payload.getEmail());
                } else {
                    throw new IllegalArgumentException("This email was already taken");
                }
                user.setName(payload.getName());
                user.setSurname(payload.getSurname());
                if (userRepo.countByUsername(payload.getUsername()) == 0) {
                    user.setUsername(payload.getUsername());
                } else {
                    throw new IllegalArgumentException("This username was already taken");
                }

                user.setPassword(passwordEncoder.encode(payload.getPassword()));

                Collection<Role> roles1 = new ArrayList<>();
                Role roleUser = roleRepo.findByName("ROLE_USER");
                roles1.add(roleUser);
                user.setRoles(roles1);

                userRepo.save(user);

                User organization = userRepo.findByUsername(username); // User(admin) who wants to create a user
                Organization org = organizationRepo.findByEmail(organization.getEmail()); // Organization that wants to create user
                org.getUsers().add(user);

                return UserDTOMapper.mapToUserDto(user);
            } else {
                throw new IllegalArgumentException("please enter the valid email address");
            }


        } else {
            log.error("Only ADMINS can create a user");
            throw new RuntimeException("Only ADMINS can create a user");
        }

    }


    @Override
    public List<UserDTO> getUsers() {
        log.info("Fetching all users");
        List<User> all = userRepo.findAll();
        return all.stream().map(user -> {
            return UserDTOMapper.mapToUserDto(user);
        }).collect(Collectors.toList());
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("Secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userRepo.findByUsername(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
