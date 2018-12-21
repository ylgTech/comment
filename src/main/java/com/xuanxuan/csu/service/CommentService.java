package com.xuanxuan.csu.service;

import com.xuanxuan.csu.dto.CommentDTO;
import com.xuanxuan.csu.vo.CommentVO;
import com.xuanxuan.csu.model.Comment;
import com.xuanxuan.csu.core.Service;

import java.util.List;


/**
 * Created by PualrDwade on 2018/12/03.
 */
public interface CommentService extends Service<Comment> {


    /**
     * 根据评论id得到评论信息以及所有的复回复信息详情
     *
     * @param commentId 评论id
     */
    public CommentVO getCommentDetail(String commentId);


    /**
     * 增加一条新的评论
     *
     * @param commentDTO 评论数据出传输对象
     */

    public void addNewComment(CommentDTO commentDTO);


    /**
     * 更新一条评论信息
     *
     * @param commentDTO
     */
    public void updateComment(CommentDTO commentDTO, String commentId);


    /**
     * 删除一条评论
     *
     * @param commentId
     */
    public void deleteComment(String commentId);


}
