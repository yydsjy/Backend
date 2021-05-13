package com.example.mall.exception;

import com.example.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleSystemException(Exception ex){
        log.error("System Exception",ex);
        return ApiRestResponse.error(ExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Object handleBusinessException(BusinessException ex){
        log.error("Business Exception",ex);
        return ApiRestResponse.error(ex.getCode(),ex.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethondArgumentNotValidException(MethodArgumentNotValidException ex){
        log.error("MethodArgumentNotValidException: ", ex);
        return handleBindingResult(ex.getBindingResult());
    }

    public ApiRestResponse handleBindingResult(BindingResult result){
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size()==0){
            return ApiRestResponse.error(ExceptionEnum.REQUEST_PARA_ERROR);
        }
        return ApiRestResponse.error(ExceptionEnum.REQUEST_PARA_ERROR.getCode(),list.toString());
    }
}
