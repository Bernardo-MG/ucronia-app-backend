
package com.bernardomg.association.library.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.member.domain.model.Member;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Lending {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Member    member;

}
