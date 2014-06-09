package com.namoo.mongodb.lotto;

public class LottoValue implements Comparable<LottoValue> {
    
    private int seq;
    private int number;
    private int total;
    
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    @Override
    public int compareTo(LottoValue o) {
        // 
        if (total > o.total) {
            return -1;
        } else if (total < o.total) {
            return 1;
        }
        return 0;
    }
}
