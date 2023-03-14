package ml.ipvz.fa.authservice.base.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ml.ipvz.fa.authservice.base.permission.Permission;

public record User(
        Long id,
        String login,
        List<String> roles
) {
    @JsonIgnore
    public List<Permission> getPermissions() {
        return roles().stream().map(Permission::fromString).toList();
    }
}
