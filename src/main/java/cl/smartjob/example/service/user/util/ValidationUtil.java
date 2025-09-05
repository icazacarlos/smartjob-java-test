package cl.smartjob.example.service.user.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Utility class for validation operations
 */
@Component
public class ValidationUtil {

    @Value("${validation.email.regex}")
    private String emailRegex;

    @Value("${validation.email.message}")
    private String emailMessage;

    @Value("${validation.password.regex}")
    private String passwordRegex;

    @Value("${validation.password.message}")
    private String passwordMessage;

    @Value("${validation.user.message}")
    private String userMessage;

    private Pattern emailPattern;
    private Pattern passwordPattern;

    /**
     * Initialize patterns after properties are loaded
     */
    public void initializePatterns() {
        if (emailPattern == null) {
            emailPattern = Pattern.compile(emailRegex);
        }
        if (passwordPattern == null) {
            passwordPattern = Pattern.compile(passwordRegex);
        }
    }

    /**
     * Validate email format
     * 
     * @param email email to validate
     * @return true if valid
     */
    public boolean isValidEmail(String email) {
        initializePatterns();
        return emailPattern.matcher(email).matches();
    }

    /**
     * Validate password format
     * 
     * @param password password to validate
     * @return true if valid
     */
    public boolean isValidPassword(String password) {
        initializePatterns();
        return passwordPattern.matcher(password).matches();
    }

    /**
     * Get email validation error message
     * 
     * @return error message
     */
    public String getEmailMessage() {
        return emailMessage;
    }

    /**
     * Get password validation error message
     * 
     * @return error message
     */
    public String getPasswordMessage() {
        return passwordMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
