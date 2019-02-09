package com.wei.entity;

import java.util.Date;

/**秒杀对象
 * @author 为为
 * @create 1/25
 */
public class  MiaoShaUser {
    private String id;
    private String name;
    private String password;
    private String salt;
    private Integer head;
    private Date register;
    private Date last;
    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public Date getRegister() {
        return register;
    }

    public void setRegister(Date register) {
        this.register = register;
    }

    public Date getLast() {
        return last;
    }

    public void setLast(Date last) {
        this.last = last;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
