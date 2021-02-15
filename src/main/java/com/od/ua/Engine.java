package com.od.ua;

import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class Engine {

    private static final int DEFAULT_CAPACITY_QUEUE = 100;
    private static final String OK = "OK";
    private static final String ERR = "ERR";
    private final Deque<InputData> inputDataQueue = new LinkedBlockingDeque<>();
    private final Deque<String> fileQueue = new LinkedBlockingDeque<>(DEFAULT_CAPACITY_QUEUE);
    private final ExecutorService executors = Executors.newFixedThreadPool(2);
    private final Finder finder = new Finder();

    private Runnable findEngine = () -> {
        while (!inputDataQueue.isEmpty()) {
            InputData inputData = inputDataQueue.peekFirst();
            if (inputData != null) {
                log.debug("run finder:" + inputData.toString());
                boolean result = finder.find(inputData, (val) -> fileQueue.add(val));
                if(result){
                    inputData.getHandler().accept(OK);
                }
                else{
                    inputData.getHandler().accept(ERR);
                }
                inputDataQueue.pollFirst();
            }
        }
    };

    private Runnable handlerEngine = () -> {
        InputData memory = null;
        while (!inputDataQueue.isEmpty()) {
            InputData currentInputData = inputDataQueue.peekFirst();
            if (memory == null || !memory.equals(currentInputData)) {
                //log.debug("run handler:" + currentInputData);
                memory = currentInputData;
            }
            String fileName = fileQueue.pollFirst();
            if (fileName != null) {
                //log.debug("add message:" + currentInputData +"-" + fileName);
                memory.getHandler().accept(fileName);
            }
        }
    };

    public void addTask(InputData inputData) {
        inputDataQueue.add(inputData);
        log.debug("send task:" + Thread.currentThread() + " " + inputData);
        if(!executors.isTerminated()){
            log.debug("run engine:" + Thread.currentThread() + " " + inputData);
            executors.submit(findEngine);
            executors.submit(handlerEngine);
        }

    }

    public void single(InputData inputData) {
        log.debug("single finish");
        finder.find(inputData, inputData.getHandler());
        log.debug("single finish");
    }
}
