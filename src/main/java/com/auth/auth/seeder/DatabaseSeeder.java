package com.auth.auth.seeder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.auth.auth.entity.User;
import com.auth.auth.repository.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bcrypt;

    public DatabaseSeeder(UserRepository userRepository, BCryptPasswordEncoder bcrypt){
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            User user_one = User.builder()
            .username("pepita")
            .password(bcrypt.encode("123456"))
            .name("Pepita")
            .lastname("De los Palotes")
            .role("USER")
            .build();

            User user_two = User.builder()
            .username("doe")
            .password(bcrypt.encode("654321"))
            .name("John")
            .lastname("Doe")
            .role("USER")
            .build();

            User admin = User.builder()
            .username("admin")
            .password(bcrypt.encode("admin"))
            .name("Admin")
            .lastname("Admin")
            .role("ADMIN")
            .build();

            userRepository.saveAll(List.of(user_one, user_two, admin));
        }
    }

}
