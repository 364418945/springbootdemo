package com.example.demo.guava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;

public class PreconditionsTest {


    @Test(expected = NullPointerException.class)
    public void test_check_not_null(){
        checkNotNull(null);
    }

    @Test
    public void test_check_not_null_message(){
        try {
            checkNotNull(null,"null is %s","null");
        }catch (NullPointerException e){
            assertThat(e, isA(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("null is null"));
        }

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_check_ele_index_exception(){
        //下表必须小于集合大小
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");}};
        checkElementIndex(3,list.size());
    }

    @Test
    public void test_check_ele_index(){
        //下表必须小于集合大小
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");}};
        checkElementIndex(2,list.size());
    }

    @Test
    public void test_check_ele_pos(){
        List<String> list = new ArrayList<String>(){{add("123");add("444");add("sdfsd");}};
        checkPositionIndex(3,list.size());
    }

}
