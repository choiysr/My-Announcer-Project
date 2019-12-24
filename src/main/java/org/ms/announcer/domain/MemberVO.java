package org.ms.announcer.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * MemberVO
 */
@Entity
@Table(name = "tbl_Member")
@Data
public class MemberVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mno;
    
    
    private String memberid;

    private String memberpassword;
    private String email;
    private String name;
    private String address;
    
    @CreationTimestamp
    private LocalDateTime regdate;
    
    @UpdateTimestamp
    private LocalDateTime updateddate;


    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "meberid")
    private List<MemberRole> roles;
}