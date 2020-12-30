package cn.edu.xjtu.stu.orangesoft.backdoor.service;

import cn.edu.xjtu.stu.orangesoft.backdoor.core.DIUtil;
import cn.edu.xjtu.stu.orangesoft.backdoor.mapper.MilestoneMapper;
import cn.edu.xjtu.stu.orangesoft.backdoor.mapper.StudentMapper;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.FindMilestoneResult;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.ProjectCompletion;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.ResultInfo;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {
    @Autowired
    MilestoneMapper milestonemapper;

    @Autowired
    StudentMapper studentmapper;

    public FindMilestoneResult GetCompletionByTeamID(Integer teamID) {
        FindMilestoneResult result = DIUtil.getBean(FindMilestoneResult.class);
        List<ProjectCompletion> completions = milestonemapper.GetCompletionByTeamID(teamID);
        result.setFinish("成功！！");
        result.setProjectCompletion(completions);
        return result;
    }

    public ResultInfo PostCompletion(Integer milestoneID, Integer fileID, Integer userID) {
        ProjectCompletion completion = DIUtil.getBean(ProjectCompletion.class);
        completion.setCompletionID(0);
        completion.setMilestoneID(milestoneID);
        completion.setFileID(fileID);
        Student student = studentmapper.GetStudentDataByUserID(userID);
        completion.setTeamID(student.getTeamID());
        milestonemapper.InsertCompletion(completion);
        ResultInfo resultInfo = DIUtil.getBean(ResultInfo.class);
        if (completion.getCompletionID() != 0) {
            resultInfo.setResultInfo("成功！！");
        } else {
            resultInfo.setResultInfo("失败！！");
        }
        return resultInfo;
    }

    public ResultInfo PutCompletion(Integer CompletionID, ProjectCompletion completion) {
        milestonemapper.updateCompletion(CompletionID, completion);
        ResultInfo resultInfo = DIUtil.getBean(ResultInfo.class);
        if (completion.getCompletionID() == CompletionID) {
            resultInfo.setResultInfo("成功！！");
        } else {
            resultInfo.setResultInfo("失败！！");
        }
        return resultInfo;
    }

    public ResultInfo DeleteCompletion(Integer CompletionID) {
        milestonemapper.DeleteCompletion(CompletionID);
        ResultInfo resultInfo = DIUtil.getBean(ResultInfo.class);
        if (milestonemapper.GetCompletionByID(CompletionID) == null) {
            resultInfo.setResultInfo("成功！！");
        } else {
            resultInfo.setResultInfo("失败！！");
        }
        return resultInfo;
    }


}
