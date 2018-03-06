package io.recommendation.common.mapper;

import io.recommendation.common.bean.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentsMapper {
    int insertComments(Comment comment);

    int insertRelationship(@Param("userId")Long userId,@Param("movieId")Long movieId,@Param("commentId")Long commentId);

    List<Comment> findCommentsByMovie(@Param("movieId")Long movieId);
}
