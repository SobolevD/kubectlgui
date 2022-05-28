package com.netcracker.kubectlgui.utils;

import com.netcracker.kubectlgui.model.KubectlConfig;
import com.netcracker.kubectlgui.model.KubectlConfigFormEntity;

import java.util.List;

public class KubectlConfigConverter {

    private static final String CONFIG_KIND = "Config";
    private static final String API_VERSION = "v1";

    public static KubectlConfigFormEntity convertToFormEntity(KubectlConfig config) {
        KubectlConfigFormEntity formEntity = new KubectlConfigFormEntity();

        formEntity.setClusterName(config.getClusters().get(0).getName());
        formEntity.setContextName(config.getContexts().get(0).getName());
        formEntity.setServerUrl(config.getClusters().get(0).getCluster().getServer());
        formEntity.setNamespace(config.getContexts().get(0).getContext().getNamespace());
        formEntity.setUser(config.getUsers().get(0).getName());
        formEntity.setToken(config.getUsers().get(0).getUser().getToken());

        return formEntity;
    }

    public static KubectlConfig convertToConfig(KubectlConfigFormEntity formEntity) {
        KubectlConfig kubectlConfig = new KubectlConfig();

        KubectlConfig.ContextEntity contextEntity = new KubectlConfig.ContextEntity();
        KubectlConfig.Context context = new KubectlConfig.Context();
        KubectlConfig.ClusterEntity clusterEntity = new KubectlConfig.ClusterEntity();
        KubectlConfig.Cluster cluster = new KubectlConfig.Cluster();
        KubectlConfig.UserEntity userEntity = new KubectlConfig.UserEntity();
        KubectlConfig.User user = new KubectlConfig.User();

        context.setNamespace(formEntity.getNamespace());
        context.setUser(formEntity.getUser());
        context.setCluster(formEntity.getClusterName());

        cluster.setServer(formEntity.getServerUrl());
        cluster.setInsecureSkipTlsVerify(true);

        user.setToken(formEntity.getToken());

        contextEntity.setName(formEntity.getContextName());
        contextEntity.setContext(context);

        clusterEntity.setName(formEntity.getClusterName());
        clusterEntity.setCluster(cluster);

        userEntity.setName(formEntity.getUser());
        userEntity.setUser(user);

        kubectlConfig.setContexts(List.of(contextEntity));
        kubectlConfig.setClusters(List.of(clusterEntity));
        kubectlConfig.setUsers(List.of(userEntity));

        kubectlConfig.setCurrentContext(formEntity.getContextName());
        kubectlConfig.setApiVersion(API_VERSION);
        kubectlConfig.setPreferences(null);
        kubectlConfig.setKind(CONFIG_KIND);

        return kubectlConfig;
    }
}
