package com;


import java.util.Random;

public class bottom_up_method {
    public static int knapsackByDynamic(int[] weight, int[] value, int n, int c) {
        //定义dp数组
        int[][] dp = new int[n + 1][c + 1];

        //初始化，对于当重量为0和物品个数为0时，价值为0
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j < c + 1; j++) {
            dp[0][j] = 0;
        }


        //根据状态转移方程编写迭代
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < c + 1; j++) {
                //放与不放，哪个价值更高，就将其存储到dp中

                //不放第i个物品的价值value
                int val = dp[i - 1][j];

                //如果要放入，那么第i个物品的重量需要比背包容量小
                if (weight[i - 1] <= j) {//表示第i个物品放得下
                    //放入第i个物品的价值为value2
                    // value2 = dp[i-1][j-W[i-1]]+V[i-1];
                    // value = Math.max(value,value2)
                    //将上面的写在一起
                    val = Math.max(val, dp[i - 1][j - weight[i - 1]] + value[i - 1]);
                }
                //将计算出来的最优dp[i][j]进行存储
                dp[i][j] = val;
            }
        }
        //看需要返回什么，如果只是最大值，那就直接返回dp[N][C],如果返回数组也行。
        return dp[n][c];
    }


    //测试代码
    public static void main(String[] args) {
        int n_number = 20;//定义物品的个数

        Random r = new Random();

        //随机生成重量数组和价值数组
        int[] weight = new int[n_number];
        int[] value = new int[n_number];

        //背包容量
        int C = 0;

        for (int i = 0; i < n_number; i++) {
            weight[i] = ((r.nextInt(89) + 10));
            value[i] = ((r.nextInt(89) + 10));
            C += weight[i];
        }
        C = C / 3;

//        //写死的，然后方便测试所有跑出来的是否一样
//        n_number = 20;
//        weight = new int[]{37, 29, 27, 61, 85, 13, 48, 12, 94, 19, 56, 36, 69, 10, 71, 27, 75, 77, 25, 41};
//        value = new int[]{32, 68, 23, 88, 46, 82, 81, 50, 65, 78, 63, 63, 55, 62, 68, 97, 51, 40, 59, 91};
//        C = 304;
//        //此时最大价值为：763


        System.out.print("随机生成的重量数组为：");
        for (int i = 0; i < n_number; i++) {
            System.out.print(" " + weight[i] + ",");
        }
        System.out.println();
        System.out.print("随机生成的价值数组为：");
        for (int i = 0; i < n_number; i++) {
            System.out.print(" " + value[i] + ",");
        }
        System.out.println();
        System.out.println("背包容量为" + C);


        int max_value = knapsackByDynamic(weight, value, n_number, C);
        System.out.println("此时最大价值为：" + max_value);


    }

}
