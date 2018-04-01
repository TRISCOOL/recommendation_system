package io.recommendation.web.controller;

import io.recommendation.common.service.MovieService;
import io.recommendation.common.vo.ResponseVo;
import io.recommendation.web.vo.AnalysisPieChartVo;
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

@Controller
@RequestMapping("/analysis")
public class AnalysisController extends BaseController{

    @Autowired
    private MovieService movieService;

    @SuppressWarnings("Duplicates")
    @GetMapping("/get_pie")
    @ResponseBody
    public Map<String,List<AnalysisPieChartVo>> getAnalysisForType(){

        List<AnalysisPieChartVo> list1 = new ArrayList<AnalysisPieChartVo>();
        List<AnalysisPieChartVo> list2 = new ArrayList<AnalysisPieChartVo>();

        Map<String,Integer> mapAction = movieService.analysisForTypeCount();
        Map<String,Integer> mapSex = movieService.analysisForSexCount();


        for (Map.Entry<String,Integer> entry : mapAction.entrySet()){
            AnalysisPieChartVo analysisPieChartVo = new AnalysisPieChartVo();
            analysisPieChartVo.setName(entry.getKey());
            analysisPieChartVo.setValue(entry.getValue());
            list1.add(analysisPieChartVo);
        }

        for (Map.Entry<String,Integer> entry : mapSex.entrySet()){
            AnalysisPieChartVo analysisPieChartVo = new AnalysisPieChartVo();
            analysisPieChartVo.setName(entry.getKey());
            analysisPieChartVo.setValue(entry.getValue());
            list2.add(analysisPieChartVo);
        }

        Map<String,List<AnalysisPieChartVo>> result = new HashMap<String, List<AnalysisPieChartVo>>();
        result.put("list1",list1);
        result.put("list2",list2);


        return result;
    }

    @GetMapping("/tx")
    @ResponseBody
    public Map<String,List<Integer>> getTX(){
        return movieService.analysisForTX();
    }

}
