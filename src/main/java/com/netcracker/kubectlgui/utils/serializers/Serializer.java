package com.netcracker.kubectlgui.utils.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Serializer<Entity> {
    String serialize(Entity object) throws JsonProcessingException;
    Entity deserialize(String object) throws JsonProcessingException;
}
