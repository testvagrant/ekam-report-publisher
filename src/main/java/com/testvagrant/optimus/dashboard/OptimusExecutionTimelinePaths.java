package com.testvagrant.optimus.dashboard;

import java.nio.file.Paths;

public class OptimusExecutionTimelinePaths {
    public static final String BUILD_INFO = Paths.get(System.getProperty("user.dir"),
            "build",
            "optimus-execution-timeline",
            "build-info.json").toString();
}
