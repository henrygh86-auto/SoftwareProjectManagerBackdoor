package cn.edu.xjtu.stu.orangesoft.backdoor.controller;

import cn.edu.xjtu.stu.orangesoft.backdoor.core.DIUtil;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.Objects;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.Operation;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.ProjectCompletion;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.ResultInfo;
import cn.edu.xjtu.stu.orangesoft.backdoor.service.MilestoneService;
import cn.edu.xjtu.stu.orangesoft.backdoor.service.RBACService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MilestoneController {
    @Autowired
    MilestoneService milestoneService;
    @Autowired
    RBACService rbacService;
    @Autowired
    Gson gson;

    /**
     * 查看小组完成情况
     *
     * @param UserID       用户ID，用于RBAC
     * @param UserPassword 用户密码，用于RBAC
     * @param TeamID       小组ID
     * @return ResultInfo: {
     * "resultInfo": String
     * }
     */
    @PostMapping(value = "/get/completion", produces = "application/json;charset=UTF-8")
    public String GetCompletionByTeamID(@RequestParam("UserID") Integer UserID,
                                        @RequestParam("UserPassword") String UserPassword,
                                        @RequestParam("TeamID") Integer TeamID) {
        Objects objects = DIUtil.getBean(Objects.class);
        Operation operation = DIUtil.getBean(Operation.class);
        objects.setObjectName("completion");
        operation.setOperationDescription("GET");
        if (rbacService.CheckPermission(UserID, UserPassword, objects, operation)) {
            return gson.toJson(milestoneService.GetCompletionByTeamID(TeamID));
        } else {
            ResultInfo resultInfo = DIUtil.getBean(ResultInfo.class);
            resultInfo.setResultInfo("无权访问！！");
            return gson.toJson(resultInfo);
        }
    }

    /**
     * 新建小组完成情况
     *
     * @param UserID       用户ID，用于RBAC
     * @param UserPassword 用户密码，用于RBAC
     * @param MilestoneID  里程碑ID
     * @param FileID       文件ID
     * @return ResultInfo: {
     * "resultInfo": String
     * }
     */
    @PostMapping(value = "/completion", produces = "application/json;charset=UTF-8")
    public String PostCompletion(@RequestParam("UserID") Integer UserID,
                                 @RequestParam("UserPassword") String UserPassword,
                                 @RequestParam("MileStoneID") Integer MilestoneID,
                                 @RequestParam("FileID") Integer FileID) {
        Objects objects = DIUtil.getBean(Objects.class);
        Operation operation = DIUtil.getBean(Operation.class);
        ResultInfo resultInfo;
        objects.setObjectName("completion");
        operation.setOperationDescription("POST");
        if (rbacService.CheckPermission(UserID, UserPassword, objects, operation)) {
            resultInfo = milestoneService.PostCompletion(MilestoneID, FileID, UserID);
        } else {
            resultInfo = DIUtil.getBean(ResultInfo.class);
            resultInfo.setResultInfo("无权访问！！");
        }
        return gson.toJson(resultInfo);
    }

    /**
     * 更改小组项目进度
     *
     * @param UserID       用户ID，用于RBAC
     * @param UserPassword 用户密码，用于RBAC
     * @param CompletionID 进度信息ID
     * @param FileID       进度代表文件ID
     * @param TeamID       小组ID
     * @param MilestoneID  进度代表里程碑ID
     * @return ResultInfo: {
     * * "resultInfo": String
     * * }
     */
    @PutMapping(value = "/completion", produces = "application/json;charset=UTF-8")
    public String PutCompletion(@RequestParam("UserID") Integer UserID,
                                @RequestParam("UserPassword") String UserPassword,
                                @RequestParam("CompletionID") Integer CompletionID,
                                @RequestParam("FileID") int FileID,
                                @RequestParam("TeamID") int TeamID,
                                @RequestParam("MilestoneID") int MilestoneID) {
        Objects objects = DIUtil.getBean(Objects.class);
        Operation operation = DIUtil.getBean(Operation.class);
        objects.setObjectName("completion");
        operation.setOperationDescription("PUT");
        ResultInfo resultInfo;
        if (rbacService.CheckPermission(UserID, UserPassword, objects, operation)) {
            ProjectCompletion projectCompletion = DIUtil.getBean(ProjectCompletion.class);
            projectCompletion.setFileID(FileID);
            projectCompletion.setCompletionID(CompletionID);
            projectCompletion.setTeamID(TeamID);
            projectCompletion.setMilestoneID(MilestoneID);
            resultInfo = milestoneService.PutCompletion(CompletionID, projectCompletion);
        } else {
            resultInfo = DIUtil.getBean(ResultInfo.class);
            resultInfo.setResultInfo("无权访问！！");
        }
        return gson.toJson(resultInfo);
    }

    /**
     * 删除小组完成情况
     *
     * @param UserID       用户ID，用于RBAC
     * @param UserPassword 用户密码，用于RBAC
     * @param CompletionID 完成情况ID
     * @return ResultInfo: {
     * "resultInfo": String
     * }
     */
    @DeleteMapping(value = "/completion", produces = "application/json;charset=UTF-8")
    public String DeleteCompletion(@RequestParam("UserID") Integer UserID,
                                   @RequestParam("UserPassword") String UserPassword,
                                   @RequestParam("CompletionID") Integer CompletionID) {
        Objects objects = DIUtil.getBean(Objects.class);
        Operation operation = DIUtil.getBean(Operation.class);
        objects.setObjectName("completion");
        operation.setOperationDescription("DELETE");
        ResultInfo resultInfo;
        if (rbacService.CheckPermission(UserID, UserPassword, objects, operation)) {
            resultInfo = milestoneService.DeleteCompletion(CompletionID);
        } else {
            resultInfo = DIUtil.getBean(ResultInfo.class);
            resultInfo.setResultInfo("无权访问！！");
        }
        return gson.toJson(resultInfo);
    }
}
