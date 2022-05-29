package com.backtracking;

//对于放入背包的物品，该类记录单个物品的信息
public class BLBag implements Comparable<BLBag> {
    //物品名字
    private String name;

    //物品重量
    private int weight;

    //物品价值
    private int value;

    //单位重量价值，设置为int类型有问题，比如5/3和4/3int以后就会出现相等。
    private double unitValue;

    //重写Comparable接口的方法，用于比较该类与传入的BLbag的单位价值的大小
    @Override
    public int compareTo(BLBag snapsack) {
        double value = snapsack.unitValue;
        if (unitValue > value)
            return 1;
        if (unitValue < value)
            return -1;
        return 0;
    }

    //下面构造方法与get，set方法
    public BLBag(String name, int weight, int value) {
        this.weight = weight;
        this.value = value;
        this.name = name;
        //由于value和weight都是int类型，在算除法之前需要转成double
        this.unitValue = (weight == 0) ? 0 : (double) value / (double) weight;
    }

    public String getname() {
        return name;
    }

    public void setname(int weight) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getUnitValue() {
        return unitValue;
    }
}