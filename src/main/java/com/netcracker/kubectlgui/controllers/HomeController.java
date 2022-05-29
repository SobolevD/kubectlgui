package com.netcracker.kubectlgui.controllers;

import com.netcracker.kubectlgui.constants.ApiPath;
import com.netcracker.kubectlgui.constants.Pages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ApiPath.API_KUBECTL_GUI)
public class HomeController {

    @GetMapping(ApiPath.HOME_PAGE)
    public String showHomePage(Model model) {
        return Pages.HOME;
    }

}
