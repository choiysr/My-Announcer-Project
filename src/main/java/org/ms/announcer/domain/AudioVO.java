package org.ms.announcer.domain;

import javax.persistence.Embeddable;

import lombok.Data;

/**
 * BCBoardDTO
 */

@Data
@Embeddable
public class AudioVO {
    
    private String audioPath;
    private String audioName;
    
    private String alarmBells;
    private String intro;
    private String end;
    
}