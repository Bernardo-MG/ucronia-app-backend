
package com.bernardomg.association.member.model;

public interface MemberDetail {

    public Long getId();

    public Iterable<MemberMonth> getMonths();

    public String getName();

    public Iterable<MemberPeriod> getPeriods();

}
