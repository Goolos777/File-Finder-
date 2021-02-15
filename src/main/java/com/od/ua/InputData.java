package com.od.ua;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.function.Consumer;

@Data
@ToString
@Builder
public class InputData {
    private final int id;
    private final String rootPath;
    private final int depth;
    private final String mask;
    private Consumer<String> handler;


    public static InputData createNewFolder(InputData inputData, String folder) {
        return InputData.builder()
                .depth(inputData.depth)
                .handler(inputData.handler)
                .mask(inputData.mask)
                .handler(inputData.handler)
                .rootPath(folder)
                .build();
    }

    public static InputData createNewDepth(InputData inputData, int newDepth) {
        return InputData.builder()
                .depth(newDepth)
                .handler(inputData.handler)
                .mask(inputData.mask)
                .handler(inputData.handler)
                .rootPath(inputData.rootPath)
                .build();
    }
}
