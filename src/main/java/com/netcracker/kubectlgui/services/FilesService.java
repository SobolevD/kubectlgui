package com.netcracker.kubectlgui.services;

import com.netcracker.kubectlgui.model.KubectlConfig;
import com.netcracker.kubectlgui.model.KubectlConfigFormEntity;
import com.netcracker.kubectlgui.utils.KubectlConfigConverter;
import com.netcracker.kubectlgui.utils.serializers.Serializer;
import com.netcracker.kubectlgui.utils.serializers.impl.YamlKubectlConfigSerializer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FilesService {

    public void saveConfig(KubectlConfigFormEntity formEntity, String configPath) throws IOException {
        KubectlConfig config = KubectlConfigConverter.convertToConfig(formEntity);

        Serializer<KubectlConfig> serializer = new YamlKubectlConfigSerializer();
        String serializedConfig = serializer.serialize(config);

        FileWriter writer = new FileWriter(configPath);
        writer.write(serializedConfig);
        writer.close();
    }

    public List<String> getAllConfigsFilesNames(String configsFolder) {
        File dir = new File(configsFolder);

        if (Objects.isNull(dir.listFiles())) {
            return new ArrayList<>();
        }

        List<File> configs = new ArrayList<>();
        for ( File file : Objects.requireNonNull(dir.listFiles())){
            if ( file.isFile() )
                configs.add(file);
        }

        return configs.stream().map(File::getName).collect(Collectors.toList());
    }
}
