package br.com.fiap.mottu_challenge.unit.mocks;

import br.com.fiap.mottu_challenge.model.User;

public class UserFactory {

    public static User userAuthentication() {
        User user = new User();
        user.setId(1L);
        user.setPassword("123456");
        user.setEmail("test@test.com");
        user.setRole("USER");
        return user;
    }
}
