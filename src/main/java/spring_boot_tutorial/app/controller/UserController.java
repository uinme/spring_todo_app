package spring_boot_tutorial.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import spring_boot_tutorial.app.model.SignupFormModel;
import spring_boot_tutorial.app.model.UserModel;
import spring_boot_tutorial.app.service.UserService;

@Controller
public class UserController {

  @Autowired
  private UserService userService;
  
  @GetMapping("/user/index")
  public String getUsers(@ModelAttribute UserModel user, Model model) {

    user.setId(1);
    user.setEmail("spring@spring.com");
    user.setUsername("spring");
    user.setPassword("password");

    model.addAttribute("contents", "user/index::user_index_contents");

    return "application";

  }

  @GetMapping("/login")
  public String getLogin(Model model) {

    model.addAttribute("contents",  "login/login::login_contents");
    return "application";

  }

  @GetMapping("/signup")
  public String getSignup(@ModelAttribute SignupFormModel form, Model model) {

    model.addAttribute("contents", "user/signup::signup_contents");
    return "application";

  }

  @PostMapping("/signup")
  public String postSingup(@ModelAttribute @Validated SignupFormModel form, BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {

      return getSignup(form, model);

    }

    UserModel user = new UserModel();

    user.setEmail(form.getEmail());
    user.setPassword(form.getPassword());
    user.setUsername(form.getUsername());

    boolean result = userService.insertOne(user);

    if (result) {

      model.addAttribute("contents", "todo/index::todo_index_contents");
      return "application";

    }

    return getSignup(form, model);

  }

}
