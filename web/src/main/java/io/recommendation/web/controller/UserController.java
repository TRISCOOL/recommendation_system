package io.recommendation.web.controller;

import io.recommendation.common.bean.User;
import io.recommendation.common.service.UserService;
import io.recommendation.common.service.impl.RedisService;
import io.recommendation.common.util.Util;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseVo login(@RequestParam("account") String account, @RequestParam("password") String password) {


        User user = userService.findUserByAccount(account);
        if (user == null){
            return ResponseVo.error(Code.NOT_FOUND_USER);
        }

        String password1 = user.getPassword();
        if (!password.equals(password1)){
            return ResponseVo.error(Code.PASSWORD_ERROR);
        }


        String uuid = Util.getUuid();
        redisService.setStr(uuid,Util.objectToJson(user));

        user.setToken(uuid);

        return ResponseVo.ok(user);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseVo register(@ModelAttribute User user){
        if (user.getAccount() == null || user.getPassword() == null){
            return ResponseVo.error(Code.PARAM_ILLEGAL);
        }

        if (user.getSex() != null){
            if (user.getSex().equals("男")){
                user.setSex("male");
            }else {
                user.setSex("female");
            }
        }

        boolean b = userService.register(user);
        if (b){
            return ResponseVo.ok();
        }

        return ResponseVo.error(Code.SERVER_ERROR);

    }

}
