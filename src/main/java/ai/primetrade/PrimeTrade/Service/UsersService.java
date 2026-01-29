package ai.primetrade.PrimeTrade.Service;

import ai.primetrade.PrimeTrade.Entity.Users;
import ai.primetrade.PrimeTrade.Repository.UsersRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveUsers(Users users) {

        Optional<Users> existingUser = usersRepository.findByEmail(users.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.getRoles().add("USER");

        usersRepository.save(users);
    }


    public Users getUserByEmail(String email) {

        Optional<Users> optionalUser = usersRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }


    public void deleteUserById(ObjectId id) {
        usersRepository.deleteById(id);
    }

    public void deleteByEmail(String email) {
        usersRepository.deleteByEmail(email);
    }


    public void addAdmin(Users admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.getRoles().add("ADMIN");
        usersRepository.save(admin);
    }


    public void updateProfile(String currentEmail,
                              String fullName,
                              String newEmail,
                              String password) {

        Optional<Users> optionalUser = usersRepository.findByEmail(currentEmail);

        if (optionalUser.isPresent()) {

            Users user = optionalUser.get();

            user.setName(fullName);
            user.setEmail(newEmail);

            if (password != null && !password.isBlank()) {
                user.setPassword(passwordEncoder.encode(password));
            }

            usersRepository.save(user);

        } else {
            throw new RuntimeException("User not found");
        }
    }
}
