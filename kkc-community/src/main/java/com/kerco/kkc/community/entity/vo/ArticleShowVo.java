package com.kerco.kkc.community.entity.vo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ArticleShowVo extends CurrencyShowVo{

    private String firstImg;

    private Integer official;

    private Integer top;

    private Integer essence;
}
