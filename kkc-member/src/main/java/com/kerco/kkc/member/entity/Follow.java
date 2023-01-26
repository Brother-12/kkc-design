package com.kerco.kkc.member.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@ToString
@Data
@TableName("member_follow")
@ApiModel(value = "Follow对象", description = "")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关注表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("关注的人的用户id")
    private Long followerId;

    @ApiModelProperty("发起关注的用户id")
    private Long followedId;

    @ApiModelProperty("状态 0:关注 1:不关注")
    private Integer status;

    @TableField(fill= FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
