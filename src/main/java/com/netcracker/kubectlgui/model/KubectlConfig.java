package com.netcracker.kubectlgui.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KubectlConfig {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClusterEntity {
        private Cluster cluster;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cluster {

        @JsonProperty("insecure-skip-tls-verify")
        private boolean insecureSkipTlsVerify;
        private String server;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContextEntity {
        private Context context;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Context {
        private String cluster;
        private String namespace;
        private String user;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserEntity {
        private String name;
        private User user;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String token;
    }

    private String apiVersion;
    private List<ClusterEntity> clusters;
    private List<ContextEntity> contexts;

    @JsonProperty("current-context")
    private String currentContext;
    private String kind;
    private JsonNode preferences;
    private List<UserEntity> users;
}
