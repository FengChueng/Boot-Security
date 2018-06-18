package com.zyl.sercurity.api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

@RestController
public class AsyncApi {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @ResponseBody
    @GetMapping("/testdeferred")
    public DeferredResult<String> testDeferred() throws InterruptedException, ExecutionException, Exception {
        DeferredResult<String> deferredResult = new DeferredResult<>(2000L);

        executorService.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = "休眠3s";
                deferredResult.setResult(result);

            }
        });
        //
        // String string = submit.get(5, TimeUnit.SECONDS);
        // System.out.println(string);

        // 调用完成后
        deferredResult.onCompletion(new Runnable() {

            @Override
            public void run() {
                //
                deferredResult.setResult("hello 成功");

            }
        });

        // 调用超时
        deferredResult.onTimeout(new Runnable() {

            @Override
            public void run() {
                deferredResult.setResult("超时返回");
            }
        });
        return deferredResult;
    }

    @ResponseBody
    @GetMapping("/testWebAsyncTask")
    public WebAsyncTask<String> testWebAsyncTask() throws InterruptedException, ExecutionException, Exception {
        WebAsyncTask<String> deferredResult = new WebAsyncTask<>(3000L, new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                String result = "休眠3s";
                return result;
            }
        });
        //
        // String string = submit.get(5, TimeUnit.SECONDS);
        // System.out.println(string);

        // 调用完成后
        deferredResult.onCompletion(new Runnable() {

            @Override
            public void run() {
                System.out.println("请求完成");
            }
        });

        // 调用超时
        deferredResult.onTimeout(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "超时返回";
            }

        });
        return deferredResult;
    }
}
