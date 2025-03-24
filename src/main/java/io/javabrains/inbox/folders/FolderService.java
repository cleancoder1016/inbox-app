package io.javabrains.inbox.folders;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FolderService {

    public List<Folder> fetchDefaultFoders(String userId) {
        return Arrays.asList(
                new Folder(userId, "Inbox", "orange"),
                new Folder(userId, "Sent", "blue"),
                new Folder(userId, "Important", "green")
        );
    }
}
