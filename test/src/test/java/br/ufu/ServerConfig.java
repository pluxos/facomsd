package br.ufu;

import java.util.List;
import java.util.Map;

public class ServerConfig {

    private int port;
    private String id;
    private String smallerKey;
    private int snapTime;
    private List<Integer> rightServers;
    private List<Integer> leftServers;
    private String maxKey;
    private List<Integer> clusterAddresses;
    private int clusterId;
    private int atomixPort;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmallerKey() {
        return smallerKey;
    }

    public void setSmallerKey(String smallerKey) {
        this.smallerKey = smallerKey;
    }

    public int getSnapTime() {
        return snapTime;
    }

    public void setSnapTime(int snapTime) {
        this.snapTime = snapTime;
    }

    public List<Integer> getRightServers() {
        return rightServers;
    }

    public void setRightServers(List<Integer> rightServers) {
        this.rightServers = rightServers;
    }

    public List<Integer> getLeftServers() {
        return leftServers;
    }

    public void setLeftServers(List<Integer> leftServers) {
        this.leftServers = leftServers;
    }

    public String getMaxKey() {
        return maxKey;
    }

    public void setMaxKey(String maxKey) {
        this.maxKey = maxKey;
    }

    public List<Integer> getClusterAddresses() {
        return clusterAddresses;
    }

    public void setClusterAddresses(List<Integer> clusterAddresses) {
        this.clusterAddresses = clusterAddresses;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public int getAtomixPort() {
        return atomixPort;
    }

    public void setAtomixPort(int atomixPort) {
        this.atomixPort = atomixPort;
    }
}
