package fr.lengrand.dialogflowfunapi;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class DialogflowFunApiApplication {

	@Autowired
	private Auth auth;

	public static void main(String[] args) {
		SpringApplication.run(DialogflowFunApiApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "hello world!";
	}

	@GetMapping("/auth")
	public String auth() throws IOException, InterruptedException {
		auth.authenticate(); // TODO: Should be done on start!
		return auth.getAuthToken().isPresent()? auth.getAuthToken().get().getToken() : "No token!";
	}

}
