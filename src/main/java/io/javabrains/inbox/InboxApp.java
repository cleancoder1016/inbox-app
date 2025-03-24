package io.javabrains.inbox;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import io.javabrains.inbox.folders.Folder;
import io.javabrains.inbox.folders.FolderRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


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

	@Autowired FolderRepository folderRepository;

	public static void main(String[] args) {
		SpringApplication.run(InboxApp.class, args);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

	@PostConstruct
	public void init() {
		folderRepository.save(new Folder("cleancoder1016","Inbox", "orange"));
		folderRepository.save(new Folder("cleancoder1016","Sent", "blue"));
		folderRepository.save(new Folder("cleancoder1016","Important", "green"));
	}

	
	

}
