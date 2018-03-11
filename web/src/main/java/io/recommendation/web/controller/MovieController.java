package io.recommendation.web.controller;

import io.recommendation.common.bean.Comment;
import io.recommendation.common.bean.Favor;
import io.recommendation.common.bean.Movie;
import io.recommendation.common.service.CommentsService;
import io.recommendation.common.service.FavorService;
import io.recommendation.common.service.MovieService;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import io.recommendation.web.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movie")
public class MovieController extends BaseController{

    @Autowired
    private MovieService movieService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private FavorService favorService;

    @RequestMapping("/all")
    @ResponseBody
    public ResponseVo findAllMovie(){
        return ResponseVo.ok(movieService.findAllMovie());
    }

    @RequestMapping("/type")
    @ResponseBody
    public ResponseVo findMovieByType(@RequestParam("type")String type,@RequestParam("page")Integer page,
                                      @RequestParam("size")Integer size){

        if (page < 1 || size < 1){
            return ResponseVo.error(Code.SERVER_ERROR);
        }

        if (type == null || "".equals(type)){
            return ResponseVo.error(Code.PARAM_ILLEGAL);
        }

        if ("all".equals(type)){
            type = null;
        }

        List<Movie> movieList = movieService.findMovieByType(type,(page-1)*size,size);
        return ResponseVo.ok(movieList);
    }

    @RequestMapping("/get")
    @ResponseBody
    public ResponseVo getOneMovie(@RequestParam("id")Long id){

        Movie movie = movieService.findMovieById(id);
        List<Comment> comments = commentsService.findCommentsByMovie(id);
        List<Favor> favors = favorService.findFavorByMovie(id);

        Map<String,Object> result = new HashMap<String, Object>();
        result.put("movie",movie);
        result.put("comment",getComments(comments));
        result.put("favorCount",favors.size());

        return ResponseVo.ok(result);
    }

    @GetMapping("/similar")
    @ResponseBody
    public ResponseVo getSimilarById(@RequestParam("movieId")Long movieId){
        List<Movie> movieList = movieService.findSimilarById(movieId);
        return ResponseVo.ok(movieId);
    }

    private List<CommentVo> getComments(List<Comment> comments){
        List<CommentVo> data = comments.stream().map(comment -> {
          return CommentVo.createVo(comment);
        }).collect(Collectors.toList());

        List<Comment> parentComments = comments.stream().filter(comment -> {
           if (comment.getParentId() == null){
               return true;
           }
           return false;
        }).collect(Collectors.toList());

        List<CommentVo> parentsVo = parentComments.stream().map(comment -> {
            return CommentVo.createVo(comment);
        }).collect(Collectors.toList());

        parentsVo.forEach(parentVo -> {
            List<CommentVo> commentVos = recursiveChildreVo(parentVo,data);
            parentVo.setReplyBody(commentVos);

        });

        return parentsVo;
    }

    private List<CommentVo>  recursiveChildreVo(CommentVo comment,List<CommentVo> comments){
        List<CommentVo> result = comments.stream().filter(commentVo -> {
            if (commentVo.getParentId() == comment.getId()){
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        for (int i=0;i<result.size();i++){
            recursiveChildreVo(result.get(i),comments);
        }

        return result;
    }

}
