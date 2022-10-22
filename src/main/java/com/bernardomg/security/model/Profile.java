
package com.bernardomg.security.model;

public interface Profile {

    public Long getId();

    public String getName();

    public Iterable<Permission> getPermissions();

}
