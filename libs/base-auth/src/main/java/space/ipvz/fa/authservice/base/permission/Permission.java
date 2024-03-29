package space.ipvz.fa.authservice.base.permission;

import java.util.Objects;

import space.ipvz.fa.authservice.base.permission.model.Resource;
import space.ipvz.fa.authservice.base.permission.model.Role;
import org.springframework.lang.NonNull;

public final class Permission {
    public final Long groupId;
    public final Resource resource;
    public final Role role;

    Permission(Resource resource, Long groupId, Role role) {
        this.resource = resource;
        this.groupId = groupId;
        this.role = role;
    }

    public static ResourceBuilder builder(Long groupId) {
        return new ResourceBuilder(groupId);
    }

    public static Permission fromString(@NonNull String permission) {
        String[] parts = permission.toUpperCase().split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("permission invalid: " + permission);
        }
        Long groupId = Long.parseLong(parts[0]);
        Resource resource = Resource.valueOf(parts[1]);
        Role role = Role.valueOf(parts[2]);

        return new Permission(resource, groupId, role);
    }

    @Override
    public String toString() {
        return "%s.%s.%s".formatted(groupId, resource, role).toLowerCase();
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
        return resource == that.resource && Objects.equals(groupId, that.groupId) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, groupId, role);
    }

    public boolean hasPermission(Permission o) {
        if (this == o) {
            return true;
        }
        if (!Objects.equals(this.groupId, o.groupId)) {
            return false;
        }

        boolean isRoleGreaterOrEq = this.role.compareTo(o.role) >= 0;

        return isRoleGreaterOrEq && (this.resource == o.resource || this.resource.level < o.resource.level);
    }

    public static class ResourceBuilder {
        private final Long groupId;

        private ResourceBuilder(Long groupId) {
            this.groupId = groupId;
        }

        public RoleBuilder group() {
            return resource(Resource.GROUP);
        }

        public RoleBuilder category() {
            return resource(Resource.CATEGORY);
        }

        public RoleBuilder account() {
            return resource(Resource.ACCOUNT);
        }

        public RoleBuilder operation() {
            return resource(Resource.OPERATION);
        }

        public RoleBuilder resource(Resource resource) {
            return new RoleBuilder(resource, groupId);
        }
    }

    public static class RoleBuilder {
        private final Resource resource;
        private final Long groupId;

        private RoleBuilder(Resource resource, Long groupId) {
            this.resource = resource;
            this.groupId = groupId;
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
            return new Permission(resource, groupId, role);
        }
    }

}
