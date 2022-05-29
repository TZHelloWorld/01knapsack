package com;


import java.util.Random;

public class top_down_with_memoization {

    //备忘录的记录
    public static int[][] memory = new int[10000][10000];//默认全都是0

    public static int knapsackByMemory(int[] weight, int[] value, int i, int C) {
        //边界条件
        if (i < 0 || C < 0) return 0;

        //如果有数据，那么就返回这个。
        if (memory[i][C] != 0) {
            return memory[i][C];
        }

        // 不选weight[i] 的价值
        int value1 = knapsackByMemory(weight, value, i - 1, C);

        //递归体
        if (C >= weight[i]) {
            //选weight[i]时的价值
            int value2 = knapsackByMemory(weight, value, i - 1, C - weight[i]) + value[i];

            value1 = Math.max(value1, value2);
        }

        memory[i][C] = value1;
        return value1;
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

        int max_value = knapsackByMemory(weight, value, n_number - 1, C);
        System.out.println("此时最大价值为：" + max_value);


    }

}
