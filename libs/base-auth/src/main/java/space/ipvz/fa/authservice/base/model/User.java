package space.ipvz.fa.authservice.base.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import space.ipvz.fa.authservice.base.permission.Permission;

public record User(
        Long id,
        String login,
        List<String> roles
) {
    @JsonIgnore
    public List<Permission> getPermissions() {
        return roles().stream().map(Permission::fromString).toList();
    }

    public User withPermissions(List<Permission> permissions) {
        return new User(this.id, this.login, permissions.stream().map(Permission::toString).toList());
    }
}
