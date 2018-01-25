package com.example.demo.model;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RequestVerify<T> {

    public static final Logger logger = LoggerFactory.getLogger(RequestVerify.class);
    private static final String INVALIDINPUTDATA = "缺失必选参数 (%s)";
    private static final String NOTFORMATINPUTDATA = "参数取值不正确 (%s)，请参考接口文档";
    private static final String INVALIDLENGTH = "参数长度不正确 (%s)，请参考接口文档";
    private static final String INVALIDBOTH = "参数不能同时为空 (%s)，请参考接口文档";

    private T t;

    private String method;

    private Field[] fields;


    public RequestVerify(T t, String method){
        this.t = t;
        this.method = method;
        Class cla = t.getClass();
        this.fields = cla.getDeclaredFields();
    }


    /**
     * method可以不传，如果存在多个接口需要验证，则需要传
     * 传入对象验证，必填项
     *
     */
    public void checkRequest() {
        boolean isAnno;
        boolean isAnnos;
        for (Field _field : fields) {
            isAnno = _field.isAnnotationPresent(ParamCheck.class);
            isAnnos = _field.isAnnotationPresent(ParamChecks.class);
            if (isAnno) {
                ParamCheck paramCheck = _field.getAnnotation(ParamCheck.class);
                CheckMethod(_field, paramCheck);
            } else if (isAnnos) {//如果没有则判断是否存在ParamChecks(是否存在多个ParamCheck注解)
                ParamChecks paramChecks = _field.getAnnotation(ParamChecks.class);
                ParamCheck[] paramCheck = paramChecks.value();
                for (ParamCheck _paramCheck : paramCheck) {
                    CheckMethod(_field, _paramCheck);
                }
            }
        }
    }


    private void CheckMethod(Field _field, ParamCheck paramCheck) {
        String[] methods = paramCheck.method();//获取验证的制定方法（考虑到多个接口共用一个对象）
        if ((!ObjectUtils.isEmpty(methods) && Arrays.asList(methods).contains(method)) || (ObjectUtils.isEmpty(methods))) {//如果包含对应的方法则验证
            boolean checkNullDepend = false;
            boolean checkNullBoth = false;
            boolean checkNull = CheckNull( _field, paramCheck);//false:不存在标签，true：有效，异常：不合格
            if (!checkNull) {//如果不存在标签
                checkNullDepend = CheckNullDepend( _field, paramCheck);
                if (!checkNullDepend) {
                    checkNullBoth = CheckNullBoth( _field, paramCheck);
                }
            }
            if (checkNull || checkNullDepend || checkNullBoth) {//如果当前列有效则验证长度
                CheckLength( _field, paramCheck);
                checkValue( _field, paramCheck);
            }
        }
    }

    private void checkValue(Field _field, ParamCheck paramCheck) {
        String[] values = paramCheck.valueIn();
        if (values.length > 0) {
            T value = getPropertyVal(_field.getName());
            Preconditions.checkArgument(!ObjectUtils.isEmpty(value), String.format(INVALIDINPUTDATA, _field.getName()));
            boolean flag = false;
            for (int i = 0; i < values.length; i++) {
                String _value = values[i];
                if (_value.equals(value.toString())) {
                    flag = true;
                    break;
                }
            }
            Preconditions.checkArgument(flag, String.format(NOTFORMATINPUTDATA, _field.getName()));
        }
    }

    /**
     * 判断字段长度要求
     *
     * @param _field
     * @param paramCheck
     */
    private void CheckLength(Field _field, ParamCheck paramCheck) {
        int length = paramCheck.length();
        if (length != 0) {
            T value = getPropertyVal(_field.getName());
            Preconditions.checkArgument(!ObjectUtils.isEmpty(value) && value.toString().length() <= length, String.format(INVALIDLENGTH, _field.getName()));
        }
    }

    /**
     * 验证不能同时为空
     *
     * @param paramCheck
     */
    private boolean CheckNullBoth(Field __field, ParamCheck paramCheck) {
        String[] strs = paramCheck.notNullBoth();
        if (strs != null && strs.length != 0) {
            boolean flag = false;
            String filedName = "";
            for (String str : strs) {
                T value = getPropertyVal(str);//获得相应字段是否为空
                boolean isEmpty = ObjectUtils.isEmpty(value);
                //如果当前不为空则直接跳出
                if (!isEmpty && __field.getName().equals(str)) {
                    return true;
                }
                flag = !isEmpty || flag;
                filedName += str + ",";
            }
            filedName = filedName.substring(0, filedName.length() - 1);
            Preconditions.checkArgument(flag, String.format(INVALIDBOTH, filedName));
        }
        return false;
    }

    /**
     * 验证某种情况下不能为空
     *
     * @param _field
     * @param paramCheck
     * @return
     */
    private boolean CheckNullDepend(Field _field, ParamCheck paramCheck) {
        boolean flag = false;//
        String str = paramCheck.notNullDepend();
        if (!"".equals(str)) {
            String[] dependOn = paramCheck.notNullDependOn();
            T value = getPropertyVal(str);//获得相应字段是否为空
            if ((dependOn != null && Arrays.asList(dependOn).contains(String.valueOf(value))) || dependOn.length == 0) {
                if (!ObjectUtils.isEmpty(value)) {
                    value = getPropertyVal(_field.getName());
                    Preconditions.checkArgument(!ObjectUtils.isEmpty(value) && !StringUtils.isEmpty(value.toString()), String.format(INVALIDINPUTDATA, _field.getName()));
                    flag = true;//如果有
                }
            }
        }
        return flag;
    }

    /**
     * 验证不能为空
     *
     * @param _field
     * @param paramCheck
     * @return
     */
    private boolean CheckNull(Field _field, ParamCheck paramCheck) {
        boolean str = paramCheck.notNull();
        if (str) {//如果为真
            T value = getPropertyVal(_field.getName());
            Preconditions.checkArgument(!ObjectUtils.isEmpty(value), String.format(INVALIDINPUTDATA, _field.getName()));
//                if(str.indexOf(",")>0){//判断取值是否在范围内
//                    String[] notNullvalue = str.split(",");
//                    List notNullvalueList = Arrays.asList(notNullvalue);
//                    Preconditions.checkArgument(notNullvalueList.contains(value.toString()), String.format(notFormatInputData, _field.getName()));
//                }else{
//                    Preconditions.checkArgument(str.equals(value.toString()), String.format(notFormatInputData, _field.getName()));
//                }
            return true;
        }
        return false;
    }

    /**
     * 获取某个fieldName的值
     *
     * @param fieldName
     * @return
     */
    private T getPropertyVal(String fieldName) {
        PropertyDescriptor propertyDescriptor = null;
        try {
            propertyDescriptor = new PropertyDescriptor(fieldName, t.getClass());
            Method readMethod = propertyDescriptor.getReadMethod();
            T result = (T) readMethod.invoke(t);
            return result;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }




}
