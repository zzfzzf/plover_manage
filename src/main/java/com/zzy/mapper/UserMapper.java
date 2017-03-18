package com.zzy.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */
@Mapper
public interface UserMapper {
    int addLiveUser(Map userMap);
}
