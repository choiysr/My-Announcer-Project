package org.ms.announcer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * BCBoardDTO
 */

@Entity
@Table(name = "tbl_bcboard")
@Data
public class BCBoardDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    // notnull (javax) 줘야할까? 
    private String title;
    private String content;
    // MemberDTO만들면 MEMBER로 수정해야함. 
    private String mid;
    private String audioPath;
    private String audioName;
    private String gender;
    private String alarmBell;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime starttime;

    @CreationTimestamp
    private LocalDateTime regdate;

    @UpdateTimestamp
    private LocalDateTime updateddate;

}