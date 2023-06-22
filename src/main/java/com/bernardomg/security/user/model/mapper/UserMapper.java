
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.model.ImmutableUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.model.PersistentUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public default User toDto(final PersistentUser entity) {
        return ImmutableUser.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .name(entity.getName())
            .email(entity.getEmail())
            .credentialsExpired(entity.getCredentialsExpired())
            .enabled(entity.getEnabled())
            .expired(entity.getExpired())
            .locked(entity.getLocked())
            .build();
    }

    public default PersistentUser toEntity(final UserCreate data) {
        final String username;
        final String email;

        if (data.getUsername() != null) {
            username = data.getUsername()
                .toLowerCase();
        } else {
            username = null;
        }
        if (data.getEmail() != null) {
            email = data.getEmail()
                .toLowerCase();
        } else {
            email = null;
        }

        return PersistentUser.builder()
            .username(username)
            .email(email)
            .name(data.getName())
            .credentialsExpired(data.getCredentialsExpired())
            .enabled(data.getEnabled())
            .expired(data.getExpired())
            .locked(data.getLocked())
            .build();
    }

    public default PersistentUser toEntity(final UserQuery data) {
        final String username;
        final String email;

        if (data.getUsername() != null) {
            username = data.getUsername()
                .toLowerCase();
        } else {
            username = null;
        }
        if (data.getEmail() != null) {
            email = data.getEmail()
                .toLowerCase();
        } else {
            email = null;
        }

        return PersistentUser.builder()
            .username(username)
            .email(email)
            .name(data.getName())
            .credentialsExpired(data.getCredentialsExpired())
            .enabled(data.getEnabled())
            .expired(data.getExpired())
            .locked(data.getLocked())
            .build();
    }

    public default PersistentUser toEntity(final UserUpdate data) {
        final String username;
        final String email;

        if (data.getUsername() != null) {
            username = data.getUsername()
                .toLowerCase();
        } else {
            username = null;
        }
        if (data.getEmail() != null) {
            email = data.getEmail()
                .toLowerCase();
        } else {
            email = null;
        }

        return PersistentUser.builder()
            .id(data.getId())
            .username(username)
            .email(email)
            .name(data.getName())
            .credentialsExpired(data.getCredentialsExpired())
            .enabled(data.getEnabled())
            .expired(data.getExpired())
            .locked(data.getLocked())
            .build();
    }

}
