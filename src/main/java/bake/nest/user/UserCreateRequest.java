package bake.nest.user;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String role;
}
