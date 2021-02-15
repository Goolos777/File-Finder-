package com.od.ua;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

@Slf4j
public class EngineTest {

    Engine findElementFileSystem;

    @Before
    public void setUp() {
        findElementFileSystem = new Engine();
    }

    @Test
    public void runTest1() {
        List data = new ArrayList();
        findElementFileSystem.single(InputData.builder().rootPath("C:\\work\\").depth(5).mask("pom").handler((val) -> data.add(val)).build());
        log.debug("count:" + data.size());
    }

    @Test
    public void runMultiTask2() {
        List data = new ArrayList();
        findElementFileSystem.addTask(InputData.builder().rootPath("C:\\work").depth(5).mask("pom").handler(val -> data.add(val)).build());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("count:" + data.size());
    }

    @Test
    public void runMultiTask() {
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        List<String> data3 = new ArrayList<>();
        List<String> data4 = new ArrayList<>();
        List<String> data5 = new ArrayList<>();
        List<String> data6 = new ArrayList<>();
        List<String> data7 = new ArrayList<>();
        List<String> data8 = new ArrayList<>();
        List<String> data9 = new ArrayList<>();
        List<String> data10 = new ArrayList<>();
        List<String> data11 = new ArrayList<>();
        List<String> data12 = new ArrayList<>();
        printSize(1, data1);
        printSize(2, data2);
        printSize(3, data3);
        printSize(4, data4);
        printSize(5, data5);
        printSize(6, data6);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printSize(7, data7);
        printSize(8, data8);
        printSize(9, data9);
        printSize(10, data10);
        printSize(11, data11);
        printSize(12, data12);
        for (int connt = 0; connt < 10; connt++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("---------------------");
            log.debug("data1:" + data1.size());
            log.debug("data2:" + data2.size());
            log.debug("data3:" + data3.size());
            log.debug("data4:" + data4.size());
            log.debug("data5:" + data5.size());
            log.debug("data6:" + data6.size());
            log.debug("data7:" + data7.size());
            log.debug("data8:" + data8.size());
            log.debug("data9:" + data9.size());
            log.debug("data10:" + data10.size());
            log.debug("data11:" + data11.size());
            log.debug("data12:" + data12.size());
            log.debug("---------------------");
        }
        log.debug("finish");
        assertTrue(data1.size() == data2.size());
        assertTrue(data1.size() == data3.size());
        assertTrue(data1.size() == data4.size());
        assertTrue(data1.size() == data5.size());
        assertTrue(data1.size() == data6.size());
        assertTrue(data1.size() == data7.size());
        assertTrue(data1.size() == data8.size());
        assertTrue(data1.size() == data10.size());
        assertTrue(data1.size() == data11.size());
        assertTrue(data1.size() == data12.size());
    }

    @Test
    public void runMultiTaskEx() {
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        List<String> data3 = new ArrayList<>();
        List<String> data4 = new ArrayList<>();
        List<String> data5 = new ArrayList<>();
        List<String> data6 = new ArrayList<>();
        List<String> data7 = new ArrayList<>();
        List<String> data8 = new ArrayList<>();
        List<String> data9 = new ArrayList<>();
        List<String> data10 = new ArrayList<>();
        List<String> data11 = new ArrayList<>();
        List<String> data12 = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(7);
        executorService.submit(() -> printSize(1, data1));
        executorService.submit(() -> printSize(2, data2));
        executorService.submit(() -> printSize(3, data3));
        executorService.submit(() -> printSize(4, data4));
        executorService.submit(() -> printSize(5, data5));
        executorService.submit(() -> printSize(6, data6));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(() -> printSize(10, data7));
        executorService.submit(() -> printSize(20, data8));
        executorService.submit(() -> printSize(30, data9));
        executorService.submit(() -> printSize(40, data10));
        executorService.submit(() -> printSize(50, data11));
        executorService.submit(() -> printSize(50, data12));

        for (int connt = 0; connt < 10; connt++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("---------------------");
            log.debug("data1:" + data1.size());
            log.debug("data2:" + data2.size());
            log.debug("data3:" + data3.size());
            log.debug("data4:" + data4.size());
            log.debug("data5:" + data5.size());
            log.debug("data6:" + data6.size());
            log.debug("data7:" + data7.size());
            log.debug("data8:" + data8.size());
            log.debug("data9:" + data9.size());
            log.debug("data10:" + data10.size());
            log.debug("data11:" + data11.size());
            log.debug("data12:" + data12.size());
            log.debug("---------------------");
        }
        assertTrue(data1.size() == data2.size());
        assertTrue(data1.size() == data3.size());
        assertTrue(data1.size() == data4.size());
        assertTrue(data1.size() == data5.size());
        assertTrue(data1.size() == data6.size());
        assertTrue(data1.size() == data7.size());
        assertTrue(data1.size() == data8.size());
        assertTrue(data1.size() == data10.size());
        assertTrue(data1.size() == data11.size());
        assertTrue(data1.size() == data12.size());
        log.debug("finish");
    }


    private void printSize(int id, List dataMulty) {
        InputData inputData = InputData
                .builder()
                .id(id)
                .rootPath("C:\\work")
                .depth(5).mask("pom")
                .handler((val) -> dataMulty.add(val))
                .build();
        findElementFileSystem.addTask(inputData);
    }
}
