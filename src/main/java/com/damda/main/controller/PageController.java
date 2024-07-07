package com.damda.main.controller;

import com.damda.global.auth.resolver.LoginUser;
import com.damda.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    //couples
    @GetMapping("/couples-edit")
    public String coupleEditPage() {
        return "c_edit";
    }

    @GetMapping("/couples-create")
    public String coupleCreatePage() {
        return "c_join";
    }

    @GetMapping("/couples-mypage")
    public String coupleMyPage(@LoginUser User user, Model model) {
        model.addAttribute("coupleId", user.getCouple().getId());
        return "c_profile";
    }

    @GetMapping("/couples-profile/{id}")
    public String coupleProfilePage(@PathVariable Long id, Model model) {
        model.addAttribute("coupleId", id);
        return "c_profile";
    }

    // feeds
    @GetMapping("/feeds-create")
    public String feedCreatePage() {
        return "upload";
    }

    @GetMapping("/feeds-list")
    public String feedList() {
        return "feedlist";
    }

    @GetMapping("/feeds-detail/{id}")
    public String feedsDetail(@PathVariable Long id, Model model) {
        model.addAttribute("feedId", id);
        return "feed_detail";
    }

    @GetMapping("/feeds-edit/{id}")
    public String feedsEdit(@PathVariable Long id, Model model) {
        model.addAttribute("feedId", id);
        return "upload_edit";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "p_join";
    }

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }
}
