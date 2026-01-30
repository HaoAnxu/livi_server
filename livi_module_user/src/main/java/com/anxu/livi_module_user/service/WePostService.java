package com.anxu.livi_module_user.service;

import com.anxu.livi_model.model.result.PageResult;
import com.anxu.livi_model.model.dto.wePost.PageDTO;
import com.anxu.livi_model.model.dto.wePost.PostCommentDTO;
import com.anxu.livi_model.model.dto.wePost.PostInfoDTO;
import com.anxu.livi_model.model.vo.wePost.PostCarouselVO;
import com.anxu.livi_model.model.vo.wePost.PostCircleVO;
import com.anxu.livi_model.model.vo.wePost.PostInfoVO;

import java.util.List;
import java.util.Map;

/**
 * WePost相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:44
 */
public interface WePostService {

    // 查询帖子列表
    List<PostInfoVO> listWePost(PageDTO pageDTO);

    // 查询帖子详情
    PostInfoVO detailWePost(Integer postId);

    // 查询帖子评论列表
    PageResult listWePostComment(PageDTO pageDTO);

    // 查询根评论的子评论列表
    PageResult listWePostReplyComment(PageDTO pageDTO);

    // 查询评论下的子评论数量
    Map<Integer,Integer> queryReplyCount(List<Integer> commentIds);

    // 创建帖子
    boolean saveWePost(PostInfoDTO postInfoDTO);

    // 查询用户帖子列表
    List<PostInfoVO> listWePostByUserId(PageDTO pageDTO);

    // 发评论
    boolean saveWePostComment(PostCommentDTO postCommentDTO);

    // 回复评论
    boolean replyWePostComment(PostCommentDTO postCommentDTO);

    // 删除帖子根据postId
    boolean deleteWePost(Integer postId);

    // 删除评论根据commentId
    boolean deleteWePostComment(Integer commentId);

    // 查询圈子列表
    List<PostCircleVO> listWePostCircle();

    // 查询圈子详情
    PostCircleVO detailWePostCircle(Integer circleId);

    // 查询3张轮播图片
    List<PostCarouselVO> listCarousel();

    // 查询最新5条热点
    List<PostInfoVO> listHotNews();

    // 查询用户加入的圈子列表
    PageResult listWePostCircleByUserId(PageDTO pageDTO);

    // 根据circleId查询帖子列表
    List<PostInfoVO> listWePostByCircleId(PageDTO pageDTO);
}
