
package com.bernardomg.association.model;

import lombok.Data;

@Data
public final class DtoMonthRequest implements MonthRequest {

    private Integer month;

    private Integer year;

}
