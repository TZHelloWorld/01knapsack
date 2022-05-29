package com.backtracking;

import java.util.Arrays;
import java.util.Collections;

public class Knapsack {
    // 待选择物品数量
    private int n;

    // 待选择的物品
    private BLBag[] bags;

    // 背包的总承重
    private int totalWeight;

    // 背包的当前承重
    private int currWeight;

    // 放入物品后背包的最优价值
    private int bestValue;

    // 放入物品和背包的当前价值
    private int currValue;

    //构造方法，将物品按照一定规则排序，并且初始化背包容量
    public Knapsack(BLBag[] bags, int totalWeight) {
        this.bags = bags;
        this.totalWeight = totalWeight;
        this.n = bags.length;

        // 物品依据单位重量价值从大到小进行排序
        Arrays.sort(bags, Collections.reverseOrder());
    }

    //回溯算法,从子集树第i个开始。。。
    public int backtrack(int i) {
        //到达子集树的叶结点，相当于此时没有物品可以放入背包，当前价值为最优价值
        if (i >= n) {
            bestValue = currValue;
            return bestValue;
        }

        // 首要条件：放入当前物品，判断物品放入背包后是否小于背包的总承重
        if (currWeight + bags[i].getWeight() <= totalWeight) {
            // 将物品放入背包中的状态
            currWeight += bags[i].getWeight();
            currValue += bags[i].getValue();

            // 选择下一个物品进行判断
            bestValue = backtrack(i + 1);

            // 将物品从背包中取出的状态
            currWeight -= bags[i].getWeight();
            currValue -= bags[i].getValue();
        }

        // 次要条件：不放入当前物品，放入下一个物品可能会产生更优的价值，则对下一个物品进行判断
        // 当前价值+剩余价值<=最优价值，不需考虑右子树情况，由于最优价值的结果是由小往上逐层返回，
        // 为了防止错误的将单位重量价值大的物品错误的剔除，需要将物品按照单位重量价值从大到小进行排序
        if (currValue + getSurplusValue(i + 1) > bestValue) {
            // 选择下一个物品进行判断
            bestValue = backtrack(i + 1);
        }
        return bestValue;
    }

    /**
     * 两种方法获取上界：
     * 1.是直接获取未被选择的所有的价值，作为上界
     * 2.根据当前剩余重量，根据贪心思想获取能够获得的最大价值，作为上界
     **/

//    // 获得物品的剩余总价值
//    public int getSurplusValue(int i) {
//        int surplusValue = 0;
//        for (int j = i; j < n; j++)
//            surplusValue += bags[i].getValue();
//        return surplusValue;
//    }

    //计算上界,也就是剩余容量能够获得的最大价值surplusValue。
    public int getSurplusValue(int i) {
        int residualWeight = totalWeight - currWeight;//计算剩余容量 = 总承重 - 当前承重
        int surplusValue = 0;

        //由于物品数组bags已经按照单位重量价值从大到小排好序了。
        //所以按照贪心策略，如果第i个能够装进背包，那就将其价值累加。
        while (i < n && bags[i].getValue() <= residualWeight) {
            //考虑了装第i个的，那么剩余容量相应减少，而获得的最大价值相应增加
            residualWeight -= bags[i].getWeight();
            surplusValue += bags[i].getValue();
            i++;
        }

        //到这里可能还存在剩余背包未装满，但是剩余容量却小于当前的第i个的重量。也按照贪心的思想分块加
        if (i < n) {
            surplusValue += bags[i].getValue() * residualWeight / bags[i].getWeight();
        }

        return surplusValue;
    }

}