package com.vk.flowable.rpc.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

import java.util.UUID;

@Slf4j
public class TransferLogFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            String logId = MDC.get("logId");
            logId = logId == null ? UUID.randomUUID().toString() : logId;
            RpcContext.getContext().setAttachment("logId", logId);
        } catch (Exception e) {
            log.warn("transfer print info error:{}", e.getMessage());
        }
        long begin = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long time = System.currentTimeMillis() - begin;
        try {
            String methodName = invocation.getMethodName();
            String argumentsStr = JSON.toJSONString(invocation.getArguments());
            String resultStr = JSON.toJSONString(result);
            log.info("transfer method-name:{}; time:{}; arguments:{}; result:{}", methodName, time, argumentsStr, resultStr);
        } catch (Exception e) {
            log.warn("transfer print info error:{}", e.getMessage());
        }
        return result;
    }

}
