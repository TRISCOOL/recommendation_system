package io.recommendation.web.controller;

import io.recommendation.common.bean.Movie;
import io.recommendation.common.service.MovieService;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController extends BaseController{

    @Autowired
    private MovieService movieService;

    @RequestMapping("/all")
    @ResponseBody
    public ResponseVo findAllMovie(){
        return ResponseVo.ok(movieService.findAllMovie());
    }

    @RequestMapping("/type")
    @ResponseBody
    public ResponseVo findMovieByType(@RequestParam("type")String type){
        if (type == null || "".equals(type)){
            return ResponseVo.error(Code.PARAM_ILLEGAL);
        }

        if ("all".equals(type)){
            type = null;
        }

        List<Movie> movieList = movieService.findMovieByType(type);
        return ResponseVo.ok(movieList);
    }

    @RequestMapping("/get")
    @ResponseBody
    public ResponseVo getOneMovie(@RequestParam("id")Long id){
        return ResponseVo.ok(movieService.findMovieById(id));
    }
}
