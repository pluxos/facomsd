package server;

public class Arriving {

    private Integer mPort;
    private byte[] mPackage;

    public byte[] getPackage() {
        return mPackage;
    }

    public void setPackage( byte[] mPackage ) {
        this.mPackage = mPackage;
    }

    public Integer getmPort() {
        return mPort;
    }

    public void setmPort( Integer mPort ) {
        this.mPort = mPort;
    }

    Arriving(Integer mPort, byte[] mPackage) {
        this.mPort = mPort;
        this.mPackage = mPackage;
    }
}
