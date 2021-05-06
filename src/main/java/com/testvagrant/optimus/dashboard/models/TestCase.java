package com.testvagrant.optimus.dashboard.models;


import com.testvagrant.optimus.dashboard.models.dashboard.Device;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter @Setter @Builder
public class TestCase {
    private String name;
    private Collection<String> tags;
    private String status;
    private String featureFileName;

    @Builder.Default
    private String stackTrace = "";

    @Builder.Default
    private int timeTaken = 0;
}
