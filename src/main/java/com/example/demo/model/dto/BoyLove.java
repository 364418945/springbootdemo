package com.example.demo.model.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class BoyLove {
    @GeneratedValue
    @Id
    private Integer id;

    private String name;

    private Date createTime;



}
