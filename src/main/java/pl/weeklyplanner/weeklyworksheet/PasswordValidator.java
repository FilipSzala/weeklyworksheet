package pl.weeklyplanner.weeklyworksheet;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator {
    public boolean isPasswordValid(String password){
        if (password.length()<8){
            return false;
        }
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
        }

        if (!hasLowerCase || !hasUpperCase) {
            return false;
        }


        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        if (!hasDigit) {
            return false;
        }


        if (!password.matches("[a-zA-Z0-9]+")) {
            return false;
        }
        return true;
}
}