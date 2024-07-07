package com.damda.couple.controller;

import com.damda.global.auth.resolver.LoginUser;
import com.damda.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class CouplePageController {

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
}
