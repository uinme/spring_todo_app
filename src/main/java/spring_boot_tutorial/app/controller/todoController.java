package spring_boot_tutorial.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import spring_boot_tutorial.app.model.TodoModel;

@Controller
public class todoController {
  
  @GetMapping("/todo/new")
  public String getNew(@ModelAttribute TodoModel todo, Model model) {

    model.addAttribute("contents", "todo/new::todo_new_contents");

    return "application";

  }

  @PostMapping("/todo/create")
  public String postCreate(@ModelAttribute @Validated TodoModel todo, BindingResult bindingResult,  Model model) {

    if (bindingResult.hasErrors()) {
      return getNew(todo, model);
    }

    model.addAttribute("contents", "staticpages/home::staticpages_home_contents");

    return "application";

  }

}
