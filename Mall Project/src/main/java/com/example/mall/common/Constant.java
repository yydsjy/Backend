package com.example.mall.common;

import com.example.mall.exception.BusinessException;
import com.example.mall.exception.ExceptionEnum;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Constant {
    public static final String SALT = "1234";
    public static final String MALL_USER = "mall_user";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_AS_DESC = Sets.newHashSet("price desc","price asc");
    }

    public interface SaleStatus{
        int NOT_SALE = 0;
        int SALE = 1;
    }

    public interface Cart{
        int UNSELECTED = 0;
        int SELECTED = 1;
    }

    public enum OrderStatusEnum{
        CANCELED(0,"User Canceled"),
        UNPAID(10,"Not Paid"),
        PAID(20,"Paid"),
        DELIVERED(30,"Delivered"),
        FINISHED(40,"Finished");


        private Integer code;
        private String value;

        OrderStatusEnum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static OrderStatusEnum valueOf(Integer code){
            for (OrderStatusEnum status:values()){
                if (status.getCode()==code){
                    return status;
                }
            }
            throw new BusinessException(ExceptionEnum.UNFOUND_ENUM);
        }
    }
}
