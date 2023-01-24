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
@TableName("community_article_content")
@ApiModel(value = "ArticleContent对象", description = "")
public class ArticleContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章内容-markdown")
    private String mdContent;

    @ApiModelProperty("文章内容-html")
    private String htmlContent;

    @ApiModelProperty("文章内容-只保留文字")
    private String parseContent;

    @ApiModelProperty("文章的第一张图片")
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
        return "ArticleContent{" +
            "id=" + id +
            ", mdContent=" + mdContent +
            ", htmlContent=" + htmlContent +
            ", parseContent=" + parseContent +
            ", firstImg=" + firstImg +
        "}";
    }
}
