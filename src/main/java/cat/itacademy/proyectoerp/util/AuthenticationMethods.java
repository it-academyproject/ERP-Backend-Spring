package cat.itacademy.proyectoerp.util;

import cat.itacademy.proyectoerp.domain.User;
import org.springframework.security.core.Authentication;

public class AuthenticationMethods {

    public static Boolean tokenMatchesOrAdmin(Authentication auth, User user) {
        return auth.getName().equals(user.getUsername()) || auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"));
    }
}
