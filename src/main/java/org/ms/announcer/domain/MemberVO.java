package org.ms.announcer.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import groovy.transform.ToString;
import lombok.Data;

/**
 * MemberVO
 */
@Entity
@Table(name = "tbl_Member")
@Data
@ToString(excludes = "cpInfo")
public class MemberVO {

    @Id
    private String id;

    private String memberpassword;
    private String email;
    private String name;
    private String address;
    private String type;

    
    @CreationTimestamp
    private LocalDateTime regdate;
    
    @UpdateTimestamp
    private LocalDateTime updateddate;


    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "role")
    private List<MemberRole> roles;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "cpinfo")
    // @JsonIgnore
    private CPInfo cpInfo;

    // @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    // private List<Subscribe> subscribes;
}