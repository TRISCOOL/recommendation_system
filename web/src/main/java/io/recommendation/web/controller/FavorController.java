package io.recommendation.web.controller;

import io.recommendation.common.bean.Favor;
import io.recommendation.common.bean.User;
import io.recommendation.common.service.FavorService;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import io.recommendation.web.annotations.AuthRequire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/favor")
public class FavorController extends BaseController{

    @Autowired
    private FavorService favorService;

    @PostMapping("/insert")
    @ResponseBody
    @AuthRequire
    public ResponseVo insertFavor(@RequestParam("movieId")Long movieId, HttpServletRequest request){

        User user = getUserByAuthRequire(request);
        if (user == null){
            return ResponseVo.error(Code.NOT_FOUND_USER);
        }

        Favor favor = favorService.findFavorByUserAndMovie(movieId,user.getId());
        if (favor != null){
            return ResponseVo.error(Code.HAVED_FAVOR);
        }

        Favor favor1 = new Favor();
        favor1.setMovieId(movieId);
        favor1.setUserId(user.getId());

        boolean result = favorService.insertOneFavor(favor1);
        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(Code.SERVER_ERROR);
    }

    @PostMapping("/delete")
    @ResponseBody
    @AuthRequire
    public ResponseVo deleteFavor(@RequestParam("movieId")Long movieId,HttpServletRequest request){
        User user = getUserByAuthRequire(request);
        if (user == null){
            return ResponseVo.error(Code.NOT_FOUND_USER);
        }

        boolean result = favorService.delete(movieId,user.getId());
        if (result){
            return ResponseVo.ok();
        }
        return ResponseVo.error(Code.SERVER_ERROR);
    }

}
