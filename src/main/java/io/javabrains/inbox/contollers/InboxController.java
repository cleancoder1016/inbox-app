package io.javabrains.inbox.contollers;

import java.util.List;
import io.javabrains.inbox.folders.Folder;
import io.javabrains.inbox.folders.FolderService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import io.javabrains.inbox.folders.FolderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class InboxController {

    @Autowired private FolderRepository folderRepository;
    @Autowired private FolderService folderService;

    @GetMapping(value="/")
    public String homePage(@AuthenticationPrincipal OAuth2User principal, Model model) {

        
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        }

        String userId = principal.getAttribute("login");
        List<Folder> userFolders = folderRepository.findAllById(userId);
        List<Folder> defaultFolders = folderService.fetchDefaultFoders(userId);
        model.addAttribute("userFolders", userFolders);
        model.addAttribute("defaultFolders", defaultFolders);


        return "inbox-page";

    }
}
