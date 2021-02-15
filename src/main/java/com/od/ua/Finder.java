package com.od.ua;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
public class Finder {

    enum State {
        FIND,
        FIND_LIB
    }

    @Setter
    @Getter
    private State state = State.FIND_LIB;

    public boolean find(final InputData inputData, final Consumer<String> handler) {
        boolean result;
        switch (state) {
            case FIND_LIB:
                result = findLib(inputData, handler);
                break;
            case FIND:
            default:
                result = findCastQueue(inputData, handler);

        }
        return result;
    }

    boolean findCastQueue(InputData inputData, final Consumer<String> handler){
        File[] files = new File(inputData.getRootPath()).getAbsoluteFile().listFiles();
        if(files == null) return false;
        int defaultLevel = files.length;
        InputData inputDataUpdate = InputData.createNewDepth(inputData,defaultLevel + inputData.getDepth());
        Deque<File>folderList = new ArrayDeque<>();
        boolean result = findCast(inputDataUpdate, handler, folderList);
        while (!folderList.isEmpty()){
            String folder = folderList.pollFirst().getAbsolutePath();
            findCast(InputData.createNewFolder(inputData,folder), handler, folderList);
        }
        return result;
    }

    private boolean findCast(final InputData inputData, final Consumer<String> handler, Deque folder) {
        boolean result = false;
        final String rootPath = inputData.getRootPath();
        final String mask = inputData.getMask();
        if (rootPath != null && mask != null) {
            File[] files = new File(rootPath).getAbsoluteFile().listFiles();
            if (files != null) {
                for (File f : files) {
                    String tmp[] = f.getAbsolutePath().split("\\\\");
                    if (f.isDirectory() && tmp.length <= inputData.getDepth()) {
                        folder.addLast(f);
                    }
                    if ((mask == null || f.getName().contains(mask))) {
                        handler.accept(f.getAbsolutePath());
                    }
                }
                result = true;
            }
        }
        return result;
    }

    private boolean findLib(final InputData inputData, final Consumer<String> handler) {
        log.debug("run find");
        boolean result = false;
        final String rootPath = inputData.getRootPath();
        final int depth = inputData.getDepth();
        final String mask = inputData.getMask();
        try (Stream<Path> paths = Files.find(Paths.get(rootPath), depth, (val, attr) -> val.toString().contains(mask))) {
            paths.map(val -> val.toString()).forEach(val -> handler.accept(val));
            result = true;
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            handler.accept(e.toString());

        } catch (IOException e) {
            handler.accept(e.toString());
            e.printStackTrace();
        }
        return result;
    }
}
