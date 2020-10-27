package com.github.black.hole.base.algorithm.dynamic;

/**
 * @author hairen.long
 * @date 2020/10/26
 */
public class ZeroOneKnapsack {


    /**
     * 回溯算法实现。注意：我把输入的变量都定义成了成员变量。
     */
    public static void main(String[] args) {
        ZeroOneKnapsack instance = new ZeroOneKnapsack();
//        instance.f(0, 0);
        int i = instance.knapsack(weight, n, w);
        System.out.println(i);
    }

    /**
     * 结果放到maxW中
     */
    private int maxW = Integer.MIN_VALUE;
    /**
     * 物品重量
     */
    private static int[] weight = {2, 2, 4, 6, 3};
    /**
     * 物品个数
     */
    private static int n = 5;
    /**
     * 背包承受的最大重量
     */
    private static int w = 9;


    /**
     * weight:物品重量，n:物品个数，w:背包可承载重量
     */
    public int knapsack(int[] weight, int n, int w) {
        // 默认值false
        boolean[][] states = new boolean[n][w + 1];
        // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0][0] = true;
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }
        // 动态规划状态转移
        for (int i = 1; i < n; ++i) {
            // 不把第i个物品放入背包
            for (int j = 0; j <= w; ++j) {
                if (states[i - 1][j] == true) {
                    states[i][j] = states[i - 1][j];
                }
            }
            //把第i个物品放入背包
            for (int j = 0; j <= w - weight[i]; ++j) {
                if (states[i - 1][j] == true) {
                    states[i][j + weight[i]] = true;
                }
            }
        }
        // 输出结果
        for (int i = w; i >= 0; --i) {
            if (states[n - 1][i] == true) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 调用f(0, 0)
     *
     * @param i
     * @param cw
     */
    public void f(int i, int cw) {
        /**cw==w表示装满了,i==n表示物品都考察完了*/
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        // 选择不装第i个物品
        f(i + 1, cw);
        if (cw + weight[i] <= w) {
            System.out.println("i=" + i + ",cw=" + cw);
            // 选择装第i个物品
            f(i + 1, cw + weight[i]);
        }
    }
}
