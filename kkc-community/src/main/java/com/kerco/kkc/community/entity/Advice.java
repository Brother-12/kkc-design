package com.kerco.kkc.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("community_advice")
@ApiModel(value = "Advice对象", description = "")
public class Advice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("建议表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("发起人id")
    private Long clientId;

    @ApiModelProperty("发起人姓名")
    private String clientName;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Advice{" +
            "id=" + id +
            ", clientId=" + clientId +
            ", clientName=" + clientName +
            ", content=" + content +
            ", createTime=" + createTime +
        "}";
    }
}
