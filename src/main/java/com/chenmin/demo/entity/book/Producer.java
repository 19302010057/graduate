
package com.store.entity.book;

/**
 * @author: 黄龙
 * @date: 2020/6/12 20:58
 * @description: 出版社类
 */
public class Producer {
    private int id;//编号
    private String name;//出版社姓名
    private boolean showProducer;//出版社是否显示
    private Integer rank;//排序值
    private Integer num;//该出版社中有多少书

    public Producer() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowProducer() {
        return showProducer;
    }

    public void setShowProducer(boolean showProducer) {
        this.showProducer = showProducer;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", showProducer=" + showProducer +
                ", rank=" + rank +
                ", num=" + num +
                '}';
    }
}
