package com.testvagrant.ekam.dashboard.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

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
