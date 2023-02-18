package ml.ipvz.fa.authservice.base.permission.model;

public enum Resource {
    GROUP(1),
    CATEGORY(2),
    ACCOUNT(2),
    OPERATION(2);

    public final int level;

    Resource(int level) {
        this.level = level;
    }
}
