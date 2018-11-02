package master.springframework.usermanagement.controllers;

import lombok.extern.slf4j.Slf4j;
import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.exceptions.NotFoundException;
import master.springframework.usermanagement.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    private static final String USER_FORM_URL = "user/userForm";

    private final UserService userService;

    private WebDataBinder webDataBinder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder("group")
    public void initBinder(WebDataBinder webDataBinder){
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/user/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/show";
    }

    @GetMapping("user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserCommand());
        return "user/userForm";
    }

    @GetMapping("user/{id}/update")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.getById(id));
        return USER_FORM_URL;
    }

    @PostMapping("user")
    public String saveOrUpdate(@ModelAttribute("user") UserCommand command){
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model){

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "404Error";
    }
}
