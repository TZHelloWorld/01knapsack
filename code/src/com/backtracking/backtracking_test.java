package com.backtracking;

import java.util.Random;


public class backtracking_test {

    public static void test1() {
        int w = 194;// 背包的容量
        int n = 7;// 物品的个数
        BLBag[] bags = new BLBag[n];
        int[] weight = {72, 33, 37, 94, 39, 99, 5};
        int[] value = {9, 95, 6, 4, 88, 42, 37};
        String pid;
        for (int i = 0; i < n; i++) {
            pid = "p" + i;
            bags[i] = new BLBag(pid, weight[i], value[i]);
        }
        Knapsack knapsack = new Knapsack(bags, w);
        System.out.println("最优解为：" + knapsack.backtrack(0));
    }

    public static void test2() {
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


        //回溯算法
        BLBag[] bags = new BLBag[n_number];
        for (int i = 0; i < n_number; i++) {
            bags[i] = new BLBag("p" + i, weight[i], value[i]);
        }
        Knapsack knapsack = new Knapsack(bags, C);

        int max_value = knapsack.backtrack(0);
        System.out.println("此时最大价值为：" + max_value);

    }

    //测试代码
    public static void main(String[] args) {
        test1();
        test2();
    }


}
