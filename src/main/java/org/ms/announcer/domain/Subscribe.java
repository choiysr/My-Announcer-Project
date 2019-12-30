package org.ms.announcer.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tbl_subscribe")
@Data
public class Subscribe {


    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private MemberVO member;
    private MemberVO cp;

    
}