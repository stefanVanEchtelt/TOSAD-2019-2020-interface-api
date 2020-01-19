package HU.Tosad.businessRule;

public class Rule {
    private int id;
    private String type;
    private int sortOrder;
    private int businessRuleId;

    public Rule(int id, String typeEID, int sortOrder, int businessRulesId) {
        this.id = id;
        this.type = typeEID;
        this.sortOrder = sortOrder;
        this.businessRuleId = businessRulesId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public int getBusinessRuleId() {
        return businessRuleId;
    }

}
