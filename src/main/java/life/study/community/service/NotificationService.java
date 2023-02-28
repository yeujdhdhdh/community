package life.study.community.service;

import life.study.community.dto.NotificationDTO;
import life.study.community.dto.PageDto;
import life.study.community.enums.NotificationStatusEnum;
import life.study.community.enums.NotificationTypeEnum;
import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import life.study.community.mapper.NotificationMapper;
import life.study.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PageDto list(Integer userId, Integer page, Integer size) {
        PageDto<NotificationDTO> pageDto=new PageDto<>();

        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount=notificationMapper.countByExample(example);

        pageDto.setPage(totalCount,page,size);
        if (page<1){
            page=1;
        }
        if (page>totalCount&&totalCount!=0){
            page=totalCount;
        }
        Integer offSizeNum=size*(page-1);

        NotificationExample example1 = new NotificationExample();
        example1.createCriteria().andReceiverEqualTo(userId);
        example1.setOrderByClause("gmt_create desc");
        List<Notification> notifications=notificationMapper.selectByExampleWithRowbounds(example1,new RowBounds(offSizeNum,size));

        if (notifications.size()==0){
            return pageDto;
        }
        List<NotificationDTO> notificationDTOS=new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        pageDto.setData(notificationDTOS);
        return pageDto;
}

    public Integer unreadCount(Integer userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Integer id, User user) {
        NotificationKey notificationKey=new NotificationKey();
        notificationKey.setId(id);
        Notification notification = notificationMapper.selectByPrimaryKey(notificationKey);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }

    public int removeNotification(int id) {
        NotificationKey notificationKey=new NotificationKey();
        notificationKey.setId(id);
        int res=notificationMapper.deleteByPrimaryKey(notificationKey);

        return res;
    }
}
