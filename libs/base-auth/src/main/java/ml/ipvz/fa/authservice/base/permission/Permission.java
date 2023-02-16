package ml.ipvz.fa.authservice.base.permission;

import java.util.Objects;

import ml.ipvz.fa.authservice.base.permission.model.Resource;
import ml.ipvz.fa.authservice.base.permission.model.Role;
import ml.ipvz.fa.authservice.base.permission.model.Target;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public final class Permission {
    public final Resource resource;
    public final Target target;
    public final Role role;
    @Nullable
    public final String targetId;

    Permission(Resource resource, Target target, @Nullable String targetId, Role role) {
        if (target == Target.ID && targetId == null) {
            throw new IllegalArgumentException("targetId must not be null");
        }
        this.resource = resource;
        this.target = target;
        this.targetId = targetId;
        this.role = role;
    }

    public static ResourceBuilder builder() {
        return new ResourceBuilder();
    }

    static Permission fromString(@NonNull String permission) {
        String[] parts = permission.toUpperCase().split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("permission invalid: " + permission);
        }
        Resource resource = Resource.valueOf(parts[0]);
        Target target = Target.ALL.name().equalsIgnoreCase(parts[1]) ? Target.ALL : Target.ID;
        String targetId = target == Target.ID ? parts[1] : null;
        Role role = Role.valueOf(parts[2]);

        return new Permission(resource, target, targetId, role);
    }

    @Override
    public String toString() {
        String id = target == Target.ID ? targetId : target.name();
        return "%s.%s.%s".formatted(resource, id, role).toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permission that = (Permission) o;
        return resource == that.resource && target == that.target && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, target, role);
    }

    public boolean hasPermission(Permission o) {
        if (this == o) return true;
        if (this.resource != o.resource) return false;
        if (this.target == Target.ID) {
            if (o.target == Target.ALL) {
                return false;
            } else if (!Objects.requireNonNull(this.targetId).equalsIgnoreCase(o.targetId)) {
                return false;
            }
        }
        return this.role.compareTo(o.role) >= 0;
    }

    public static class ResourceBuilder {
        private ResourceBuilder() {
        }

        public RoleBuilder group() {
            return group(null);
        }

        public RoleBuilder group(String id) {
            return resource(Resource.GROUP, id);
        }

        public RoleBuilder category() {
            return category(null);
        }

        public RoleBuilder category(String id) {
            return resource(Resource.CATEGORY, id);
        }

        public RoleBuilder account() {
            return account(null);
        }

        public RoleBuilder account(String id) {
            return resource(Resource.ACCOUNT, id);
        }

        public RoleBuilder operation() {
            return operation(null);
        }

        public RoleBuilder operation(String id) {
            return resource(Resource.OPERATION, id);
        }

        public RoleBuilder resource(Resource resource) {
            return resource(resource, null);
        }

        public RoleBuilder resource(Resource resource, @Nullable String id) {
            return new RoleBuilder(resource, id);
        }
    }

    public static class RoleBuilder {
        private final Resource resource;
        private final Target target;
        private final String targetId;

        private RoleBuilder(Resource resource, String targetId) {
            this.resource = resource;
            this.targetId = targetId;
            this.target = targetId == null ? Target.ALL : Target.ID;
        }

        public Permission viewer() {
            return role(Role.VIEWER);
        }

        public Permission editor() {
            return role(Role.EDITOR);
        }

        public Permission admin() {
            return role(Role.ADMIN);
        }

        public Permission role(Role role) {
            return new Permission(resource, target, targetId, role);
        }
    }

}
