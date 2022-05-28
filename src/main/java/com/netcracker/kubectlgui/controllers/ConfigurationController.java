package com.netcracker.kubectlgui.controllers;

import com.netcracker.kubectlgui.constants.ApiPath;
import com.netcracker.kubectlgui.model.KubectlConfig;
import com.netcracker.kubectlgui.model.KubectlConfigFormEntity;
import com.netcracker.kubectlgui.services.FilesService;
import com.netcracker.kubectlgui.utils.File2String;
import com.netcracker.kubectlgui.utils.KubectlConfigConverter;
import com.netcracker.kubectlgui.utils.serializers.Serializer;
import com.netcracker.kubectlgui.utils.serializers.impl.YamlKubectlConfigSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping(ApiPath.API_KUBECTL_GUI)
public class ConfigurationController {

    @Value(ConfigKeys.CONFIG_PATHS)
    private String configsFolder;

    @Value(ConfigKeys.USING_CONFIG)
    private String usingConfigFileName;

    private final FilesService filesService;

    @Autowired
    public ConfigurationController(FilesService filesService) {
        this.filesService = filesService;
    }

    private interface RedirectPages {
        String REDIRECT_TO_CONFIGURATIONS = "redirect:/api/kubectl-gui/v1/configuration";
    }

    private interface Pages {
        String CONFIGURATION = "configuration";
    }

    private interface ModelAttributes {
        String CONFIG = "config";
        String CONFIG_NAME = "config_name";
        String EXISTING_CONFIG_NAME = "existing_config";
        String CONFIGS_FILES_NAMES = "configsFileNames";
    }

    private interface ConfigKeys {
        String CONFIG_PATHS = "${kubectl.configs.path}";
        String USING_CONFIG = "${kubectl.configs.using}";
    }

    @GetMapping(ApiPath.CONFIGURATION_PAGE)
    public String showConfigurationPage(Model model) throws IOException {

        String currentConfigStr = File2String.read(usingConfigFileName);
        Serializer<KubectlConfig> serializer = new YamlKubectlConfigSerializer();
        KubectlConfig currentConfig = serializer.deserialize(currentConfigStr);

        KubectlConfigFormEntity formEntity = KubectlConfigConverter.convertToFormEntity(currentConfig);

        model.addAttribute(ModelAttributes.CONFIG, formEntity);
        model.addAttribute(ModelAttributes.CONFIGS_FILES_NAMES, filesService.getAllConfigsFilesNames(configsFolder));
        return Pages.CONFIGURATION;
    }

    @PostMapping(ApiPath.CONFIGURATION_PAGE + ApiPath.USE_EXISTING)
    public String useExistingConfiguration(@RequestParam(ModelAttributes.EXISTING_CONFIG_NAME) String configName) throws IOException {

        String configFullName = configsFolder + "\\" + configName;
        String currentConfigStr = File2String.read(configFullName);
        Serializer<KubectlConfig> serializer = new YamlKubectlConfigSerializer();
        KubectlConfig currentConfig = serializer.deserialize(currentConfigStr);
        KubectlConfigFormEntity formEntity = KubectlConfigConverter.convertToFormEntity(currentConfig);
        filesService.saveConfig(formEntity, usingConfigFileName);
        return RedirectPages.REDIRECT_TO_CONFIGURATIONS;
    }

    @PostMapping(value = ApiPath.CONFIGURATION_PAGE + ApiPath.UPDATE)
    public String updateConfiguration(@ModelAttribute(ModelAttributes.CONFIG) KubectlConfigFormEntity formEntity) throws IOException {
        filesService.saveConfig(formEntity, usingConfigFileName);
        return RedirectPages.REDIRECT_TO_CONFIGURATIONS;
    }

    @PostMapping(value = ApiPath.CONFIGURATION_PAGE + ApiPath.SAVE)
    public String saveConfiguration(@RequestParam(ModelAttributes.CONFIG_NAME) String configName,
                                    @ModelAttribute(ModelAttributes.CONFIG) KubectlConfigFormEntity formEntity) throws IOException {

        filesService.saveConfig(formEntity, configsFolder + "\\" + configName);
        return RedirectPages.REDIRECT_TO_CONFIGURATIONS;
    }
}
