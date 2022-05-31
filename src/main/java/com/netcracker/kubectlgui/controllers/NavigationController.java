package com.netcracker.kubectlgui.controllers;

import com.netcracker.kubectlgui.constants.ApiPath;
import com.netcracker.kubectlgui.constants.ModelAttributes;
import com.netcracker.kubectlgui.constants.Pages;
import com.netcracker.kubectlgui.model.Pod;
import com.netcracker.kubectlgui.services.PodsService;
import com.netcracker.kubectlgui.utils.CommandRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(ApiPath.API_KUBECTL_GUI)
public class NavigationController {

    private static final String DEFAULT_POD_VALUE = "default";

    private final PodsService podsService;

    @Autowired
    public NavigationController(PodsService podsService) {
        this.podsService = podsService;
    }

    private static abstract class Command {
        public static final String GET_CURRENT_FOLDER = "kubectl exec %s -- pwd";
        public static final String GET_FILES = "kubectl exec %s -- ls -1";
    }


    @GetMapping(ApiPath.NAVIGATION)
    public String showFiles(Model model, @RequestParam(name = "pod", defaultValue = DEFAULT_POD_VALUE) String podName)
            throws IOException {

        List<String> runningPodsNames = podsService.getRunningPodsNames();

        if (DEFAULT_POD_VALUE.equals(podName)) {
            podName = runningPodsNames.get(0);
        } else if (!runningPodsNames.contains(podName)) {
            throw new RuntimeException("Pod name " + podName + " does not exist");
        }

        String currentFolder = CommandRunner.runWithSingleReturn(String.format(Command.GET_CURRENT_FOLDER, podName), 1);
        List<String> filesNames = CommandRunner.runWithReturn(String.format(Command.GET_FILES, podName));
        filesNames.remove(0);

        model.addAttribute(ModelAttributes.RUNNING_PODS, runningPodsNames);
        model.addAttribute(ModelAttributes.CURRENT_FOLDER, currentFolder);
        model.addAttribute(ModelAttributes.FILES_NAMES, filesNames);
        model.addAttribute(ModelAttributes.CURRENT_POD, podName);
        return Pages.NAVIGATION;
    }
}
