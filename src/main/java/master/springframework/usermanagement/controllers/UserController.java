package master.springframework.usermanagement.controllers;

import lombok.extern.slf4j.Slf4j;
import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    private static final String USER_FORM_URL = "user/userForm";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getById(id).block());
        return "user/show";
    }

    @GetMapping("user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserCommand());
        return "user/userForm";
    }

    @GetMapping("user/{id}/update")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.getById(id).block());
        return USER_FORM_URL;
    }

    @PostMapping("user")
    public String saveOrUpdate(@Valid @ModelAttribute("user") UserCommand command, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return USER_FORM_URL;
        }

        UserCommand savedCommand = userService.save(command).block();

        return "redirect:/user/" + savedCommand.getId() + "/show";
    }

    @GetMapping("user/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        userService.deleteById(id);
        return "redirect:/";
    }
}
