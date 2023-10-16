package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.user.User;
import br.com.toplibrary.domain.model.user.UserDtoRead;
import br.com.toplibrary.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<UserDtoRead> findAll() {
        return userRepository.findAll().stream().map(UserDtoRead::new).toList();
    }
    @Transactional(readOnly = true)
    public UserDtoRead findByUsername(String username) {
        return userRepository.findByUsername(username).toDto();
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
}
