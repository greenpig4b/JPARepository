package shop.mtcoding.blog._core.errors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import shop.mtcoding.blog._core.errors.exception.Exception400;

@Aspect // AOP 등록
@Component // IOC 등록
public class MyVaildationHandler {

    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void hi(JoinPoint jp){
        Object[] args =jp.getArgs(); //파라메터(매게변수)

        for (Object arg : args){
            if (arg instanceof Errors){
             Errors errors =(Errors) arg;

             if (errors.hasErrors()){
                 for (FieldError error : errors.getFieldErrors()){
                     throw new Exception400(error.getDefaultMessage() + " : " + error.getField());
                 }
             }
            }
        }
    }
}
