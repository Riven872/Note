package com.edu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Json返回结果类型
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    //返回状态码
    private Integer code;

    //返回信息
    private String message;

    //返回数据
    private T data;

    //无data返回值的构造方法
    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
