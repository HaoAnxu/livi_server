package com.anxu.livi.service;

import com.anxu.livi.model.dto.wePost.PostCommentDTO;
import com.anxu.livi.model.dto.wePost.PostInfoDTO;
import com.anxu.livi.model.vo.wePost.PostCommentVO;
import com.anxu.livi.model.vo.wePost.PostInfoVO;

import java.util.List;

/**
 * WePost相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:44
 */
public interface WePostService {
    // 查询帖子列表
    List<PostInfoVO> listWePost(Integer page, Integer pageSize);

    // 查询帖子详情
    PostInfoVO detailWePost(Integer postId);

    // 查询帖子评论列表
    List<PostCommentVO> listWePostComment(Integer postId, Integer page, Integer pageSize);

    // 创建帖子
    boolean saveWePost(PostInfoDTO postInfoDTO);

    // 查询用户帖子列表
    List<PostInfoVO> listWePostByUserId(Integer userId, Integer page, Integer pageSize);

    // 发评论
    boolean saveWePostComment(PostCommentDTO postCommentDTO);

    // 删除帖子根据postId
    boolean deleteWePost(Integer postId);

    // 删除评论根据commentId
    boolean deleteWePostComment(Integer commentId);
}
