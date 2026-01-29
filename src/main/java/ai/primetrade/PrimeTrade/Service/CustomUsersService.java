package ai.primetrade.PrimeTrade.Service;

import ai.primetrade.PrimeTrade.Entity.Users;
import ai.primetrade.PrimeTrade.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUsersService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> optionalUser = usersRepo.findByEmail(email);

        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found: " + email);
        }

        Users users = optionalUser.get();


        return new User(
                users.getEmail(),
                users.getPassword(),
                users.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        .collect(Collectors.toList())
        );
    }
}
