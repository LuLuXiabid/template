//package xiao.li.com.base.dto;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import java.io.Serializable;
//
///**
// * @author shuai.zhang
// * @projectName device-center
// * @Description
// * @createTime 2020-06-19 16:42
// */
//@Data
//@ApiModel(value = "统一响应返回数据结构")
//public class ApiResult<T> implements Serializable{
//
//
//    /** 状态码 */
//    @ApiModelProperty(value = "业务返回码 成功为0", required = true, example = "0")
//    private Integer code;
//    /** 具体响应消息内容 */
//    @ApiModelProperty(value = "业务返消息，由具体业务而定", required = true, example = "success")
//    private String msg;
//    /** 具体响应数据 */
//    @ApiModelProperty(value = "具体响应数据", required = true)
//    private T data;
//
//}