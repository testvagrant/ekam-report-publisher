package com.testvagrant.optimus.dashboard.io;

import com.google.gson.reflect.TypeToken;
import com.testvagrant.optimus.dashboard.models.TargetDetails;
import com.testvagrant.optimus.dashboard.models.TestCase;
import com.testvagrant.optimus.dashboard.models.dashboard.Device;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


public class TargetFinder {

    public static Device findTargetDevice(TestCase testCase) {
        String targetsPath = Paths.get(System.getProperty("user.dir"),
                "build",
                "optimus-execution-timeline",
                testCase.getFeatureFileName(),
                testCase.getName(),
                "target.json"
        ).toString();

        List<TargetDetails> targetDetailsList = GsonParser.toInstance().deserialize(targetsPath, new TypeToken<List<TargetDetails>>() {}.getType());
        TargetDetails targetDetails = targetDetailsList.size()>0? targetDetailsList.get(0) : TargetDetails.builder().build();

        return Device.builder().deviceName(targetDetails.getName())
                .platformVersion(targetDetails.getPlatformVersion())
                .platform(targetDetails.getPlatform())
                .runsOn("Device")
                .udid(Objects.isNull(targetDetails.getUdid())? "123456": targetDetails.getUdid())
                .build();
    }
}
