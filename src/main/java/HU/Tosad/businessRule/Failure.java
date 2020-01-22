package HU.Tosad.businessRule;

public class Failure {
    private int id;
    private String message;
    private int code;
    private String name;
    private int businessRuleId;

    public Failure(int id, String message, int code ,String name, int businessRulesId) {
        this.id = id;
        this.message = message;
        this.code = code;
        this.name = name;
        this.businessRuleId = businessRulesId;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getBusinessRuleId() {
        return businessRuleId;
    }

}
