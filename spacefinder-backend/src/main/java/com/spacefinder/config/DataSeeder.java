package com.spacefinder.config;

import com.spacefinder.user.Role;
import com.spacefinder.user.User;
import com.spacefinder.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder  implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){
        // Only create admin if none exists
        if (userRepository.findByEmail("admin@spacefinder.com").isEmpty()) {
            User admin = new User();
            admin.setName("System");
            admin.setSurname("Admin");
            admin.setEmail("admin@spacefinder.com");
            admin.setPhone("0000000000");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(Role.ADMIN);
            admin.setStatus("Active");
            userRepository.save(admin);
            System.out.println("✅ Default admin created: admin@spacefinder.com");
        }
    }
}
