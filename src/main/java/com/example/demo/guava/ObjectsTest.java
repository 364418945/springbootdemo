package com.example.demo.guava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.isA;

public class ObjectsTest {


    @Test(expected = NullPointerException.class)
    public void test_object_not_null(){
        Objects.requireNonNull(null);
    }

    @Test
    public void test_check_not(){
        Objects.requireNonNull(null);
    }


}
