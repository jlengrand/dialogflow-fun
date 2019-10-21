package fr.lengrand.dialogflowfunapi.openbankproject.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class OpenBankCredentials {

    @Value("${obp.username}")
    private String username;

    @Value("${obp.password}")
    private String password;

    @Value("${obp.key}")
    private String key;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getKey() {
        return key;
    }
}
