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

import spring_boot_tutorial.app.model.TodoFormModel;
import spring_boot_tutorial.app.model.TodoListModel;
import spring_boot_tutorial.app.model.TodoModel;
import spring_boot_tutorial.app.service.TodoService;

@Controller
public class TodoController {

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
  public String getNew(@ModelAttribute TodoFormModel todoForm, Model model) {

    /* ストアドプロシージャ
    *  INSERT_TODO(user_id, todoList配列)
    *  ・内部でtodoのidを自動採番
    */

    for (int i = 0; i < 2; i++) {
      TodoListModel newTodoList = new TodoListModel();
      newTodoList.setTodoId(1);
      newTodoList.setContent("");

      todoForm.getTodoLists().add(newTodoList);
    }

    model.addAttribute("useSidebar", true);

    model.addAttribute("contents", "todo/new::todo_new_contents");
    return "application";

  }

  @PostMapping("/todo/create")
  public String postCreate(@ModelAttribute @Validated TodoFormModel todoForm, BindingResult bindingResult,  Model model) {

    if (bindingResult.hasErrors()) {
      return getNew(todoForm, model);
    }

    //todoService.insert(todo);

    TodoFormModel test = todoForm;

    System.out.println(test);

    model.addAttribute("useSidebar", true);

    
    return getIndex(model);

  }

}
