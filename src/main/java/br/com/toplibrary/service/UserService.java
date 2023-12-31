package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.user.User;
import br.com.toplibrary.domain.repository.UserRepository;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements CrudService<UUID, User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAllByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public User findById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new NotFoundException(User.class));
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));

        return userRepository.save(user);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User update(UUID id, User userToUpdate) {
        var user = findById(id);
        user.update(userToUpdate);
        return user;
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void delete(UUID id) {
        var user = findById(id);
        user.setAtivo(false);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
