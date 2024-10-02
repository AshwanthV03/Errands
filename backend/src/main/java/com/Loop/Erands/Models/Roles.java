package com.Loop.Erands.Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.Loop.Erands.Models.Permission.*;

@RequiredArgsConstructor
public enum Roles {
    USER(Collections.emptySet()),
    SELLER(
            Set.of(
                    SELLER_CREATE,
                    SELLER_DELETE,
                    SELLER_UPDATE,
                    SELLER_READ

            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE,

                    SELLER_CREATE,
                    SELLER_DELETE,
                    SELLER_UPDATE,
                    SELLER_READ

            )
    );

    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        System.out.println(permissions);

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
