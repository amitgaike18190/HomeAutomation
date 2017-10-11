package com.iot.entity;

/**
 * Created by amit.gaike on 12/30/2016.
 */

public class ContainerInfo {
    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public int getContainerImageId() {
        return containerImageId;
    }

    public void setContainerImageId(int containerImageId) {
        this.containerImageId = containerImageId;
    }

    public String getcontainerName() {
        return containerName;
    }

    public void setcontainerName(String containerString) {
        this.containerName = containerString;
    }

    private int containerId;
    private int containerImageId;
    private String containerName;
}
