package ml.ipvz.fa.authservice.base.model;

import java.util.List;

import ml.ipvz.fa.authservice.base.permission.Permission;

public record User(
        Long id,
        String login,
        List<String> roles
) {
    public List<Permission> getPermissions() {
        return roles().stream().map(Permission::fromString).toList();
    }
}
