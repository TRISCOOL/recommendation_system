package io.recommendation.common.service.impl;

import io.recommendation.common.bean.*;
import io.recommendation.common.mapper.MovieMapper;
import io.recommendation.common.mapper.UserRecommendationItemMapper;
import io.recommendation.common.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private UserRecommendationItemMapper userRecommendationItemMapper;

    @Override
    public List<Movie> findAllMovie() {
        return movieMapper.findAllMovie();
    }

    @Override
    public List<Movie> findMovieByType(String type, Integer offset, Integer length) {
        return movieMapper.findMovieByType(type, offset, length);
    }

    @Override
    public Movie findMovieById(Long id) {
        return movieMapper.findMovieById(id);
    }

    @Override
    public List<Movie> findSimilarById(Long id) {
        return movieMapper.findSimilarById(id);
    }

    @Override
    public Map<String, Integer> analysisForTypeCount() {
        List<AnasisyForType> types = movieMapper.analysisForTypeCount();
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("动作", 0);
        result.put("爱情", 0);
        result.put("喜剧", 0);
        result.put("恐怖", 0);

        for (AnasisyForType anasisyForType : types){
            String type = anasisyForType.getType();
            switch (type) {
                case "action":
                    result.put("动作", anasisyForType.getCount());
                    break;
                case "love":
                    result.put("爱情", anasisyForType.getCount());
                    break;
                case "comedy":
                    result.put("喜剧", anasisyForType.getCount());
                    break;
                case "terror":
                    result.put("恐怖", anasisyForType.getCount());
                    break;
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> analysisForSexCount() {
        List<AnasisyForSex> anasisyForSexes = movieMapper.analysisForSexCount();
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("男", 0);
        result.put("女", 0);

        for (AnasisyForSex anasisyForSex:anasisyForSexes){
            String type = anasisyForSex.getSex();
            switch (type) {
                case "female":
                    result.put("女", anasisyForSex.getCount());
                    break;
                case "male":
                    result.put("男", anasisyForSex.getCount());
                    break;
            }
        }

        return result;
    }

    @Override
    public List<Movie> getRecommendationsForUser(Long userId) {

        UserRecommendationItem userRecommendationItem = userRecommendationItemMapper.selectByUserId(userId.toString());
        if (userRecommendationItem == null) return null;

        String recommendaions = userRecommendationItem.getRecommendations();
        List<Long> moviesId = getMovieIds(recommendaions);
        if (moviesId == null) return null;

        return movieMapper.selectMoviesByIds(moviesId);
    }

    @Override
    public Map<String, List<Integer>> analysisForTX() {
        List<WatchRecord> watchRecords = movieMapper.findWatchRecord();
        List<Integer> femaleCount = getCountBySex(watchRecords,"female");
        List<Integer> maleCount = getCountBySex(watchRecords,"male");

        List<Integer> firstCount = getCountByAge(watchRecords,0,18);
        List<Integer> secondCount = getCountByAge(watchRecords,18,23);
        List<Integer> thirdCount = getCountByAge(watchRecords,24,29);
        List<Integer> frouthCount = getCountByAge(watchRecords,30,35);
        List<Integer> fiveCount = getCountByAge(watchRecords,36,100);

        Map<String,List<Integer>> result = new HashMap<String, List<Integer>>();
        result.put("femaleCount",femaleCount);
        result.put("maleCount",maleCount);
        result.put("firstCount",firstCount);
        result.put("secondCount",secondCount);
        result.put("thirdCount",thirdCount);
        result.put("frouthCount",frouthCount);
        result.put("fiveCount",fiveCount);

        return result;
    }

    private List<Integer> getCountByAge(List<WatchRecord> watchRecords,Integer minAge,Integer maxAge){
        Integer actionCount = 0;
        Integer loveCount = 0;
        Integer terrorCount = 0;
        Integer comedyCount = 0;

        List<Integer> counts = new LinkedList<Integer>();
        for (WatchRecord watchRecord : watchRecords){
            if ("action".equals(watchRecord.getType()) && isHave(watchRecord.getAge(),minAge,maxAge)){
                actionCount ++;
            }else if ("love".equals(watchRecord.getType()) && isHave(watchRecord.getAge(),minAge,maxAge)){
                loveCount ++;
            }else if ("terror".equals(watchRecord.getType()) && isHave(watchRecord.getAge(),minAge,maxAge)){
                terrorCount ++;
            }else if ("comedy".equals(watchRecord.getType()) && isHave(watchRecord.getAge(),minAge,maxAge)){
                comedyCount ++;
            }
        }

        counts.add(actionCount);
        counts.add(loveCount);
        counts.add(terrorCount);
        counts.add(comedyCount);

        return counts;
    }

    private boolean isHave(Integer age,Integer minAge,Integer maxAge){
        if (age<maxAge && age >= minAge){
            return true;
        }
        return false;
    }

    private List<Integer> getCountBySex(List<WatchRecord> watchRecords, String sex){
        Integer actionCount = 0;
        Integer loveCount = 0;
        Integer terrorCount = 0;
        Integer comedyCount = 0;

        List<Integer> counts = new LinkedList<Integer>();

        for (WatchRecord watchRecord : watchRecords){
            if ("action".equals(watchRecord.getType()) && sex.equals(watchRecord.getSex())){
                actionCount ++;
            }else if ("love".equals(watchRecord.getType()) && sex.equals(watchRecord.getSex())){
                loveCount ++;
            }else if ("terror".equals(watchRecord.getType()) && sex.equals(watchRecord.getSex())){
                terrorCount ++;
            }else if ("comedy".equals(watchRecord.getType()) && sex.equals(watchRecord.getSex())){
                comedyCount ++;
            }
        }
        counts.add(actionCount);
        counts.add(loveCount);
        counts.add(terrorCount);
        counts.add(comedyCount);

        return counts;
    }

    private List<Long> getMovieIds(String recommendations) {
        if (recommendations == null || "".equals(recommendations)) return null;

        List<Long> result = new ArrayList<Long>();
        String s1 = recommendations.substring(1, recommendations.lastIndexOf("]"));
        String[] strings = s1.split("],");
        for (String s : strings) {
            String numbers = s.replace("[", "").replace("]", "");
            String[] s2 = numbers.split(",");
            result.add(Long.parseLong(s2[0]));
        }

        return result;
    }
}
