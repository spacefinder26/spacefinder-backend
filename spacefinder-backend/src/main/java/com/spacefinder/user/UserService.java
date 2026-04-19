package com.spacefinder.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Create User
    public UserResponse addUser(UserRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered" + request.getEmail());
        }

        User user = mapToEntity(request);
        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    // Get All Users
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get User by ID
    public UserResponse getUserById(Long id){
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return mapToResponse(user);
    }

    // Update User
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setStatus(request.getStatus());

        // Only hash and update password if a new one is provided
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Only update role if provided
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole()));
        }

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    // Delete User
    public void deleteUser(Long id) {
        userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        userRepository.deleteById(id);
    }

    // Promote User to Agent
    public UserResponse makeAgent(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setRole(Role.AGENT);
        return mapToResponse(userRepository.save(user));
    }

    // Deactivate User
    public UserResponse deactivateUser(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setStatus("Inactive");
        return mapToResponse(userRepository.save(user));
    }


    // MAPPER — Entity → Response
    public UserResponse mapToResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setSurname(user.getSurname());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole() != null ? user.getRole().name() : null);
        response.setStatus(user.getStatus());
        return response;
    }

    // MAPPER — Request → Entity
    public User mapToEntity(UserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.USER);
        user.setStatus(request.getStatus() != null ? request.getStatus() : "Active");
        return user;
    }
}
