package life.study.community.mapper;

import life.study.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,GMT_MODIFIED) value (#{user.name},#{user.accountId},#{user.token},#{user.gmtCreate},#{user.gmtModified})")
    void insert(@Param("user") User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}