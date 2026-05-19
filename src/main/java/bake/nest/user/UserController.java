package bake.nest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        // Fetch fresh data from DB
        return userRepository.findById(user.getId())
                .map(u -> {
                    u.setPassword(null); // Don't return password
                    return ResponseEntity.ok(u);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal User user, @RequestBody UserUpdateRequest request) {
        if (user == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        return userRepository.findById(user.getId())
                .map(existingUser -> {
                    if (request.getName() != null && !request.getName().isEmpty()) {
                        existingUser.setName(request.getName());
                    }
                    if (request.getPhone() != null) {
                        existingUser.setPhone(request.getPhone());
                    }
                    if (request.getAddress() != null) {
                        existingUser.setAddress(request.getAddress());
                    }
                    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    }
                    
                    userRepository.save(existingUser);
                    return ResponseEntity.ok("Profile updated successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/verify-password")
    public ResponseEntity<?> verifyPassword(@AuthenticationPrincipal User user, @RequestBody PasswordVerifyRequest request) {
        if (user == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        return userRepository.findById(user.getId())
                .map(existingUser -> {
                    if (passwordEncoder.matches(request.getCurrentPassword(), existingUser.getPassword())) {
                        return ResponseEntity.ok("Password verified");
                    } else {
                        return ResponseEntity.status(400).body("Incorrect current password");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Admin Endpoints ---

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('USER_ADMIN')")
    public ResponseEntity<List<UserAdminResponseDto>> getAllUsers() {
        List<UserAdminResponseDto> users = userRepository.findAll().stream()
                .map(this::mapToAdminDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/admin/users/{id}/role")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('USER_ADMIN')")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody RoleUpdateRequest request) {
        RoleEntity role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        
        return userRepository.findById(id)
                .map(user -> {
                    user.setRole(role);
                    userRepository.save(user);
                    return ResponseEntity.ok("User role updated successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('USER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('USER_ADMIN')")
    public ResponseEntity<?> updateUserDetails(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        java.util.Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty() && !user.getEmail().equals(request.getEmail().trim())) {
            if (userRepository.existsByEmail(request.getEmail().trim())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            user.setEmail(request.getEmail().trim());
        }
        
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        }
        
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/users")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('USER_ADMIN')")
    public ResponseEntity<?> createStaff(@RequestBody UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setAddress(request.getAddress());
        RoleEntity role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(role);
        newUser.setCreatedAt(java.time.LocalDateTime.now());

        userRepository.save(newUser);
        return ResponseEntity.ok("Staff member created successfully");
    }

    private UserAdminResponseDto mapToAdminDto(User user) {
        return UserAdminResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
