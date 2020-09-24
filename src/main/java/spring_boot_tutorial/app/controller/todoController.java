package spring_boot_tutorial.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import spring_boot_tutorial.app.model.TodoModel;
import spring_boot_tutorial.app.service.TodoService;

@Controller
public class todoController {

  @Autowired
  private TodoService todoService;

  @GetMapping("/todo/index")
  public String getIndex(Model model) {

    List<TodoModel> todos = todoService.selectMany();

    model.addAttribute("todos", todos);

    model.addAttribute("useSidebar", true);

    model.addAttribute("contents", "todo/index::todo_index_contents");
    return "application";

  }
  
  @GetMapping("/todo/new")
  public String getNew(@ModelAttribute TodoModel todo, Model model) {

    model.addAttribute("useSidebar", true);

    model.addAttribute("contents", "todo/new::todo_new_contents");
    return "application";

  }

  @PostMapping("/todo/create")
  public String postCreate(@ModelAttribute @Validated TodoModel todo, BindingResult bindingResult,  Model model) {

    if (bindingResult.hasErrors()) {
      return getNew(todo, model);
    }

    todoService.insert(todo);

    model.addAttribute("useSidebar", true);

    
    return getIndex(model);

  }

}
