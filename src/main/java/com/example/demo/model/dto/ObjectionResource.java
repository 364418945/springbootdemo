package com.example.demo.model.dto;

import com.example.demo.model.ParamCheck;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
@Data
public class ObjectionResource{
    @ParamCheck(method = "updateObjection", notNull = true)
    String objectionId;//异议id
    @ParamCheck(method = "verifyDemo",notNull=true,  valueIn = {"1","2","3"})
    Integer objectionType;//异议异议类型（编高，偏低，其他）
    @ParamCheck(method = "updateObjection", notNull = true)
    @ParamCheck(method = "verifyDemo", notNull = true)
    String suitCode;//房产码
    @ParamCheck(method = "verifyDemo",notNull=true, valueIn = {"1","2"})
    String accessflag;//出售，出租
    @ParamCheck(method = "updateObjection", notNull = true)
    @ParamCheck(method = "verifyDemo", notNull = true)
    String logId;//评估码（reportid）
    @ParamCheck(method = "verifyDemo", notNullDepend = "objectionType", notNullDependOn = {"1", "2"})
    Float expectPrice;//预期价格
    @ParamCheck(method = "verifyDemo", notNullDepend = "objectionType", notNullDependOn = {"3"}, length = 500)
    String note;//异议说明
    String author;//异议用户id
    String confirmUid;
    Date confirmTime;//处理时间
//    @ParamCheck(method = "verifyDemo", notNullDepend = "status", notNullDependOn = {"confirmed"})
    @ParamCheck(method = "updateObjection", notNullDepend = "confirmflag", notNullDependOn = {"1"})
    String confirmNote;//处理意见
    String readUid;
    Date readTime;//阅读时间
//    @ParamCheck(method = "verifyDemo", notNull = "new")
    String status;//异议状态
    Float dealPrice;
    Date dealTime;
    @JsonIgnore
    String apiKey;
    @JsonIgnore
    @ParamCheck(method = "updateObjection",notNullBoth ={"readflag","confirmflag"},valueIn = {"1"},length = 2)
    Integer readflag;//已读，未读
    @JsonIgnore
    String cityCode;//城市code
    @JsonIgnore
    String distCode;//行政区code
    String suitName;//小区名称
    @JsonIgnore
    String addTimeBegin;//添加时间开始
    @JsonIgnore
    String addTimeEnd;//添加时间结束
    @JsonIgnore
    String dealTimeBegin;//处理时间开始
    @JsonIgnore
    String dealTimeEnd;//处理时间结束
    @JsonIgnore
    @ParamCheck(method = "updateObjection",notNullBoth ={"readflag","confirmflag"},valueIn = {"1"})
    Integer confirmflag;//处理标识（1：已处理，2：未处理）

    Double evalPrice;

    Double evalUnitPrice;

    Date evalTime;
    @JsonIgnore
    Date addTime;


}
