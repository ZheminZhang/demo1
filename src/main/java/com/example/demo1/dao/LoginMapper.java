package com.example.demo1.dao;

import com.example.demo1.entity.User;
import com.example.demo1.entity.WXUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LoginMapper {

    @Select("select id, phone_num, open_id from wx_wd_user order by sort desc")
    public WXUser getWXuser();

    @Select("select * from wx_wd_user where open_id = #{open_id}")
    public WXUser selectUserByOpenId(Object open_id);

    @Insert("insert into wx_wd_user(open_id) values (#{open_id})")
    public void addWXUser(WXUser user);
}
