package life.study.community.controller;

import life.study.community.dto.RemoveNotificationDto;
import life.study.community.dto.ResultDto;
import life.study.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RemoveNotificationController {
    @Autowired
    private NotificationService notificationService;
    @ResponseBody

    @RequestMapping(value = "/removeNotification", method = RequestMethod.POST)
    public Object removeNotification(@RequestBody RemoveNotificationDto removeNotificationDto){
        int res=notificationService.removeNotification(removeNotificationDto.getId());
        if (res==1){
            return ResultDto.okOf();
        }else {
            return ResultDto.errorOf(1999,"删除错误");
        }

    }

}
