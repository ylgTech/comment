package com.xuanxuan.csu.util.convertorImp;


import com.xuanxuan.csu.configurer.AppConfigurer;
import com.xuanxuan.csu.dao.ReplyMapper;
import com.xuanxuan.csu.dao.UserInfoMapper;
import com.xuanxuan.csu.model.Comment;
import com.xuanxuan.csu.model.Reply;
import com.xuanxuan.csu.model.UserInfo;
import com.xuanxuan.csu.util.VoConvertor;
import com.xuanxuan.csu.vo.CommentVO;
import com.xuanxuan.csu.vo.ReplyVO;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 评论数据属性的转换器
 */
@Component
public class CommentVoConvertor implements VoConvertor<Comment, CommentVO> {

    @Resource
    ReplyMapper replyMapper;

    @Resource
    UserInfoMapper userInfoMapper;


    @Override
    public CommentVO conver2Vo(Comment comment) {
        CommentVO commentVO = new CommentVO(comment);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(comment.getFromUid());
        commentVO.setAvatar(userInfo.getAvatarUrl());
        commentVO.setUsername(userInfo.getNickName());
        Condition condition = new Condition(comment.getClass());
        //构造回复条件
        condition.createCriteria().andCondition("comment_id=", comment.getId());
        //得到最新的回复
        condition.orderBy("create_time").desc();
        List<Reply> replyList = replyMapper.selectByCondition(condition);
        //评论没有回复,则构造必要信息直接返回
        if (replyList == null || replyList.size() == 0) {
            commentVO.setReplyNum(0);
            commentVO.setReplyList(new ArrayList<>());
            return commentVO;
        }
        //项目配置，固定评论数量
        replyList = replyList.subList(0, Math.min(AppConfigurer.COMMENT_REPLAY_NUMBER, replyList.size()));
        List<ReplyVO> replyVOList = new ArrayList<>();
        //回复不为空,构造回复内容,使用App配置中的常量
        for (Reply reply : replyList) {
            ReplyVO replyVO = new ReplyVO(reply);
            UserInfo rUserInfo = userInfoMapper.selectByPrimaryKey(reply.getFromUid());
            replyVO.setFromUname(rUserInfo.getAvatarUrl());
            String toUid = replyMapper.selectByPrimaryKey(reply.getId()).getFromUid();
            replyVO.setToUid(toUid);
            replyVO.setToUname(userInfoMapper.selectByPrimaryKey(toUid).getNickName());
            replyVOList.add(replyVO);
        }
        commentVO.setReplyList(replyVOList);
        commentVO.setReplyNum(replyList.size());
        return commentVO;
    }
}
