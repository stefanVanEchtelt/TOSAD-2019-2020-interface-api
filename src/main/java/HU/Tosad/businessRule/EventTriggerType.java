package HU.Tosad.businessRule;

public class EventTriggerType {
    private int id;
    private String name;

    public EventTriggerType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
