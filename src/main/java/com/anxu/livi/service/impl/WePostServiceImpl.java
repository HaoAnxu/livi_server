package com.anxu.livi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.livi.mapper.wePost.PostCircleMapper;
import com.anxu.livi.mapper.wePost.PostCommentMapper;
import com.anxu.livi.mapper.wePost.PostInfoMapper;
import com.anxu.livi.model.dto.wePost.PostCommentDTO;
import com.anxu.livi.model.dto.wePost.PostInfoDTO;
import com.anxu.livi.model.entity.wePost.PostCommentEntity;
import com.anxu.livi.model.entity.wePost.PostInfoEntity;
import com.anxu.livi.model.vo.wePost.PostCommentVO;
import com.anxu.livi.model.vo.wePost.PostInfoVO;
import com.anxu.livi.service.WePostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【WePost相关服务实现类】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:45
 */
@Slf4j
@Service
public class WePostServiceImpl implements WePostService {
    @Autowired
    private PostInfoMapper postInfoMapper;
    @Autowired
    private PostCircleMapper postCircleMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;

    // 分页查询帖子列表
    @Override
    public List<PostInfoVO> listWePost(Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            page = 1;
            pageSize = 10;
        }
        Page<PostInfoEntity> pagePostInfoEntity = new Page<>(page, pageSize);
        // 分页查询帖子列表
        QueryWrapper<PostInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        // 分页查询帖子列表
        postInfoMapper.selectPage(pagePostInfoEntity, queryWrapper);
        return BeanUtil.copyToList(pagePostInfoEntity.getRecords(), PostInfoVO.class);
    }

    // 查询帖子详情
    @Override
    public PostInfoVO detailWePost(Integer postId) {
        // 查询帖子详情
        PostInfoEntity postInfoEntity = postInfoMapper.selectById(postId);
        return BeanUtil.copyProperties(postInfoEntity, PostInfoVO.class);
    }

    // 分页查询帖子评论列表
    @Override
    public List<PostCommentVO> listWePostComment(Integer postId, Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            page = 1;
            pageSize = 10;
        }
        Page<PostCommentEntity> pagePostCommentEntity = new Page<>(page, pageSize);
        // 分页查询帖子评论列表
        QueryWrapper<PostCommentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        queryWrapper.orderByDesc("create_time");
        // 分页查询帖子评论列表
        postCommentMapper.selectPage(pagePostCommentEntity, queryWrapper);
        return BeanUtil.copyToList(pagePostCommentEntity.getRecords(), PostCommentVO.class);
    }

    // 创建新帖子
    @Override
    public boolean saveWePost(PostInfoDTO postInfoDTO) {
        // 转换为实体类
        PostInfoEntity postInfoEntity = BeanUtil.copyProperties(postInfoDTO, PostInfoEntity.class);
        // 保存帖子
        return postInfoMapper.insert(postInfoEntity) > 0;
    }

    // 查询用户帖子列表
    @Override
    public List<PostInfoVO> listWePostByUserId(Integer userId, Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            page = 1;
            pageSize = 10;
        }
        Page<PostInfoEntity> pagePostInfoEntity = new Page<>(page, pageSize);
        // 分页查询用户帖子列表
        QueryWrapper<PostInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        // 分页查询用户帖子列表
        postInfoMapper.selectPage(pagePostInfoEntity, queryWrapper);
        return BeanUtil.copyToList(pagePostInfoEntity.getRecords(), PostInfoVO.class);
    }

    // 发评论
    @Override
    public boolean saveWePostComment(PostCommentDTO postCommentDTO) {
        //先查询帖子是否存在
        PostInfoEntity postInfoEntity = postInfoMapper.selectById(postCommentDTO.getPostId());
        if (postInfoEntity == null) {
            log.info("帖子不存在, postId: {}", postCommentDTO.getPostId());
            return false;
        }
        // 转换为实体类
        PostCommentEntity postCommentEntity = BeanUtil.copyProperties(postCommentDTO, PostCommentEntity.class);
        // 保存评论
        return postCommentMapper.insert(postCommentEntity) > 0;
    }

    // 删除帖子根据postId
    @Override
    public boolean deleteWePost(Integer postId) {
        // 先查询帖子是否存在
        PostInfoEntity postInfoEntity = postInfoMapper.selectById(postId);
        if (postInfoEntity == null) {
            log.info("删除操作：帖子不存在, postId: {}", postId);
            return false;
        }
        // 删除帖子
        return postInfoMapper.deleteById(postId) > 0;
    }

    // 删除评论根据commentId
    @Override
    public boolean deleteWePostComment(Integer commentId) {
        // 先查询评论是否存在
        PostCommentEntity postCommentEntity = postCommentMapper.selectById(commentId);
        if (postCommentEntity == null) {
            log.info("删除操作：评论不存在, commentId: {}", commentId);
            return false;
        }
        // 删除评论
        return postCommentMapper.deleteById(commentId) > 0;
    }
}
