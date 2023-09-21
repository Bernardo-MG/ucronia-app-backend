
package com.bernardomg.association.membership.member.model.request;

import com.bernardomg.association.membership.member.model.MemberStatus;

public interface MemberQuery {

    public Long getId();

    public String getIdentifier();

    public String getName();

    public String getPhone();

    public MemberStatus getStatus();

    public String getSurname();

}
