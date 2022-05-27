package com.netcracker.kubectlgui.controllers;

import com.netcracker.kubectlgui.constants.ApiPath;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ApiPath.API_KUBECTL_GUI)
public class HomeController {

    private interface Pages {
        String HOME = "home";
    }

    @GetMapping(ApiPath.HOME_PAGE)
    public String showHomePage(Model model) {
        return Pages.HOME;
    }

}
