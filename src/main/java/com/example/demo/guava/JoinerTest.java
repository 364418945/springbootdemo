package com.example.demo.guava;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class JoinerTest {


    @Test
    public void test_jonner(){
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");}};
        String result = Joiner.on("|").join(list);
        assertThat(result,equalTo("123|444|sdfsd"));
    }


    @Test(expected = NullPointerException.class)
    public void test_jonner_with_null_exception(){
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");add(null);}};
        String result = Joiner.on("|").join(list);
        assertThat(result,equalTo("123|444|sdfsd"));
    }

    @Test
    public void test_jonner_with_null(){
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");add(null);}};
        String result = Joiner.on("|").skipNulls().join(list);
        assertThat(result,equalTo("123|444|sdfsd"));
    }


    @Test
    public void test_jonner_with_null_defalut(){
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");add(null);}};
        String result = Joiner.on("|").useForNull("NULL").join(list);
        assertThat(result,equalTo("123|444|sdfsd|NULL"));
    }


    @Test
    public void test_jonner_with_map(){
        Map map = new HashMap<String,String>(){{put("key1","value1");put("key2","value2");put("key3","value3");}};
        String result = Joiner.on("|").withKeyValueSeparator("=").join(map);
        assertThat(result,equalTo("key1=value1|key2=value2|key3=value3"));
    }


    @Test
    public void test_jonner_with_append(){
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");}};
        StringBuilder s = new StringBuilder();
        StringBuilder result = Joiner.on("|").appendTo(s,list);
        assertThat(result,sameInstance(s));
        assertThat(result.toString(),equalTo("123|444|sdfsd"));
    }


}
