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
                    result = "Please fill in all fields";
                } else if(!name.equals(name.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ]", ""))){
                    result = "You need to use only letters and digits for login";
                } else if(age <= 0) {
                    result = "Age must be more then 0";
                } else {
                    service.addUser(new User(name, password, age));
                    result = "User " + name + " is added!";
                }
            } else result = "Please fill in all fields";
        } else result =  "User already exists";
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
            modelMap.addAttribute("result", "No such user exists");
            return "redirect:/main";
        }
    }

    @PostMapping("/update")
    public String updateUser(ModelMap modelMap, @RequestParam("name")String name, @RequestParam("password")String password, @RequestParam("age")Long age){
        String success = "Update successful!";
        String result = success;
        User user = service.getByName(name);
        if((name == null | name.equals(user.getName()))
                & (password == null | password.equals(user.getPassword()))
                & (age == null | age == user.getAge())){
            result = "No changes made";
        } else if(!name.equals(name.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ]", ""))) {
            name = user.getName();
            result = "You need to use only letters and digits for login";
        }
        if(!service.doesUserNotExist(name) & !name.equals(user.getName())) {
            name = user.getName();
            result = "User already exists";
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
            modelMap.addAttribute("result", "User doesn't exist");
            return "redirect:/main";
        }
        int x = service.deleteUser(id);
        if (x > 0){
            result = user.getName()+" is deleted!";
        } else {
             result = "Delete failed";
        }
        modelMap.addAttribute("result", result);
        return "redirect:/main";
    }

    @GetMapping("/drop")
    public String dropTable(ModelMap modelMap){
        service.dropTable();
        modelMap.addAttribute("result", "Table is cleared!");
        return "redirect:/main";
    }
}
