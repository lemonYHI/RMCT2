package com.shura.rmct2.netconnect.model;

/**
 * Created by lemon on 2018/1/29.
 * 星座的实体类；用于封装聚合数据返回的json所转化的实体类。
 * 需要用到gsonformat
 */

public class constellation {

    /**
     * date : 20180129
     * name : 摩羯座
     * datetime : 2018年01月29日
     * all : 60%
     * color : 墨绿色
     * health : 60%
     * love : 40%
     * money : 40%
     * number : 8
     * QFriend : 巨蟹座
     * summary : 今天可以和朋友们交流一下关于兴趣学习、投资方面的信息，或许会有新的想法。但是要注意钱财上不要贪心
     * work : 40%
     * resultcode : 200
     * error_code : 0
     */

    private int date;
    private String name;
    private String datetime;
    private String all;
    private String color;
    private String health;
    private String love;
    private String money;
    private int number;
    private String QFriend;
    private String summary;
    private String work;
    private String resultcode;
    private int error_code;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "constellation{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", datetime='" + datetime + '\'' +
                ", all='" + all + '\'' +
                ", color='" + color + '\'' +
                ", health='" + health + '\'' +
                ", love='" + love + '\'' +
                ", money='" + money + '\'' +
                ", number=" + number +
                ", QFriend='" + QFriend + '\'' +
                ", summary='" + summary + '\'' +
                ", work='" + work + '\'' +
                ", resultcode='" + resultcode + '\'' +
                ", error_code=" + error_code +
                '}';
    }
}
