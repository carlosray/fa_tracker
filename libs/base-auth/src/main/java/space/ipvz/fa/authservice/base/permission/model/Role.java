package space.ipvz.fa.authservice.base.permission.model;

public enum Role {
    VIEWER(1),
    EDITOR(2),
    ADMIN(3);

    public final int priority;

    Role(int priority) {
        this.priority = priority;
    }
}