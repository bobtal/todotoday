package com.teamtreehouse.todotoday.web.controller;

import com.teamtreehouse.todotoday.model.Task;
import com.teamtreehouse.todotoday.model.User;
import com.teamtreehouse.todotoday.service.TaskService;
import com.teamtreehouse.todotoday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @RequestMapping({"/", "/todo"})
    public String taskList(Model model) {
        Iterable<Task> tasks = taskService.findAll();
        model.addAttribute("tasks",tasks);
        model.addAttribute("newTask", new Task());
        return "todo";
    }

    @RequestMapping(path = "/mark", method = RequestMethod.POST)
    public String toggleComplete(@RequestParam Long id) {
        Task task = taskService.findOne(id);
        taskService.toggleComplete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    public String addTask(@ModelAttribute Task task, Principal principal) {
        // We need to add a user(_id) to the task, so that it becomes related to
        // the currently logged in user, which is the reason for adding the principal
        // parameter to the method above

        // One way of getting the User object, but it requires a hit
        // to the database. Not an expensive query, but still a query to the database
        // User user = userService.findByUsername(principal.getName());

        // The more appropriate way: the Principal parameter is populated with a
        // UsernamePasswordAuthenticationToken object which has a get principal method
        // whose return value is going to be the object that has implemented
        // the UserDetails interface in a typical Spring Security Setup.
        // In this case, that's our User object, so we can get it like this
        User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        // adding a user to the task
        task.setUser(user);

        taskService.save(task);
        return "redirect:/";
        // We can also move this logic away from the controller and inject the user
        // directly in the DAO layer instead (UserDao interface)
        // example in UserDao.java
    }
}