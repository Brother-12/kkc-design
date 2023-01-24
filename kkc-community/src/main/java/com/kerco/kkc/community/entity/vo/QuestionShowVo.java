package com.kerco.kkc.community.entity.vo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class QuestionShowVo extends CurrencyShowVo{

    //回答数
    private Integer commentCount;


}
