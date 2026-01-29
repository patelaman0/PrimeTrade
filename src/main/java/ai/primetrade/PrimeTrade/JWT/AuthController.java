package ai.primetrade.PrimeTrade.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signin")
    public JwtResponse signin(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email,request.password)
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();


        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new JwtResponse(token);
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class JwtResponse {
        public final String token;
        public JwtResponse(String token) {
            this.token = token;
        }
    }
}
