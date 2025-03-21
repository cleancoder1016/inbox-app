package io.javabrains;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class InboxApp {

    /*
	private final CqlSessionBuilderCustomizer sessionBuilderCustomizer;

    private final SecurityAdapter securityAdapter;


    InboxApp(SecurityAdapter securityAdapter, CqlSessionBuilderCustomizer sessionBuilderCustomizer) {
        this.securityAdapter = securityAdapter;
        this.sessionBuilderCustomizer = sessionBuilderCustomizer;
    }
	*/

	public static void main(String[] args) {
		SpringApplication.run(InboxApp.class, args);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

	
	

}
