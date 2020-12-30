package cn.edu.xjtu.stu.orangesoft.backdoor.mapper;

import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.GroupAssess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GroupAssessMapper {
    GroupAssess GetGroupScoreByTeamID(
            @Param("TeamID") Integer TeamID);

    Integer BuildNewGroupAssess(@Param("groupAssess") GroupAssess groupAssess);
}
