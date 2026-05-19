package bake.nest.config;

import bake.nest.user.*;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
        repairUserRoles();
        // Force update roles for standard admin emails to ensure they aren't stuck with 'USER' role
        ensureAdminRole("bakenest@product.com", "Super Admin", "SUPER_ADMIN");
        ensureAdminRole("productadmin@bakenest.com", "Product Admin", "PRODUCT_ADMIN");
        ensureAdminRole("orderadmin@bakenest.com", "Order Admin", "ORDER_ADMIN");
        ensureAdminRole("useradmin@bakenest.com", "User Admin", "USER_ADMIN");
    }

    private void ensureAdminRole(String email, String name, String roleName) {
        userRepository.findByEmail(email).ifPresentOrElse(
            user -> {
                RoleEntity role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
                if (user.getRole() == null || !user.getRole().getName().equals(roleName)) {
                    user.setRole(role);
                    userRepository.save(user);
                    System.out.println("Ensured " + roleName + " role for: " + email);
                }
            },
            () -> createAdminIfNotFound(email, name, roleName)
        );
    }

    private void repairUserRoles() {
        RoleEntity defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));
        
        userRepository.findAll().stream()
                .filter(u -> u.getRole() == null)
                .forEach(u -> {
                    u.setRole(defaultRole);
                    userRepository.save(u);
                    System.out.println("Repaired role for user: " + u.getEmail());
                });
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new RoleEntity(null, "USER", "Standard access for purchasing products.", LocalDateTime.now()));
            roleRepository.save(new RoleEntity(null, "PRODUCT_ADMIN", "Manage catalog, categories, and stock.", LocalDateTime.now()));
            roleRepository.save(new RoleEntity(null, "ORDER_ADMIN", "Manage customer orders and shipments.", LocalDateTime.now()));
            roleRepository.save(new RoleEntity(null, "USER_ADMIN", "Manage customer accounts (excluding staff).", LocalDateTime.now()));
            roleRepository.save(new RoleEntity(null, "SUPER_ADMIN", "Full system access and staff management.", LocalDateTime.now()));
            System.out.println("Seeded system roles.");
        }
    }

    private void createAdminIfNotFound(String email, String name, String roleName) {
        if (userRepository.findByEmail(email).isEmpty()) {
            RoleEntity role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
            
            User admin = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode("admin123"))
                    .role(role)
                    .phone("0000000000")
                    .address("BakeNest Headquarters")
                    .build();
            userRepository.save(admin);
            System.out.println("Seeded " + roleName + " account: " + email);
        }
    }
}
