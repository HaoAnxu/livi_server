package com.anxu.livi.model.vo.weCommunity;

import com.anxu.livi.model.dto.weCommunity.ChatInfoDetailDTO;
import lombok.Data;

import java.util.List;

/**
 * 聊天历史记录返回VO
 *
 * @Author: haoanxu
 * @Date: 2025/11/28 14:14
 */
@Data
public class ChatHistoryVO {
    // 历史记录列表（按msgId降序，最新的在前）
    private List<ChatInfoDetailDTO> historyList;
    // 是否还有更多数据（true=还有，false=已加载完所有）
    private Boolean hasMore;
    // 当前加载的最老的msgId（供下一次查询用）
    private Integer currentLastMsgId;
}
