package com.netcracker.kubectlgui.controllers;

import com.netcracker.kubectlgui.constants.ApiPath;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(ApiPath.API_KUBECTL_GUI)
public class ConfigurationController {

    private interface Pages {
        String CONFIGURATION = "configuration";
    }

    @GetMapping(ApiPath.CONFIGURATION_PAGE)
    public String showConfigurationPage(Model model) throws IOException {
        return Pages.CONFIGURATION;
    }

}
