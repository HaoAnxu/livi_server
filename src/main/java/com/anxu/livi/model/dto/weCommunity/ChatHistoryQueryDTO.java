package com.anxu.livi.model.dto.weCommunity;
import lombok.Data;
/**
 * 聊天历史记录查询DTO
 *
 * @Author: haoanxu
 * @Date: 2025/11/28 14:11
 */

@Data
public class ChatHistoryQueryDTO {
    // 社区ID
    private Integer communityId;
    // 锚点msgId（首次传0，后续传当前加载的最老的msgId）
    private Integer lastMsgId;
    // 每页条数
    private Integer pageSize;
}