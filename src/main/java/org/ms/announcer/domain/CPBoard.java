package org.ms.announcer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * CPBoard
 */
@Entity
@Table(name = "tbl_cpboard")
@Data
public class CPBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberVO member;

	private String title;
	private String file_path;
	private String file_name;

    private LocalDate bcdate;
    
    @CreationTimestamp
    private LocalDateTime regdate;
    @UpdateTimestamp
    private LocalDateTime updateddate;
}