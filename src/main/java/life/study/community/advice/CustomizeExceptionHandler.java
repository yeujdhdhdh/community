package life.study.community.advice;

import com.alibaba.fastjson.JSON;
import life.study.community.dto.ResultDto;
import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
//
//
//@ControllerAdvice
//public class CustomizeExceptionHandler
//{
//    @ExceptionHandler(Exception.class)
//
//    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response){
//
//        String contentType = request.getContentType();
//        if ("application/json".equals(contentType)) {
//            ResultDto resultDTO;
//            // 返回 JSON
//            if (ex instanceof CustomizeException) {
//                resultDTO = ResultDto.errorOf((CustomizeException) ex);
//            } else {
//
//                resultDTO = ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
//            }
//            try {
//                response.setContentType("application/json");
//                response.setStatus(200);
//                response.setCharacterEncoding("utf-8");
//                PrintWriter writer = response.getWriter();
//                writer.write(JSON.toJSONString(resultDTO));
//                writer.close();
//            } catch (IOException ioe) {
//            }
//            return null;
//        }else {
//            if (ex instanceof CustomizeException){
//                model.addAttribute("message",ex.getMessage());
//
//            }else {
//                model.addAttribute("message","骚后在世");
//            }
//
//            return new ModelAndView("error");
//            }
//    }
//
//}
