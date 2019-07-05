package com.iotechn.unimall.data.model;

import lombok.Data;

/**
 * Created by rize on 2019/7/5.
 */
@Data
public class KVModel<K,V> {

    private K key;

    private V value;

}
