package com.example.demo.guava;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FilesTest {

    final String sourceFile = "src/main/java/com/example/demo/guava/testCopy.txt";
    final String targetFile = "src/main/java/com/example/demo/guava/testCopyTarget.txt";
    final String javaFile = "src/main/java";
    @Test
    public void testFileCopy() throws IOException {
        File sourcF = new File(sourceFile);
        File targetF = new File(targetFile);
        System.out.println(sourcF.getAbsolutePath());
        System.out.println(sourcF.getCanonicalPath());
        Files.copy(sourcF,targetF);
        assertThat(targetF.exists(),equalTo(true));
    }

    @Test
    public void testFileCopyNio() throws IOException {
        File targetF = new File(targetFile);
        java.nio.file.Files.copy(
                Paths.get(sourceFile),
                Paths.get(targetFile),
                StandardCopyOption.REPLACE_EXISTING);
        assertThat(targetF.exists(),equalTo(true));
    }


    @Test
    public void testReadLine()throws IOException {
        List<String> result = Files.readLines(new File(sourceFile), Charset.defaultCharset());

        String res = "adfasdfa\n" +
                "jkdsafiasdfj\n" +
                "sdaf";
        assertThat(Joiner.on("\n").join(result),equalTo(res));
    }


    @Test
    public void testReadLineProcessLine()throws IOException {
        List<String> result = Files.readLines(new File(sourceFile), Charset.defaultCharset(), new LineProcessor<List<String>>() {
            List<String> result = Lists.newArrayList();
            @Override
            public boolean processLine(String s) throws IOException {
                if(s.contains("da")){
                    result.add(s);
                }
                return true;
            }

            @Override
            public List<String> getResult() {
                return result;
            }
        });

        String res = "sdaf";
        assertThat(Joiner.on("\n").join(result),equalTo(res));


    }

    @Test
    public void testisFileOrDir(){
        assertThat(Files.isFile().apply(new File(sourceFile)),equalTo(true));
        assertThat(Files.isDirectory().apply(new File(sourceFile).getParentFile()),equalTo(true));
    }


    @Test
    public void testCopyFileHashEqual() throws IOException {
        HashCode hashCode = Files.hash(new File(sourceFile), Hashing.md5());
        System.out.println(hashCode.toString());
        System.out.println(hashCode.toString().length());
        Files.copy(new File(sourceFile),new File(targetFile));
        HashCode hashCode2 = Files.hash(new File(targetFile), Hashing.md5());
        assertThat(hashCode,equalTo(hashCode2));

        File file = new File(targetFile);
        file.deleteOnExit();//虚拟机结束时删除
    }


    @Test
    public void testWriteFile() throws IOException {
        String content = "content";
        Files.write(content,new File(targetFile),Charset.defaultCharset());
        String content1 = Files.toString(new File(targetFile),Charset.defaultCharset());
        assertThat(content,equalTo(content1));

        String content3 = "content3";
        Files.write(content3,new File(targetFile),Charset.defaultCharset());
        String content4 = Files.toString(new File(targetFile),Charset.defaultCharset());
        assertThat(content4,equalTo(content3));


        String res = "adfasdfa\r\n" +
                "jkdsafiasdfj\r\n" +
                "sdaf";
        String content2 = Files.toString(new File(sourceFile),Charset.defaultCharset());
        System.out.println(content2);
        assertThat(content2,equalTo(res));

    }


    @Test
    public void testAppendFile() throws IOException {
        String content = "content";
        String content1 = "content1";
        Files.write(content,new File(targetFile), Charsets.UTF_8);
        Files.append(content1,new File(targetFile),Charsets.UTF_8);
        String readresult = Files.toString(new File(targetFile),Charset.defaultCharset());
        assertThat(content+content1,equalTo(readresult));
    }


    @Test
    public void testTouch() throws IOException {
        Files.touch(new File(targetFile));
        new File(targetFile).deleteOnExit();
    }


    @Test
    public void testTreeFiles(){
        Iterable<File> iterable = Files.fileTreeTraverser().children(new File(javaFile));
        iterable.forEach(System.out::println);
    }


//    @After
//    public void removeFile(){
//        File targetF = new File(targetFile);
//        if(targetF.exists()){
//            targetF.delete();
//        }
//    }
}
