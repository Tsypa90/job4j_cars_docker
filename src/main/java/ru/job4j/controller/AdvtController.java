package ru.job4j.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.model.Advt;
import ru.job4j.model.Car;
import ru.job4j.model.User;
import ru.job4j.service.AdvtService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@ThreadSafe
@RequiredArgsConstructor
public class AdvtController {
    @NonNull
    private final AdvtService service;

    private static void addUserToSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        addUserToSession(model, session);
        model.addAttribute("advts", service.findAll());
        return "index";
    }

    @GetMapping("/photoAdvt/{advtId}")
    public ResponseEntity<Resource> download(@PathVariable("advtId") Integer advtId) {
        Advt advt = service.findById(advtId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(advt.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(advt.getPhoto()));
    }

    @GetMapping("/addAdvt")
    public String addAdvt(Model model, HttpSession session) {
        addUserToSession(model, session);
        List<String> brands = List.of("Toyota", "Nissan", "BMW", "Opel", "Kia");
        List<String> body = List.of("седан", "хетчбэк", "джип", "кроссовер");
        model.addAttribute("brands", brands);
        model.addAttribute("body", body);
        return "addAdvt";
    }

    @GetMapping("/advts/{advtId}")
    public String advt(Model model, @PathVariable("advtId") int id, HttpSession session) {
        addUserToSession(model, session);
        model.addAttribute("advt", service.findById(id));
        return "advt";
    }

    @GetMapping("/update/{advtId}")
    public String updateAdvt(Model model, @PathVariable("advtId") int id, HttpSession session) {
        addUserToSession(model, session);
        model.addAttribute("advt", service.findById(id));
        return "/update";
    }

    @GetMapping("myAdvt")
    public String myAdvt(Model model, HttpSession session) {
        addUserToSession(model, session);
        User user = (User) session.getAttribute("user");
        model.addAttribute("advts", service.myAdvt(user.getId()));
        return "/myAdvt";
    }

    @PostMapping("/saveAdvt")
    public String saveAdvt(@ModelAttribute Advt advt, 
                           @RequestParam("file") MultipartFile file, 
                           @ModelAttribute Car car, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        advt.setUser(user);
        advt.setCar(car);
        advt.setPhoto(file.getBytes());
        service.save(advt);
        return "redirect:/index";
    }

    @PostMapping("/isSaled/{advtId}")
    public String doneItem(@PathVariable("advtId") int id) {
        service.isSaled(id);
        return "redirect:/myAdvt";
    }

    @PostMapping("/delete/{advtId}")
    public String deleteAdvt(@PathVariable("advtId") int id) {
        service.deleteAdvt(id);
        return "redirect:/index";
    }

    @PostMapping("/updateAdvt")
    public String updateAdvt(@ModelAttribute Advt advt, @RequestParam("file") MultipartFile file) throws IOException {
        advt.setPhoto(file.getBytes());
        service.updateAdvt(advt);
        return "redirect:/myAdvt";
    }
}
