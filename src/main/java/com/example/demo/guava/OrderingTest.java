package com.example.demo.guava;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertTrue;
import java.io.FileWriter;
import java.security.Key;
import java.util.List;
import java.util.Objects;

/**
 * @author cityre
 * @create 2019-04-26
 * @desc guava Ordering
 **/
public class OrderingTest {
    public static void main(String[] args) {
        List arrayList = Lists.newArrayList(1,2,3,4,5,6);
        arrayList = Ordering.natural().greatestOf(arrayList,3);
        System.out.println(arrayList);
        Function<Foo,String> sorted = new Function<Foo, String>() {
            @Override
            public String apply(Foo input) {
                return input.sortedBy;
            }
        };

        List<Foo> foolist = Lists.newArrayList(new Foo("123"),new Foo("321"));
//        Ordering.natural().onResultOf(new Function<Foo, Comparable>() {
//            @Override
//            public Comparable apply(Object input) {
//                return input;
//
//            }
//        })
        System.out.println(Ints.asList(1,2, (int) 3.3,44));
        System.out.println(Ints.tryParse("s"));
        System.out.println(Ints.tryParse("12"));

        Integer x = 5;
        Integer y = 1;
        Integer z = 10;
        System.out.println(Range.closed(y,z).contains(5));
        System.out.println("123".hashCode());
        System.out.println("123".hashCode());
        System.out.println("c".hashCode());

//        LoadingCache catche = CacheBuilder.newBuilder().build(new CacheLoader<Key, Graph>() {
//            @Override
//            public Graph load(Key key) throws Exception {
//                return createExpensiveGraph(key);
//            }
//        })
//        Objects.
//        FileWriter
    }

    public static class Foo{
        String sortedBy;

        public Foo(String sortedBy) {
            this.sortedBy = sortedBy;
        }
    }
}
