package master.springframework.usermanagement.controllers;

import master.springframework.usermanagement.services.ImageService;
import master.springframework.usermanagement.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("user/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("user", userService.getById(id).block());
        return "user/imageUploadForm";
    }

    @PostMapping("user/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(id, file).block();

        return "redirect:/user/" + id + "/show";
    }

}
