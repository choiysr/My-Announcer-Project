package org.ms.announcer.domain;

import javax.persistence.Embeddable;

import lombok.Data;

/**
 * RepeatVO
 */

@Data
@Embeddable
public class RepeatVO {
    private String repeatWeek;
    private String repeatMonth;
    
}