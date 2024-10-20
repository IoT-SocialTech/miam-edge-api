package com.miam.edgeApi.enums;

public enum MetricsStatus {
    NORMAL("Normal"),
    WARNING("Warning"),
    DANGER("Danger");

    private String status;

    MetricsStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
