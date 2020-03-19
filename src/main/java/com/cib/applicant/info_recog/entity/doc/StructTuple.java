package com.cib.applicant.info_recog.entity.doc;

public class StructTuple<K,V,T>{
    /***
     * 用于整合最终结构的tuple
     */
    protected final K column;
    protected final V row;
    protected final T content;

    public StructTuple(K column, V row, T content) {
        this.column = column;
        this.row = row;
        this.content = content;
    }

    public K getColumn() {
        return column;
    }

    public V getRow() {
        return row;
    }

    public T getContent() {
        return content;
    }
}
