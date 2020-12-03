package com.example.demo.guava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class FunctionsTest {

    public static void main(String[] args) {


    }

    @Test
    public void compose(){
        Function<String,Long> compose = Functions.compose(new Function<Integer, Long>() {
            @Override
            public Long apply(Integer integer) {
                return integer.longValue();
            }
        }, new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        });

        assertThat(compose.apply("hell"),is(4L));
    }

    @Test
    public void toStringFunction(){
        assertThat(Functions.toStringFunction().apply(123),equalTo("123"));
    }


    @Test
    public void forMap(){
        Map map = ImmutableMap.of("key1","value1");
        Function f = Functions.forMap(map);
        assertThat(f.apply("key1"),equalTo("value1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void forMapException(){
        Map map = ImmutableMap.of("key1","value1");
        Function f = Functions.forMap(map);
        assertThat(f.apply("key2"),equalTo("value1"));
    }


    @Test
    public void forMapDefalut(){
        Map map = ImmutableMap.of("key1","value1");
        Function f = Functions.forMap(map,"default");
        assertThat(f.apply("key2"),equalTo("default"));
    }
}
