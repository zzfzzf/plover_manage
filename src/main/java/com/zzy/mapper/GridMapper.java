<<<<<<< HEAD
package com.zzy.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/25.
 */
@Mapper
public interface GridMapper {
    int addGrid(Map gridMap);

    int updateGrid(Map gridMap);

    int deleteGrid(Map gridMap);

    List listGrid(Map gridMap);

    Map getGrid(Map idMap);

    List allGridByType(Map map);

    int countGrid();
    int countGridByUser(Map map);

    List getTopImage();
}
=======
package com.zzy.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/25.
 */
@Mapper
public interface GridMapper {
    int addGrid(Map gridMap);

    int updateGrid(Map gridMap);

    int deleteGrid(Map gridMap);

    List listGrid(Map gridMap);

    Map getGrid(Map idMap);

    List allGridByType(Map map);

    int countGrid();
    int countGridByUser(Map map);

    List getTopImage();
}
>>>>>>> 87862a9414168c7958e3f27eded4d0e8c1646de5
