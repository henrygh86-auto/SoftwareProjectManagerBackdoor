package cn.edu.xjtu.stu.orangesoft.backdoor.service;

import cn.edu.xjtu.stu.orangesoft.backdoor.core.DIUtil;
import cn.edu.xjtu.stu.orangesoft.backdoor.mapper.RoleMapper;
import cn.edu.xjtu.stu.orangesoft.backdoor.mapper.StudentMapper;
import cn.edu.xjtu.stu.orangesoft.backdoor.mapper.TeamMapper;
import cn.edu.xjtu.stu.orangesoft.backdoor.mapper.UserMapper;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    RoleMapper rolemapper;
    @Autowired
    UserMapper usermapper;
    @Autowired
    TeamMapper teammapper;
    @Autowired
    StudentMapper studentmapper;

    /**
     * LoginService的主要方法，输入UserID、UserPassword，返回user、role、team相关的信息
     *
     * @param UserID       UserID
     * @param UserPassword UserPassword
     * @return LoginResult: {
     * "Finish": String,
     * "LoginType": String,
     * "TeamID": Integer,
     * "TeamName": String,
     * "RoleName": String
     * }
     */
    public LoginResult login(Integer UserID, String UserPassword) {
        User user;
        Role role;
        Team team = null;

        LoginResult loginResult = DIUtil.getBean(LoginResult.class);
        user = usermapper.GetUserByIDAndPassword(UserID, null);
        if (user == null) {
            loginResult.setFinish("user not found");//找不到用户
        } else {
            //若找到了用户
            if (UserPassword.compareTo(user.getUserPassword()) != 0) {
                //判断密码是否正确
                loginResult.setFinish("wrong password");
            } else {
                role = rolemapper.GetRoleByID(user.getRoleID());
                Student student = studentmapper.GetStudentDataByUserID(user.getUserID());
                if (student != null) {
                    team = teammapper.GetTeamByTeamID(student.getTeamID());
                }
                if (team != null) {
                    loginResult.setTeamID(team.getTeamID());
                    loginResult.setTeamName(team.getTeamName());
                }
                if (role != null) {
                    loginResult.setRoleName(role.getRoleName());
                }
                loginResult.setFinish("success");
            }

        }

        return loginResult;
    }
}
