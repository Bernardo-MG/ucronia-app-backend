
package com.bernardomg.association.fee.model.mapper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.fee.model.ImmutableMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.model.PersistentMemberFee;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    public default MemberFee toDto(final PersistentFee entity) {
        final Calendar date;

        if (entity.getDate() != null) {
            date = removeDay(entity.getDate());
        } else {
            date = null;
        }

        return ImmutableMemberFee.builder()
            .id(entity.getId())
            .memberId(entity.getMemberId())
            .date(date)
            .paid(entity.getPaid())
            .build();
    }

    public default MemberFee toDto(final PersistentMemberFee entity) {
        final Calendar date;

        if (entity.getDate() != null) {
            date = removeDay(entity.getDate());
        } else {
            date = null;
        }

        return ImmutableMemberFee.builder()
            .id(entity.getId())
            .memberId(entity.getMemberId())
            .date(date)
            .paid(entity.getPaid())
            .name(entity.getName())
            .surname(entity.getSurname())
            .build();
    }

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeCreate request);

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeUpdate request);

    default Calendar removeDay(final Calendar calendar) {
        final Integer year;
        final Integer month;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        return new GregorianCalendar(year, month, 1);
    }

}
