package io.recommendation.common.service.impl;

import io.recommendation.common.bean.Comment;
import io.recommendation.common.mapper.CommentsMapper;
import io.recommendation.common.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentsService{

    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    @Transactional
    public boolean insertComments(Comment comment) {

        int result = commentsMapper.insertComments(comment);
        if (result == 1){
            Long commentId = comment.getId();
            int result2 = commentsMapper.insertRelationship(comment.getUserId(),comment.getMovieId(),commentId);
            if (result2 == 1){
                return true;
            }

        }

        return false;
    }

    @Override
    public List<Comment> findCommentsByMovie(Long movieId) {
        return commentsMapper.findCommentsByMovie(movieId);
    }
}
