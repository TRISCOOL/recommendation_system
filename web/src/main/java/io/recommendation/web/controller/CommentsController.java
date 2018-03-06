package io.recommendation.web.controller;

import io.recommendation.common.bean.Comment;
import io.recommendation.common.bean.User;
import io.recommendation.common.service.CommentsService;
import io.recommendation.common.util.Util;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import io.recommendation.web.annotations.AuthRequire;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentsController extends BaseController{

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/insert")
    @ResponseBody
    @AuthRequire
    public ResponseVo insertComment(@ModelAttribute  Comment comment, HttpServletRequest request){
        User user = getUserByAuthRequire(request);
        if (user == null){
            return ResponseVo.error(Code.NOT_FOUND_USER);
        }

        comment.setCreateTime(Util.formatDate(new Date()));
        comment.setUserId(user.getId());
        comment.setDelete(0);

        boolean result = commentsService.insertComments(comment);

        if (result){
            return ResponseVo.ok();
        }

        return ResponseVo.error(Code.SERVER_ERROR);
    }


    @RequestMapping("/get_by_movie")
    public ResponseVo findForMovie(@RequestParam("movieId")Long movieId){
        return ResponseVo.ok(commentsService.findCommentsByMovie(movieId));
    }
}
