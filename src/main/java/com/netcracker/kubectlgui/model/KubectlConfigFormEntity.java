package com.netcracker.kubectlgui.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KubectlConfigFormEntity {

    private String serverUrl;
    private String clusterName;
    private String contextName;
    private String namespace;
    private String user;
    private String token;
}
