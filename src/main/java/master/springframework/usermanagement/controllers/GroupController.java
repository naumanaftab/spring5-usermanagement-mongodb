package master.springframework.usermanagement.controllers;

import lombok.extern.slf4j.Slf4j;
import master.springframework.usermanagement.commands.GroupCommand;
import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.services.GroupService;
import master.springframework.usermanagement.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    private WebDataBinder webDataBinder;

    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }


    @InitBinder("group")
    public void initBinder(WebDataBinder webDataBinder){
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/user/{userId}/groups")
    public String listGroups(@PathVariable String userId, Model model){
        log.debug("Getting group list for user id: " + userId);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("user", userService.getById(userId));

        return "user/group/list";
    }

    @GetMapping("user/{userId}/group/{id}/show")
    public String showUserGroup(@PathVariable String userId,
                                @PathVariable String id, Model model){
        model.addAttribute("group", groupService.findByUserIdAndGroupId(userId, id));
        return "user/group/show";
    }

    @GetMapping("user/{userId}/group/new")
    public String newUserGroupForm(@PathVariable String userId, Model model){

        //make sure we have a good id value
        UserCommand userCommand = userService.getById(userId).block();
        //todo raise exception if null

        //need to return back parent id for hidden form property
        GroupCommand groupCommand = new GroupCommand();
        groupCommand.setUserId(userId);
        model.addAttribute("group", groupCommand);

        return "user/group/groupForm";
    }

    @GetMapping("user/{userId}/group/{id}/update")
    public String updateUserGroupForm(@PathVariable String userId,
                                         @PathVariable String id, Model model){
        model.addAttribute("group", groupService.findByUserIdAndGroupId(userId, id).block());
        return "user/group/groupForm";
    }

    @PostMapping("user/{userId}/group")
    public String saveOrUpdate(@ModelAttribute("group") GroupCommand command){

        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "user/group/groupForm";
        }

        GroupCommand savedCommand = groupService.saveGroup(command).block();

        log.debug("saved group id:" + savedCommand.getId());

        return "redirect:/user/" + savedCommand.getUserId() + "/group/" + savedCommand.getId() + "/show";
    }

    @GetMapping("user/{userId}/group/{id}/delete")
    public String deleteGroup(@PathVariable String userId,
                                   @PathVariable String id){

        log.debug("deleting group id:" + id);
        groupService.deleteById(userId, id).block();

        return "redirect:/user/" + userId + "/groups";
    }

}

