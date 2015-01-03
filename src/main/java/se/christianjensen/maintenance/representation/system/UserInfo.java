package se.christianjensen.maintenance.representation.system;

public class UserInfo {
    private String user;
    private String device;
    private String host;
    private long time;

    public static UserInfo fromSigarBean(org.hyperic.sigar.Who who) {
        return new UserInfo(who.getUser(), who.getDevice(), who.getHost(), who.getTime());
    }

    public UserInfo(String user, String device, String host, long time) {
        this.user = user;
        this.device = device;
        this.host = host;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public String getDevice() {
        return device;
    }

    public String getHost() {
        return host;
    }

    public long getTime() {
        return time;
    }
}