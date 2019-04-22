package com.ladybird.hkd.controller;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.exception.TokenException;
import com.ladybird.hkd.model.json.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shen
 * @description: baseController
 * @create: 2019-03-13
 */
@Controller
public class BaseController {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object handleException(Exception e) {

        if (e.getClass() == ParamException.class) {
            e.printStackTrace();
            return ResultJson.ParameterException(e.getMessage(), null);
        }
        if (e.getClass() == TokenException.class) {
            e.printStackTrace();
            return ResultJson.TokenRedisException();
        }
        if (e.getClass() == BusinessException.class) {
            e.printStackTrace();
            return ResultJson.BusinessErrorException(e.getMessage(),null);
        }
        e.printStackTrace();
        return ResultJson.ServerException("出错了，请联系管理员！");
    }

}
