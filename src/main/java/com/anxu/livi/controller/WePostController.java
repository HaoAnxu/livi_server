package com.anxu.livi.controller;
import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.dto.wePost.PageDTO;
import com.anxu.livi.model.dto.wePost.PostCommentDTO;
import com.anxu.livi.model.dto.wePost.PostInfoDTO;
import com.anxu.livi.model.result.Result;
import com.anxu.livi.model.vo.wePost.*;
import com.anxu.livi.service.WePostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 【WePost-相关接口】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:42
 */
@Slf4j
@RestController
public class WePostController {

    @Autowired
    private WePostService wePostService;

    //查询圈子列表-随机4个
    @PostMapping("/wePost/queryCircleList")
    private Result listWePostCircle() {
        log.info("查询圈子列表，随机");
        List<PostCircleVO> postList = wePostService.listWePostCircle();
        return Result.success(postList);
    }

    //查询圈子列表-根据userId
    @PostMapping("/wePost/queryCircleListByUserId")
    private Result listWePostCircleByUserId(@RequestBody PageDTO pageDTO) {
        log.info("查询圈子列表-根据userId, userId: {}, page: {}, pageSize: {}", pageDTO.getId(), pageDTO.getPage(), pageDTO.getPageSize());
        PageResult pageResult = wePostService.listWePostCircleByUserId(pageDTO);
        return Result.success(pageResult);
    }

    //查询圈子详情
    @PostMapping("/wePost/queryCircleDetail")
    public Result detailWePostCircle(@RequestParam Integer circleId) {
        log.info("查询圈子详情, circleId: {}", circleId);
        PostCircleVO postCircleVO = wePostService.detailWePostCircle(circleId);
        return Result.success(postCircleVO);
    }

    // 查询帖子列表
    @PostMapping("/wePost/queryPostList")
    public Result listWePost(@RequestBody PageDTO pageDTO) {
        log.info("查询帖子列表");
        List<PostInfoVO> postList = wePostService.listWePost(pageDTO);
        return Result.success(postList);
    }

    // 查询帖子根据userId
    @PostMapping("/wePost/queryPostListByUserId")
    public Result listWePostByUserId(@RequestBody PageDTO pageDTO) {
        log.info("查询用户帖子列表, userId: {}, page: {}, pageSize: {}", pageDTO.getId(), pageDTO.getPage(), pageDTO.getPageSize());
        List<PostInfoVO> postList = wePostService.listWePostByUserId(pageDTO);
        return Result.success(postList);
    }

    // 查询帖子根据circleId
    @PostMapping("/wePost/queryPostListByCircleId")
    public Result listWePostByCircleId(@RequestBody PageDTO pageDTO) {
        log.info("查询圈子帖子列表, circleId: {}, page: {}, pageSize: {}", pageDTO.getId(), pageDTO.getPage(), pageDTO.getPageSize());
        List<PostInfoVO> postList = wePostService.listWePostByCircleId(pageDTO);
        return Result.success(postList);
    }

    // 查询帖子详情
    @PostMapping("/wePost/queryPostDetail")
    public Result detailWePost(@RequestParam Integer postId) {
        log.info("查询帖子详情, postId: {}", postId);
        PostInfoVO postInfoVO = wePostService.detailWePost(postId);
        return Result.success(postInfoVO);
    }

    // 分页查询帖子根评论列表
    @PostMapping("/wePost/comment/queryCommentList")
    public Result listWePostComment(@RequestBody PageDTO pageDTO) {
        log.info("分页查询帖子评论列表, postId: {}, page: {}, pageSize: {}", pageDTO.getId(), pageDTO.getPage(), pageDTO.getPageSize());
        PageResult pageResult = wePostService.listWePostComment(pageDTO);
        return Result.success(pageResult);
    }

    //分页查询根评论的子评论列表，根据commentId
    @PostMapping("/wePost/comment/queryReplyCommentList")
    public Result listWePostReplyComment(@RequestBody PageDTO pageDTO) {
        log.info("分页查询根评论的子评论列表, commentId: {}, page: {}, pageSize: {}", pageDTO.getId(), pageDTO.getPage(), pageDTO.getPageSize());
        PageResult pageResult = wePostService.listWePostReplyComment(pageDTO);
        return Result.success(pageResult);
    }

    //批量查询根评论的回复数量
    @PostMapping("/wePost/comment/queryReplyCount")
    public Result queryReplyCount(@RequestBody List<Integer> commentIds) {
        log.info("批量查询根评论的回复数量, commentIds: {}", commentIds);
        Map<Integer,Integer> replyCountMap = wePostService.queryReplyCount(commentIds);
        return Result.success(replyCountMap);
    }

    // 发帖子
    @PostMapping("/permission/wePost/createPost")
    public Result saveWePost(@RequestBody PostInfoDTO postInfoDTO) {
        log.info("发帖子, postInfoDTO: {}", postInfoDTO);
        boolean result = wePostService.saveWePost(postInfoDTO);
        if (result) {
            return Result.success("发送成功");
        }
        return Result.error("发送失败");
    }

    // 发评论
    @PostMapping("/permission/wePost/createComment")
    public Result saveWePostComment(@RequestBody PostCommentDTO postCommentDTO) {
        log.info("发评论, postCommentDTO: {}", postCommentDTO);
        boolean result = wePostService.saveWePostComment(postCommentDTO);
        if (result) {
            return Result.success("发送成功");
        }
        return Result.error("发送失败, 未知原因");
    }

    // 回复评论
    @PostMapping("/permission/wePost/replyComment")
    public Result replyWePostComment(@RequestBody PostCommentDTO postCommentDTO) {
        log.info("回复评论, postCommentDTO: {}", postCommentDTO);
        boolean result = wePostService.replyWePostComment(postCommentDTO);
        if (result) {
            return Result.success("回复成功");
        }
        return Result.error("回复失败, 未知原因");
    }

    // 删帖子根据postId
    @PostMapping("/permission/wePost/deletePostByPostId")
    public Result deleteWePost(@RequestParam Integer postId) {
        log.info("删帖子, postId: {}", postId);
        boolean result = wePostService.deleteWePost(postId);
        if (result) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    // 删评论根据commentId
    @PostMapping("/permission/wePost/deleteCommentByCommentId")
    public Result deleteWePostComment(@RequestParam Integer commentId) {
        log.info("删评论, commentId: {}", commentId);
        boolean result = wePostService.deleteWePostComment(commentId);
        if (result) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    // 查询3张轮播图片
    @PostMapping("/wePost/queryCarouselImageList")
    public Result listCarousel() {
        log.info("查询轮播图片");
        List<PostCarouselVO> postList = wePostService.listCarousel();
        return Result.success(postList);
    }

    // 查询最新5条热点
    @PostMapping("/wePost/queryHotNewsList")
    public Result listHotNews() {
        log.info("查询最新5条热点");
        List<PostInfoVO> postList = wePostService.listHotNews();
        return Result.success(postList);
    }
}