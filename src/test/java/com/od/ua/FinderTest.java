package com.od.ua;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FinderTest {
    Finder finder;

    @Before
    public void setUp() throws Exception {
        finder = new Finder();
    }

    @Test
    public void runCastTest() {
        List data = new ArrayList();
        finder.find(InputData.builder().rootPath("C:\\work\\").depth(5).mask("pom").handler((val) -> data.add(val)).build(), (val) -> System.out.println(val));
        log.debug("count:" + data.size());
    }

    @Test
    public void runLibTest() {
        finder.setState(Finder.State.FIND_LIB);
        List data = new ArrayList();
        finder.find(InputData.builder().rootPath("C:\\work\\").depth(5).mask("pom").handler((val) -> data.add(val)).build(), (val) -> System.out.println(val));
        log.debug("count:" + data.size());
    }

    @Test
    public void runTest1() {
        List data = new ArrayList();
        finder.find(InputData.builder().rootPath("C:\\123\\").depth(5).mask("pom").handler((val) -> data.add(val)).build(), (val) -> System.out.println(val));

    }

}
