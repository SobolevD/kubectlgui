package com.netcracker.kubectlgui.services;

import com.netcracker.kubectlgui.model.Pod;
import com.netcracker.kubectlgui.utils.CommandRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PodsService {

    private static abstract class Commands {
        public static final String GET_ALL_PODS = "kubectl get pods";
    }

    public List<Pod> getRunningPods() throws IOException {
        List<String> output = CommandRunner.runWithReturn(Commands.GET_ALL_PODS);
        List<Pod> allPods = getAllPodsFromOutput(output);

        List<Pod> runningPods = new ArrayList<>();
        for (Pod pod : allPods) {
            if (Pod.Statuses.RUNNING.equals(pod.getStatus())) {
                runningPods.add(pod);
            }
        }
        return runningPods;
    }

    private List<Pod> getAllPodsFromOutput(List<String> output) {
        List<Pod> result = new ArrayList<>();
        output.remove(0);
        for (String pod : output) {
            List<String> podDetails = getPodDetails(pod);
            result.add(new Pod(
                    podDetails.get(0),
                    podDetails.get(1),
                    podDetails.get(2),
                    podDetails.get(3),
                    podDetails.get(4))
            );
        }
        return result;
    }

    private List<String> getPodDetails(String pod) {
        String[] podDetailsArr = pod.split(" ");
        List<String> podDetails = Arrays.asList(podDetailsArr);

        List<String> validPodDetails = new ArrayList<>();
        for (String detail : podDetails) {
            if (!"".equals(detail)) {
                validPodDetails.add(detail);
            }
        }

        return validPodDetails;
    }
}
