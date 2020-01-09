package org.ms.announcer.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

/**
 * LoginHistory
 */
@Entity
@Table(name = "tbl_LoginHistory")
@Data
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Pno;

    private LocalDate loginDate;

    private String memberId;
    
}