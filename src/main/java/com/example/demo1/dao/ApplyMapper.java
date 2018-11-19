package com.example.demo1.dao;

import com.example.demo1.entity.WXUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ApplyMapper {

    @Select("select * from wx_wd_user where open_id = #{open_id}")
    public WXUser selectUserByOpenId(Object open_id);

    @Update("update wx_wd_user set username=#{username}, phonenum=#{phonenum} where open_id = #{open_id}")
    public void updateWXUser(WXUser user);
}
