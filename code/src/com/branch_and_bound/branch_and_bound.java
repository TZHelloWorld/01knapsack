package com.branch_and_bound;

import java.util.LinkedList;
import java.util.Random;

public class branch_and_bound {

    /**
     * 权重排序，选择排序
     *
     * @param p:传入的BLBag数组
     */
    public static void sort(BLBag[] p) {
        BLBag t;
        for (int i = 0; i < p.length; i++) {
            int max = i;
            t = p[i];
            for (int j = i; j < p.length; j++) {
                if (t.getUnitValue() < p[j].getUnitValue()) {
                    t = p[j];
                    max = j;
                }
            }
            t = p[i];
            p[i] = p[max];
            p[max] = t;

        }
    }

    /**
     * 求上界的函数
     *
     * @param p:数组p
     * @param i:当前位置
     * @param weight:当前背包重量
     * @return 最大价值（不包含背包的已有价值）
     */
    public static double find_bound(BLBag[] p, int i, int weight) {
        double value = 0;
        //将状态位后面的物品求贪心算法的解，上界函数的解为返回值+当前背包价值
        forLOOP:
        for (int k = i; k < p.length; k++)//循环名字
        {
            //贪心算法求解问题（修改版）
            if (p[k].getValue() < weight) {
                value = value + p[k].getValue();
                weight = weight - p[k].getValue();
            } else {
                double a = weight * p[k].getUnitValue();//当前价值
                value = value + a;
                //weight = 0;
                break forLOOP;//跳出循环
            }
        }
        return value;
    }


    /**
     * 分支限界法主体
     *
     * @param p:物品数组p
     * @param weight:重量
     * @param a:状态数组
     * @param solution:
     * @return 当前考虑位置i的最优解
     */
    public static int branch_and_limit(BLBag[] p, int weight, Integer[] a, double solution) {
        //声明队列
        LinkedList<Node> node_list = new LinkedList<Node>();
        LinkedList<Node> node_solution = new LinkedList<Node>();
        node_list.add(new Node(0, 0, 0));
        node_solution.add(new Node(0, 0, 0));
        while (!node_list.isEmpty()) {
            //取出元素
            Node node = node_list.pop();
            //判断条件，节点的不放入的最大值大于当前最优解，节点小于数组的长度
            //这里不用等于，必须要大于
            if (node.getUnbounvalue() + node.getCurrvalue() > solution && node.getIndex() < p.length) {
                //左节点
                int left_Weight = node.getCurrweight() + p[node.getIndex()].getWeight();
                int left_value = node.getCurrvalue() + p[node.getIndex()].getValue();
                Node left = new Node(left_Weight, left_value, node.getIndex() + 1);
                //设置左节点的父节点
                left.setFather(node);
                left.setIsleft(true);
                //将左节点添加到最优解队列中
                node_solution.add(left);
                //设置左节点的上界价值
                left.setUnbounvalue((int) find_bound(p, node.getIndex(), weight - node.getCurrweight()));
                //左节点的重量小于等于背包的承重，且左节点的上界价值大于最优解
                if (left.getCurrweight() <= weight && left.getUnbounvalue() + left.getCurrvalue() > solution) {
                    //将节点加入队列中
                    node_list.add(left);
                    a[node.getIndex()] = 1;
                    //将最优值重新赋值  条件就是节点的当前价值大于问题的最优解
                    if (left.getCurrvalue() > solution) {
                        solution = left.getCurrvalue();
                        //System.out.println("放入的物品有："+p[node.getIndex()].pid);
                    }
                }
                //右节点   右节点的设置不需要太多，和父节点差不多
                Node right = new Node(node.getCurrweight(), node.getCurrvalue(), node.getIndex() + 1);
                //将右节点添加到最优解队列中
                right.setFather(node);
                right.setIsleft(false);
                node_solution.add(right);
                right.setUnbounvalue((int) find_bound(p, node.getIndex(), weight - node.getCurrweight()));
                //右节点的上界价值大于当前最优解
                if (right.getUnbounvalue() + node.getCurrvalue() > solution) {
                    //添加右节点
                    node_list.add(right);
                    a[node.getIndex()] = 0;
                }
            }
        }

        //调用最优解方法
        pr(node_solution, (int) solution, p);

        //返回最优解
        return (int) solution;
    }

    /**
     * 分支限界法求解最优解的方法
     *
     * @param node_solution:
     * @param solution:
     * @param p:
     */
    public static void pr(LinkedList<Node> node_solution, int solution, BLBag[] p) {
        int[] a = new int[p.length];
        Node pr_node = null;
        //从list中循环遍历最优解的节点
        for (Node node : node_solution) {
            if (node.getCurrvalue() == solution) {
                //System.out.println("最优解的父节点的索引为："+node.getFather().getIndex());
                pr_node = node;
            }
        }
        //循环遍历最优节点的父节点，判断其是否为左节点
        while (pr_node.getFather() != null) {
            if (pr_node.isIsleft()) {
                a[pr_node.getIndex() - 1] = 1;
            }
            pr_node = pr_node.getFather();
        }
        //打印放入了哪些物品
//        for (int i = 0; i < p.length; i++) {
//            if (a[i] == 1) System.out.println("放入了物品：" + p[i].getname());
//        }
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


        //分支限界法
        BLBag[] p = new BLBag[n_number];
        int solution = -1;
        String pid;
        for (int i = 0; i < n_number; i++) {
            pid = "p" + i;
            p[i] = new BLBag(pid, weight[i], value[i]);
        }
        // 算法开始
        //声明状态数组并初始化为空
        Integer[] arr = new Integer[n_number];
        for (int i = 0; i < n_number; i++) arr[i] = null;
        //对p数组按权重排序
        sort(p);

        int max_value = branch_and_limit(p, C, arr, solution);
        System.out.println("此时最大价值为：" + max_value);

    }

}
