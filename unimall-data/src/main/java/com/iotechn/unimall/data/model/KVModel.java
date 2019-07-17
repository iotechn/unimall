package com.iotechn.unimall.data.model;

import lombok.Data;

/**
 * Created by rize on 2019/7/5.
 */
@Data
public class KVModel<K,V> {

    private K key;

    private V value;

    /**
     * 适配一部分前端 key 为 name的地方
     * @return
     */
    public K getName() {
        return this.key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KVModel<?, ?> kvModel = (KVModel<?, ?>) o;

        return key != null ? key.equals(kvModel.key) : kvModel.key == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
