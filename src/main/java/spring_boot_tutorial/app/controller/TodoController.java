package spring_boot_tutorial.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import spring_boot_tutorial.app.model.ActionModel;
import spring_boot_tutorial.app.model.TodoListModel;
import spring_boot_tutorial.app.model.UserDetail;
import spring_boot_tutorial.app.service.TodoService;

@Controller
public class TodoController {

  @Autowired
  private TodoService todoService;

  @GetMapping("/todo/index")
  public String getIndex(Model model) {

    List<TodoListModel> todoLists = todoService.selectMany();

    model.addAttribute("todoLists", todoLists);

    model.addAttribute("useSidebar", true);

    model.addAttribute("contents", "todo/index::todo_index_contents");
    return "application";

  }
  
  @GetMapping("/todo/new")
  public String getNew(@ModelAttribute TodoListModel todoList, Model model) {

    /* ストアドプロシージャ
    *  INSERT_TODO(user_id, todoList配列)
    *  ・内部でtodoのidを自動採番
    */

    for (int i = 0; i < 2; i++) {
      ActionModel newAction = new ActionModel();
      newAction.setContent("");
      todoList.getActions().add(newAction);
    }

    model.addAttribute("useSidebar", true);

    model.addAttribute("contents", "todo/new::todo_new_contents");
    return "application";

  }

  @PostMapping("/todo/create")
  public String postCreate(@ModelAttribute @Validated TodoListModel todoList, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetail userDetail) {

    if (bindingResult.hasErrors()) {
      return getNew(todoList, model);
    }

    todoList.setUserId(userDetail.getUserId());

    todoService.insertTodoList(todoList);

    model.addAttribute("useSidebar", true);

    
    return getIndex(model);

  }

}
