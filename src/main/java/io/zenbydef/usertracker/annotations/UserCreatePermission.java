package io.zenbydef.usertracker.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(value = "hasAuthority('user.create')")
public @interface UserCreatePermission {
}
