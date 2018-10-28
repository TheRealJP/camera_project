package be.kdg.processor.user.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final long userId;

    public CustomUserDetails(String userName, String password, long userId, Collection<? extends GrantedAuthority> authorities) {
        super(userName, password, authorities);
        this.userId = userId;
    }

    public long getUserId() {
        return this.userId;
    }
}
