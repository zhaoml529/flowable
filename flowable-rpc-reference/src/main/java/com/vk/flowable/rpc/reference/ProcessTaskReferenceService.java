package com.vk.flowable.rpc.reference;

import com.vk.flowable.mgt.rpc.api.dto.ProcessTaskDTO;
import com.vk.flowable.mgt.rpc.api.request.CompleteTaskRpcParam;
import com.vk.flowable.mgt.rpc.api.response.ApiResponse;
import com.vk.flowable.mgt.rpc.api.service.ProcessTaskRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 流程任务
 * Created by zml on 2019/9/2.
 */
@Component
@Slf4j
public class ProcessTaskReferenceService {

    @Autowired
    private ProcessTaskRpcService processTaskRpcService;

    /**
     * 待办任务列表
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    public List<ProcessTaskDTO> findTodoTask(Long userId, Integer limit, Integer offset) {
        try {
            ApiResponse<List<ProcessTaskDTO>> apiResponse = processTaskRpcService.findTodoTask(userId, limit, offset);
            if (apiResponse.isSuccess()) {
                return apiResponse.getData();
            }
        } catch (Exception e) {
            log.error("查询用户待办任务异常，userId:{} limit:{}, offset:{}", userId, limit, offset, e);
        }
        return Collections.emptyList();
    }

    /**
     * 签收任务
     * @param userId
     * @param taskId
     * @return
     */
    public Boolean claimTask(Long userId, String taskId) {
        try {
            ApiResponse apiResponse = processTaskRpcService.claimTask(userId, taskId);
            if (apiResponse.isSuccess()) {
                apiResponse.getData();
            }
        } catch (Exception e) {
            log.error("签收任务异常，userId:{}, taskId:{}", userId, taskId, e);
        }
        return false;
    }

    /**
     * 委派任务
     * @param userId
     * @param taskId
     * @return
     */
    public Boolean delegateTask(Long userId, String taskId) {
        try {
            ApiResponse apiResponse = processTaskRpcService.delegateTask(userId, taskId);
            if (apiResponse.isSuccess()) {
                apiResponse.getData();
            }
        } catch (Exception e) {
            log.error("委派任务异常，userId:{}, taskId:{}", userId, taskId, e);
        }
        return false;
    }

    /**
     * 转办任务
     * @param userId
     * @param taskId
     * @return
     */
    public Boolean transferTask(Long userId, String taskId) {
        try {
            ApiResponse apiResponse = processTaskRpcService.transferTask(userId, taskId);
            if (apiResponse.isSuccess()) {
                apiResponse.getData();
            }
        } catch (Exception e) {
            log.error("转办任务异常，userId:{}, taskId:{}", userId, taskId, e);
        }
        return false;
    }

    /**
     * 完成任务
     * @param completeTaskRpcParam
     * @return
     */
    public Boolean completeTask(CompleteTaskRpcParam completeTaskRpcParam) {
        try {
            ApiResponse apiResponse = processTaskRpcService.completeTask(completeTaskRpcParam);
            if (apiResponse.isSuccess()) {
                apiResponse.getData();
            }
        } catch (Exception e) {
            log.error("转办任务异常，completeTaskRpcParam:{}", completeTaskRpcParam, e);
        }
        return false;
    }


}
