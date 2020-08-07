package com.github.black.hole.netty.simulate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;

/**
 * @author hairen.long
 * @date 2020/7/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeCalculateContext {
    /** 执行线程池 */
    private ExecutorService executor;
}
