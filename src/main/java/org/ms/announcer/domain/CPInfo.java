package org.ms.announcer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import groovy.transform.ToString;
import lombok.Data;

@Entity
@Table(name = "tbl_cpinfo")
@Data
@ToString(excludes = "member")
public class CPInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    
    private String title;
    private String introduce;
    private String imgFile;
    
    public CPInfo(){

        imgFile="default.png";
        title = "속이 거북이의 아침방송";
        introduce = "안녕하세요. 김만기입니다";
    }

    @OneToOne
    @JsonIgnore
    private MemberVO member;
    
}