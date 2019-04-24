package com.ladybird.hkd.controller;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.ElseIf;
import com.ladybird.hkd.exception.AuthorizationException;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ExcelImportException;
import com.ladybird.hkd.exception.NopaperException;
import com.ladybird.hkd.exception.NullException;
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


    @ExceptionHandler({ParamException.class,BusinessException.class,ExcelImportException.class,NullException.class,NopaperException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object badRequest(Exception e) {

        if (e.getClass() == ParamException.class) {
            e.printStackTrace();
            return ResultJson.ParameterException(e.getMessage(), null);
        }else if (e.getClass() == BusinessException.class) {
            e.printStackTrace();
            return ResultJson.BusinessErrorException(e.getMessage(),null);
        }else if(e.getClass() == NullException.class){
            e.printStackTrace();
            return ResultJson.BusinessErrorException(e.getMessage(),null);
        }else if(e.getClass() == NopaperException.class){
            e.printStackTrace();
            return ResultJson.BusinessErrorException(e.getMessage(), null);
        }
        
        else{
            e.printStackTrace();
            return ResultJson.BusinessErrorException(e.getLocalizedMessage(), null);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public Object fo(Exception e) {
        if (e.getClass() == TokenException.class) {
            e.printStackTrace();
            return ResultJson.TokenRedisException();
        }else if(e.getClass() == AuthorizationException.class){
            e.printStackTrace();
            return ResultJson.Forbidden(e.getMessage());
        }else{
            e.printStackTrace();
            return ResultJson.ServerException();
        }
    }


}
