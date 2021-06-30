package com.testvagrant.ekam.dashboard.models.dashboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BuildOptions {
    private String target;

    public BuildOptions(String target) {
        this.target = target;
    }

    public String getCommitId() {
        String ci_od_commit_id = System.getenv("CI_OD_COMMIT_ID");
        return ci_od_commit_id == null
                ? ""
                : ci_od_commit_id;
    }

    public String getCommitUrl() {
        String ci_od_commit_url = System.getenv("CI_OD_COMMIT_URL");
        return ci_od_commit_url == null
                ? ""
                : ci_od_commit_url;
    }
}
