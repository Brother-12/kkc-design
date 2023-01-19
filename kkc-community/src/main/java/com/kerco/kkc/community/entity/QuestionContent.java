package com.kerco.kkc.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@TableName("community_question_content")
@ApiModel(value = "QuestionContent对象", description = "")
public class QuestionContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("问答id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("问答内容-markdown")
    private String mdContent;

    @ApiModelProperty("问答内容-html")
    private String htmlContent;

    @ApiModelProperty("问答内容-只保留文字")
    private String parseContent;

    @ApiModelProperty("问答的第一张图片")
    private String firstImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMdContent() {
        return mdContent;
    }

    public void setMdContent(String mdContent) {
        this.mdContent = mdContent;
    }
    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
    public String getParseContent() {
        return parseContent;
    }

    public void setParseContent(String parseContent) {
        this.parseContent = parseContent;
    }
    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    @Override
    public String toString() {
        return "QuestionContent{" +
            "id=" + id +
            ", mdContent=" + mdContent +
            ", htmlContent=" + htmlContent +
            ", parseContent=" + parseContent +
            ", firstImg=" + firstImg +
        "}";
    }
}
