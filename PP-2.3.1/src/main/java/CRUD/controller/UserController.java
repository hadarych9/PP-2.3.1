package CRUD.controller;

import CRUD.model.User;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class UserController {

    UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @GetMapping("/main")
    public void getMain(ModelMap modelMap){
        modelMap.addAttribute("userData", service.getAllUsers());
    }

    @GetMapping("/add")
    public ModelAndView addUser(){
        return new ModelAndView("add");
    }

    @PostMapping("/add")
    public String addUser(ModelMap modelMap, @RequestParam("name")String name, @RequestParam("password")String password, @RequestParam("age")Long age) {
        String result;
        if(service.doesUserNotExist(name)){
            if(age != null) {
                if(name == null | password == null) {
                    result = "Пожалуйста, заполните все поля";
                } else if(!name.equals(name.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ]", ""))){
                    result = "Для ввода логина необходимо использовать только буквы или цифры";
                } else if(age <= 0) {
                    result = "Возраст должен быть больше нуля";
                } else {
                    service.addUser(new User(name, password, age));
                    result = "Пользователь " + name + " добавлен(а)!";
                }
            } else result = "Пожалуйста, заполните все поля";
        } else result =  "Пользователь с таким именем уже существует";
        modelMap.addAttribute("result", result);
        return "redirect:/main";
    }

    @GetMapping("/update")
    public String updateUser(ModelMap modelMap, @RequestParam("id")Long id){
        User user = service.getById(id);
        if(user != null) {
            modelMap.addAttribute("user", user);
            return null;
        } else {
            modelMap.addAttribute("result", "Такого пользователя не существует");
            return "redirect:/main";
        }
    }

    @PostMapping("/update")
    public String updateUser(ModelMap modelMap, @RequestParam("name")String name, @RequestParam("password")String password, @RequestParam("age")Long age){
        String success = "Изменения внесены!";
        String result = success;
        User user = service.getByName(name);
        if((name == null | name.equals(user.getName()))
                & (password == null | password.equals(user.getPassword()))
                & (age == null | age == user.getAge())){
            result = "Изменения не внесены";
        } else if(!name.equals(name.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ]", ""))) {
            name = user.getName();
            result = "Для ввода логина необходимо использовать только буквы или цифры";
        }
        if(!service.doesUserNotExist(name) & !name.equals(user.getName())) {
            name = user.getName();
            result = "Пользователь с таким именем уже существует";
        } else if(name.equals("")) {
            name = user.getName();
        }
        if(password.equals("")) password = user.getPassword();
        if(age == null) age = user.getAge();
        service.updateUser(new User(user.getId(), name, password, age));
        modelMap.addAttribute("result", result);
        return "redirect:/main";
    }

    @GetMapping("/delete")
    public String deleteUser(ModelMap modelMap, @RequestParam("id")Long id){
        User user = service.getById(id);
        String result;
        if(user == null) {
            modelMap.addAttribute("result", "Такого пользователя не существует");
            return "redirect:/main";
        }
        int x = service.deleteUser(id);
        if (x > 0){
            result = "Удаление " + user.getName() + " успешно проведено!";
        } else {
             result = "Удаление неудачно";
        }
        modelMap.addAttribute("result", result);
        return "redirect:/main";
    }

    @GetMapping("/drop")
    public String dropTable(ModelMap modelMap){
        service.dropTable();
        modelMap.addAttribute("result", "Очистка базы успешно проведена!");
        return "redirect:/main";
    }
}
