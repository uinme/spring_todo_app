package spring_boot_tutorial.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class staticPageController {
  
  @GetMapping("/home")
  public String getHome(Model model) {

    model.addAttribute("contents", "staticpages/home::staticpages_home_contents");

    return "application";

  }

}
