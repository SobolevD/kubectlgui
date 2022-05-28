package com.netcracker.kubectlgui.utils.serializers.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.netcracker.kubectlgui.model.KubectlConfig;
import com.netcracker.kubectlgui.utils.serializers.Serializer;

public class YamlKubectlConfigSerializer implements Serializer<KubectlConfig> {

    protected final ObjectMapper om = new ObjectMapper(new YAMLFactory());

    @Override
    public String serialize(KubectlConfig object) throws JsonProcessingException {
        return om.writeValueAsString(object);
    }

    @Override
    public KubectlConfig deserialize(String object) throws JsonProcessingException {
        return om.readValue(object, KubectlConfig.class);
    }
}
