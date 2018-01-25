package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Gril implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    @Min(value = 20, message = "20是最小")//设置验证
    private Integer age;

    private String sex;

    private Integer cupSize;

    private String hobby;
}
