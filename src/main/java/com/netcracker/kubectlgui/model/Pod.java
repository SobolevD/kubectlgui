package com.netcracker.kubectlgui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pod {

    public interface Statuses {
        String RUNNING = "Running";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadyStatus {
        private Integer ready;
        private Integer all;
    }

    public Pod(String name, String ready, String status, String restarts, String age) {
        this.name = name;

        String[] readyStatus = ready.split("/");
        this.readyStatus = new ReadyStatus(Integer.parseInt(readyStatus[0]), Integer.parseInt(readyStatus[1]));
        this.status = status;
        this.restarts = Integer.parseInt(restarts);
        this.age = age;
    }

    private String name;
    private ReadyStatus readyStatus;
    private String status;
    private Integer restarts;
    private String age;
}
