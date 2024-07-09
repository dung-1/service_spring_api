package dungcts.backendapi.com.shoplaptop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dungcts.backendapi.com.shoplaptop.entity.UserAccount;
import dungcts.backendapi.com.shoplaptop.jwt_security.JwtUtils;
import dungcts.backendapi.com.shoplaptop.payload.request.LoginRequest;
import dungcts.backendapi.com.shoplaptop.payload.response.JwtResponse;
import dungcts.backendapi.com.shoplaptop.responsitory.UserRepository;
import dungcts.backendapi.com.shoplaptop.service.UserDetailsImpl;
import dungcts.backendapi.com.shoplaptop.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserDetailsServiceImpl userService;

    public AuthController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUserId(),
                userDetails.getUsername(), userDetails.getRole()));
    }

    @GetMapping("users/{username}")
    public ResponseEntity<UserAccount> getUserByUsername(@PathVariable String username) {
        UserAccount user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
