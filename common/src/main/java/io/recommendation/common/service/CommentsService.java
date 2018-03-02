package io.recommendation.common.service;

import io.recommendation.common.bean.Comment;

import java.util.List;

public interface CommentsService {

    boolean insertComments(Comment comment);

    List<Comment> findCommentsByMovie(Long movieId);
}
