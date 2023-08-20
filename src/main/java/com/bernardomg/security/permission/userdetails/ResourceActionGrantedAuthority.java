
package com.bernardomg.security.permission.userdetails;

import org.springframework.security.core.GrantedAuthority;

public final class ResourceActionGrantedAuthority implements GrantedAuthority {

    /**
     *
     */
    private static final long serialVersionUID = 2121524436657408632L;

    private final String      action;

    private final String      resource;

    public ResourceActionGrantedAuthority(final String resource, final String action) {
        super();

        this.resource = resource;
        this.action = action;
    }

    
    public  final String getAction() {
        return action;
    }

    
    public  final String getResource() {
        return resource;
    }

    @Override
    public final String getAuthority() {
        return toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s:%s", resource, action);
    }

}
