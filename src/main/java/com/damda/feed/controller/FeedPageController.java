package com.damda.feed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FeedPageController {

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
}
