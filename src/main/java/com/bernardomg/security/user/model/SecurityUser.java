
package com.bernardomg.security.user.model;

import com.bernardomg.security.profile.model.SecurityProfile;

public interface SecurityUser {

    public Boolean getEnabled();

    public Long getId();

    public Boolean getLocked();

    public Long getMemberId();

    public Iterable<SecurityProfile> getProfiles();

    public String getUsername();

}
