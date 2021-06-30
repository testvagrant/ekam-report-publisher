package com.testvagrant.ekam.dashboard.io;

import com.google.gson.reflect.TypeToken;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.dashboard.models.TargetDetails;
import com.testvagrant.ekam.dashboard.models.TestCase;
import com.testvagrant.ekam.dashboard.EkamExecutionTimelinePaths;
import com.testvagrant.ekam.dashboard.models.dashboard.Device;

import java.util.List;
import java.util.Objects;


public class TargetFinder {

    public static Device findTargetDevice(String target, TestCase testCase) {
        if(target.equals("api")) return Device.builder().build();
        String targetsPath = EkamExecutionTimelinePaths.getTargetsPath(testCase);

        List<TargetDetails> targetDetailsList = new GsonParser().deserialize(targetsPath, new TypeToken<List<TargetDetails>>() {}.getType());
        TargetDetails targetDetails = targetDetailsList.size()>0? targetDetailsList.get(0) : TargetDetails.builder().build();

        return Device.builder().deviceName(targetDetails.getName())
                .platformVersion(targetDetails.getPlatformVersion())
                .platform(targetDetails.getPlatform())
                .runsOn("Device")
                .udid(Objects.isNull(targetDetails.getUdid())? "123456": targetDetails.getUdid())
                .build();
    }
}
