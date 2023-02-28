package life.study.community.service;

import life.study.community.exceptiopn.CustomizeErrorCode;
import life.study.community.exceptiopn.CustomizeException;
import life.study.community.mapper.UserMapper;
import life.study.community.model.User;
import life.study.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user,HttpServletRequest request) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size()==0){
            //create
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //update
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setImgUrl(user.getImgUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setBio(user.getBio());
            updateUser.setUsertype(user.getUsertype());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
            UserExample userExample1=new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
            List<User> users1 = userMapper.selectByExample(userExample1);
            request.getSession().setAttribute("user",users1.get(0));
        }
    }
    public int userCreateOrUpdate(User user) {

        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        UserExample userExample1=new UserExample();
        userExample1.createCriteria().andNameEqualTo(user.getName());
        List<User> users1=userMapper.selectByExample(userExample1);
        if (users1.size()!=0){
            return 0;
        }else {
            if (users.size() == 0) {
                //create

                user.setToken(UUID.randomUUID().toString());
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
                return 1;
            } else {
                //update
                User dbUser = users.get(0);
                User updateUser = new User();
                updateUser.setGmtModified(System.currentTimeMillis());
                updateUser.setImgUrl(user.getImgUrl());
                updateUser.setName(user.getName());
                updateUser.setToken(user.getToken());
                updateUser.setBio(user.getBio());
                user.setUsertype(user.getUsertype());
                UserExample example = new UserExample();
                example.createCriteria().andIdEqualTo(dbUser.getId());
                userMapper.updateByExampleSelective(updateUser, example);
                return 1;
            }
        }

    }
    public Integer userLoginVerify(String username,String password,HttpServletRequest request){
        UserExample userExample=new UserExample();
        userExample.createCriteria().andNameEqualTo(username);
        List<User> users=userMapper.selectByExample(userExample);
        User user1=users.get(0);
        if (password.equals(user1.getPassword())){
            request.getSession().setAttribute("user",user1);
            return 1;
        }else {
            return 0;
        }

    }

}
