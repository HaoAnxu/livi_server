package com.anxu.livi.controller;

import com.anxu.livi.model.dto.wePost.PostCommentDTO;
import com.anxu.livi.model.dto.wePost.PostInfoDTO;
import com.anxu.livi.model.result.Result;
import com.anxu.livi.model.vo.wePost.PostCommentVO;
import com.anxu.livi.model.vo.wePost.PostInfoVO;
import com.anxu.livi.service.WePostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 查询帖子列表
    @PostMapping("/wePost/queryPostList")
    public Result listWePost(@RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer pageSize) {
        log.info("查询帖子列表");
        List<PostInfoVO> postList = wePostService.listWePost(page, pageSize);
        return Result.success(postList);
    }

    // 查询帖子根据userId
    @PostMapping("/wePost/queryPostListByUserId")
    public Result listWePostByUserId(@RequestParam Integer userId,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer pageSize) {
        log.info("查询用户帖子列表, userId: {}, page: {}, pageSize: {}", userId, page, pageSize);
        List<PostInfoVO> postList = wePostService.listWePostByUserId(userId, page, pageSize);
        return Result.success(postList);
    }

    // 查询帖子详情
    @PostMapping("/wePost/queryPostDetail")
    public Result detailWePost(@RequestParam Integer postId) {
        log.info("查询帖子详情, postId: {}", postId);
        PostInfoVO postInfoVO = wePostService.detailWePost(postId);
        return Result.success(postInfoVO);
    }

    // 分页查询帖子评论列表
    @PostMapping("/wePost/comment/queryCommentList")
    public Result listWePostComment(@RequestParam Integer postId,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer pageSize) {
        log.info("分页查询帖子评论列表, postId: {}, page: {}, pageSize: {}", postId, page, pageSize);
        List<PostCommentVO> postCommentVOList = wePostService.listWePostComment(postId, page, pageSize);
        return Result.success(postCommentVOList);
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
}