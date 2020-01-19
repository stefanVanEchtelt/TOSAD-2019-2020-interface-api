package HU.Tosad.businessRule;

public class BusinessRuleEventTrigger {
    private int businessRuleId;
    private int eventTriggerTypeId;

    public BusinessRuleEventTrigger(int businessRuleId, int eventTriggerTypeId) {
        this.businessRuleId = businessRuleId;
        this.eventTriggerTypeId = eventTriggerTypeId;
    }

    public int getBusinessRuleId() {
        return businessRuleId;
    }

    public int getEventTriggerTypeId() {
        return eventTriggerTypeId;
    }
}
