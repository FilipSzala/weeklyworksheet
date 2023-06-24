package pl.weeklyplanner.weeklyworksheet;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class LoginSession {
    private Long userId;

    public LoginSession(Long userId) {
        this.userId = userId;
    }
}