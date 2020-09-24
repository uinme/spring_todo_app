package spring_boot_tutorial.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import spring_boot_tutorial.app.model.UserModel;

@Controller
public class UserController {
  
  @GetMapping("/user/index")
  public String getUsers(@ModelAttribute UserModel user, Model model) {

    user.setId(1);
    user.setEmail("spring@spring.com");
    user.setUsername("spring");
    user.setPassword("password");

    model.addAttribute("contents", "user/index::user_index_contents");

    return "application";

  }

}
