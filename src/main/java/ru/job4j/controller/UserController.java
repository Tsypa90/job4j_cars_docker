package ru.job4j.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
@RequiredArgsConstructor
public class UserController {
    @NonNull
    private final UserService service;

    @GetMapping("/registration")
    public String registration(Model model, HttpSession session,
                               @RequestParam(name = "fail", required = false) Boolean fail) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = service.save(user);
        if (regUser.isPresent()) {
            return "redirect:/registration?fail=true";
        }
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = service.login(user.getLogin(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/login?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
