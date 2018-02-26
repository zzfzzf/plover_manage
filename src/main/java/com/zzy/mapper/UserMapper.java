<<<<<<< HEAD
package com.zzy.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */
@Mapper
public interface UserMapper {


    Map getUser(Map userMap);

    Map getByUsername(Map userMap);

    int updateUser(Map userMap);

    int updateUserByEmail(Map userMap);



    int register(Map userMap);
}
=======
package com.zzy.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */
@Mapper
public interface UserMapper {


    Map getUser(Map userMap);

    Map getByUsername(Map userMap);

    int updateUser(Map userMap);

    int updateUserByEmail(Map userMap);



    int register(Map userMap);
}
>>>>>>> 87862a9414168c7958e3f27eded4d0e8c1646de5
