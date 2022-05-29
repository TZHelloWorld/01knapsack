package com.branch_and_bound;

public class Node {
    //当前物品的属性
    private int currweight;//当前重量
    private int currvalue;//当前价值
    private int unbounvalue;//上界价值
    private int index;//索引
    private Node father;//父节点
    private boolean isleft;//是否为左节点

    public boolean isIsleft() {
        return isleft;
    }

    public void setIsleft(boolean isleft) {
        this.isleft = isleft;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public int getCurrweight() {
        return currweight;
    }

    public void setCurrweight(int currweight) {
        this.currweight = currweight;
    }

    public int getCurrvalue() {
        return currvalue;
    }

    public void setCurrvalue(int currvalue) {
        this.currvalue = currvalue;
    }

    public int getUnbounvalue() {
        return unbounvalue;
    }

    public void setUnbounvalue(int unbounvalue) {
        this.unbounvalue = unbounvalue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    //构造函数
    public Node(int currweight, int currvalue, int index) {
        this.currweight = currweight;
        this.currvalue = currvalue;
        this.index = index;
    }
}
