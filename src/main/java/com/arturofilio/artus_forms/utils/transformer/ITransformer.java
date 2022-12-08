package com.arturofilio.artus_forms.utils.transformer;

public interface ITransformer<K, T> {
    T transformData(K data);
}
