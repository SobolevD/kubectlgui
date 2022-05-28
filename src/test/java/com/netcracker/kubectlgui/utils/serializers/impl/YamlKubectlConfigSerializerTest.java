package com.netcracker.kubectlgui.utils.serializers.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.kubectlgui.model.KubectlConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YamlKubectlConfigSerializerTest {

    private final String TEST_CONFIG_YAML = "apiVersion: v1\n" +
            "clusters:\n" +
            "- cluster:\n" +
            "    insecure-skip-tls-verify: true\n" +
            "    server: https://api.ora2pg-k8s.managed.netcracker.cloud:6443\n" +
            "  name: kuber-ora2pg\n" +
            "contexts:\n" +
            "- context:\n" +
            "    cluster: kuber-ora2pg\n" +
            "    namespace: toms-hds\n" +
            "    user: k8s-deployer\n" +
            "  name: kuber-ora2pg\n" +
            "current-context: kuber-ora2pg\n" +
            "kind: Config\n" +
            "preferences: {}\n" +
            "users:\n" +
            "- name: k8s-deployer\n" +
            "  user:\n" +
            "    token: eyJhbGciOiJSUzI1NiIsImtpZCI6ImE3eDlHWjVzLTQzWnFzMklTQ2JkaG9rYzRTYnlpWlpJblM1WUZiQlVBdTQifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJzdXBlcmFkbWluLXRva2VuLXp6dzk5Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InN1cGVyYWRtaW4iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI3ZTAyZThkZC0zM2U0LTQwMzgtOTM2OS00YzJmYjUzZjJjMWMiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06c3VwZXJhZG1pbiJ9.HckT6xBhg3gj6Uymynk_-NB4L5J0VIzzoJjmLnYTN44gZ7hLTECKMVEIO05KTSnbd-4K7myOpflSrv53qkc6OLj3QARamlctrr82bw2hmkEBJ5tnrzlwT3ecKsYvSyIFQv0Co_brZnMsv1pYOTYOpdPGK2w1nE0hyXgA5FO4Ns4sPWRIYNwGpmaDalczn7EIlgPzC4d912CbDgB4922avEpt_zmLSF2SLONMVXpZBMawA6yE7290LytwoZVL9mBEPMN0jKeRiQkwVcOOkvs85VxcnrtCBPJt1ex0Q6iqNnw5-4T15Wy62IyybYaLx8eGAH0eM6uTtksG_TKpWrBvNQ";

    @Test
    void deserialize() {
        YamlKubectlConfigSerializer serializer = new YamlKubectlConfigSerializer();
        try {
            KubectlConfig testConfig = serializer.deserialize(TEST_CONFIG_YAML);

            Assertions.assertEquals("v1", testConfig.getApiVersion(),
                    "Api version wrong. Expected 'v1' but received '" + testConfig.getApiVersion() + "'");
            Assertions.assertEquals(1, testConfig.getClusters().size(),
                    "Clusters count wrong. Expected '1' but received '" + testConfig.getClusters().size() + "'");
            KubectlConfig.Cluster cluster = testConfig.getClusters().get(0).getCluster();
            Assertions.assertEquals(Boolean.TRUE, cluster.isInsecureSkipTlsVerify(),
                    "Insecure skip TLS verify parameter wrong. Expected 'true' but received '"
                            + cluster.isInsecureSkipTlsVerify() + "'");
            Assertions.assertEquals("https://api.ora2pg-k8s.managed.netcracker.cloud:6443", cluster.getServer(),
                    "Server url is wrong. Expected 'https://api.ora2pg-k8s.managed.netcracker.cloud:6443' " +
                            "but received '" + cluster.getServer() + "'");
            Assertions.assertEquals(1, testConfig.getContexts().size(),
                    "Contexts count wrong. Expected '1' but received '" + testConfig.getContexts().size() + "'");
            KubectlConfig.Context context = testConfig.getContexts().get(0).getContext();
            Assertions.assertEquals("kuber-ora2pg", context.getCluster(),
                    "Cluster name wrong. Expected 'kuber-ora2pg' but received '" + context.getCluster() + "'");
            Assertions.assertEquals("toms-hds", context.getNamespace(),
                    "Namespace is wrong. Expected 'toms-hds' but received '" + context.getNamespace() + "'");
            Assertions.assertEquals("k8s-deployer", context.getUser(),
                    "Username is wrong. Expected 'k8s-deployer' but received '" + context.getUser() + "'");
            Assertions.assertEquals("kuber-ora2pg", testConfig.getCurrentContext(),
                    "Username is wrong. Expected 'kuber-ora2pg' but received '"
                            + testConfig.getCurrentContext() + "'");
            Assertions.assertEquals("Config", testConfig.getKind(),
                    "Username is wrong. Expected 'Config' but received '" + testConfig.getKind() + "'");
            Assertions.assertEquals(1, testConfig.getUsers().size(),
                    "Users count wrong. Expected '1' but received '" + testConfig.getUsers().size() + "'");
            KubectlConfig.UserEntity user = testConfig.getUsers().get(0);
            Assertions.assertEquals("k8s-deployer", user.getName(),
                    "Username is wrong. Expected 'k8s-deployer' but received '" + user.getName() + "'");
            String expectedToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImE3eDlHWjVzLTQzWnFzMklTQ2JkaG9rYzRTYnlp" +
                    "WlpJblM1WUZiQlVBdTQifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50I" +
                    "iwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c" +
                    "3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJzd" +
                    "XBlcmFkbWluLXRva2VuLXp6dzk5Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3Vud" +
                    "C9zZXJ2aWNlLWFjY291bnQubmFtZSI6InN1cGVyYWRtaW4iLCJrdWJlcm5ldGVzLmlvL" +
                    "3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI3ZTAyZThkZC0zM2U0L" +
                    "TQwMzgtOTM2OS00YzJmYjUzZjJjMWMiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291b" +
                    "nQ6a3ViZS1zeXN0ZW06c3VwZXJhZG1pbiJ9.HckT6xBhg3gj6Uymynk_-NB4L5J0VIzz" +
                    "oJjmLnYTN44gZ7hLTECKMVEIO05KTSnbd-4K7myOpflSrv53qkc6OLj3QARamlctrr82" +
                    "bw2hmkEBJ5tnrzlwT3ecKsYvSyIFQv0Co_brZnMsv1pYOTYOpdPGK2w1nE0hyXgA5FO4" +
                    "Ns4sPWRIYNwGpmaDalczn7EIlgPzC4d912CbDgB4922avEpt_zmLSF2SLONMVXpZBMaw" +
                    "A6yE7290LytwoZVL9mBEPMN0jKeRiQkwVcOOkvs85VxcnrtCBPJt1ex0Q6iqNnw5-4T1" +
                    "5Wy62IyybYaLx8eGAH0eM6uTtksG_TKpWrBvNQ";
            Assertions.assertEquals(expectedToken, user.getUser().getToken(),
                    "Token is wrong. Expected '" + expectedToken + "' but received '"
                            + user.getUser().getToken() + "'");

        }
        catch (JsonProcessingException e) {
            Assertions.fail("Could not deserialize test yaml config");
        }
    }
}