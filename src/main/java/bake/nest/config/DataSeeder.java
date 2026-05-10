package bake.nest.config;

import bake.nest.user.Role;
import bake.nest.user.User;
import bake.nest.user.UserRepository;
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

    @Override
    public void run(String... args) throws Exception {
        createAdminIfNotFound("bakenest@product.com", "Super Admin", Role.SUPER_ADMIN);
        createAdminIfNotFound("productadmin@bakenest.com", "Product Admin", Role.PRODUCT_ADMIN);
        createAdminIfNotFound("orderadmin@bakenest.com", "Order Admin", Role.ORDER_ADMIN);
        createAdminIfNotFound("useradmin@bakenest.com", "User Admin", Role.USER_ADMIN);
    }

    private void createAdminIfNotFound(String email, String name, Role role) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User admin = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode("admin123"))
                    .role(role)
                    .phone("0000000000")
                    .address("BakeNest Headquarters")
                    .build();
            userRepository.save(admin);
            System.out.println("Seeded " + role.name() + " account: " + email);
        }
    }
}
