package br.com.toplibrary;

import br.com.toplibrary.domain.model.user.User;
import br.com.toplibrary.domain.model.user.UserRole;
import br.com.toplibrary.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartApp implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsByUsername("caiopfalt")) {
            userRepository.save(new User("caio.capf@gmail.com", "caiopfalt", "Caio Pfaltzgraff", "12345", UserRole.ADMIN));
        }
    }
}
