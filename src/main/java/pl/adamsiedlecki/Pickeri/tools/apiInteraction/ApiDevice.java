package pl.adamsiedlecki.Pickeri.tools.apiInteraction;

public class ApiDevice {

    private int pin;
    private String state;
    private String key;

    public ApiDevice(int pin, String state, String key) {
        this.pin = pin;
        this.state = state;
        this.key = key;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toJson() {
        return "{" +
                "\"pin\":" + pin +
                ", \"state\":\"" + state + '\"' +
                ", \"key\":\"" + key + '\"' +
                '}';
    }
}
