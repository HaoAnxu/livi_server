package com.anxu.livi_module_user.controller;

import com.anxu.livi_model.model.result.Result;
import com.anxu.livi_model.model.vo.user.UserInfoVO;
import com.anxu.livi_model.model.vo.weCommunity.CommunityInfoVO;
import com.anxu.livi_module_user.service.WeCommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【WeCommunity-相关接口】
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 13:27
 */
@RestController
@Slf4j
public class WeCommunityController {

    @Autowired
    private WeCommunityService weCommunityService;

    @GetMapping("/permission/isLogin")
    public Result isLogin() {
        log.info("验证登录状态-已经登陆-拦截验证通过");
        return Result.success();
    }

    //获取社区列表
    @GetMapping("/permission/wecommunity/getCommunityList")
    public Result getWeCommunity() {
        log.info("获取所有社区列表");
        List<CommunityInfoVO> allCommunityList = weCommunityService.getAllCommunityList();
        return Result.success(allCommunityList);
    }
    //查询单个社区详情
    @GetMapping("/permission/wecommunity/getCommunityDetail")
    public Result getCommunityDetail(@RequestParam("communityId") Integer communityId) {
        log.info("查询社区详情：{}", communityId);
        CommunityInfoVO communityInfoVO = weCommunityService.getCommunityDetail(communityId);
        return Result.success(communityInfoVO);
    }
    //查询社区成员信息列表
    @GetMapping("/permission/wecommunity/getCommunityMemberList")
    public Result getCommunityAllMembers(@RequestParam("communityId") Integer communityId) {
        log.info("查询社区所有成员：{}", communityId);
        List<UserInfoVO> allMembers = weCommunityService.getCommunityAllMembersInfo(communityId);
        return Result.success(allMembers);
    }

    //查询用户是否加入了该社区
    @PostMapping("/permission/wecommunity/isJoinCommunity")
    public Result isJoinCommunity(@RequestParam("communityId") Integer communityId,
                                  @RequestParam("userId") Integer userId) {
        boolean isJoin = weCommunityService.isJoinCommunity(communityId, userId);
        log.info("查询用户是否加入了该社区：{}", isJoin);
        return Result.success(isJoin);
    }

    //用户加入社区
    @PostMapping("/permission/wecommunity/joinCommunity")
    public Result joinCommunity(@RequestParam("communityId") Integer communityId,
                                @RequestParam("userId") Integer userId) {
        log.info("用户{}加入社区：{}", userId, communityId);
        weCommunityService.joinCommunity(communityId, userId);
        return Result.success();
    }

    //用户退出社区
    @DeleteMapping("/permission/wecommunity/exitCommunity")
    public Result exitCommunity(@RequestParam("communityId") Integer communityId,
                                @RequestParam("userId") Integer userId) {
        log.info("用户退出社区");
        weCommunityService.exitCommunity(communityId, userId);
        return Result.success();
    }
}
