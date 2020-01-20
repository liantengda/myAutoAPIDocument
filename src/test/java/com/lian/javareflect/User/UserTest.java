package com.lian.javareflect.User;

import com.lian.javareflect.JavareflectApplication;
import com.lian.javareflect.annotion.TestAnnotation;
import com.lian.javareflect.mapper.UserMapper;
import com.lian.javareflect.model.User;
import com.lian.javareflect.service.UserService;
import com.lian.javareflect.service.impl.UserServiceImpl;
import javafx.beans.binding.ObjectExpression;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = JavareflectApplication.class)
public class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper  userMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public  void addUser(){
        User user = new User();
        user.setUserName("Ted");
        user.setPassWord("hehe");
        user.setRealName("古天乐");
        userService.add(user);
    }

    @Test
    public void addUser2() throws ClassNotFoundException, NoSuchFieldException {
        User user = new User();
        user.setUserName("Ted");
        user.setPassWord("hehe");
        user.setRealName("古天乐");
        //反射获取类模板
        Class<?> aClass = Class.forName("com.lian.javareflect.service.impl.UserServiceImpl");
        Field mapper = aClass.getDeclaredField("userMapper");
        mapper.setAccessible(true);

        try {
            Method add = aClass.getDeclaredMethod("jdbcAdd", User.class,JdbcTemplate.class);
            try {
                try {
                    UserServiceImpl userService = (UserServiceImpl)aClass.newInstance();
                    mapper.set(userService,this.userMapper);

                    add.invoke(userService,user,jdbcTemplate);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void executeAddMethod(Class<?> clz,String specifiedMethod) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Map<String, Map<String, Object>> methodMapInfo = getMethodMapInfo(clz);
        Map<String, Object> methodInfo = methodMapInfo.get(specifiedMethod);
        List<Object> paramTypes = (List<Object>)methodInfo.get("paramTypes");
        Object methodName = methodInfo.get("methodName");
        System.out.println(methodName.toString());
        Method method = clz.getDeclaredMethod(methodInfo.get("methodName").toString(), new Class[]{Class.forName(paramTypes.get(0).toString()), Class.forName(paramTypes.get(1).toString())});
        Object methodClass = clz.newInstance();
        User user = new User();
        user.setUserName("Ted");
        user.setPassWord("hehe");
        user.setRealName("陈小春");
        method.invoke(methodClass, user, jdbcTemplate);
    }
    /**
     * 通过JDK的反射，获取类的所有信息
     * @param clz   类的Class对象。类的模板
     * @return
     */
    private Map<String,List<String>> getMethodInfo(Class<?> clz){
        Map<String, List<String>> classInfo = new HashMap<>();
        Method[] methods = clz.getDeclaredMethods();
        for(int i=0;i<methods.length;i++){
            ArrayList<String> methodInfo = new ArrayList<>();
            if(methods[i]!=null){
                methodInfo.add("methodName:"+methods[i].getName());
                methodInfo.add("method modifier:"+ Modifier.toString(methods[i].getModifiers()));
                Parameter[] parameters = methods[i].getParameters();
                for (int j=0;j<parameters.length;j++){
                    methodInfo.add(parameters[j].getName()+":"+parameters[j].getType().getSimpleName());
                }
                methodInfo.add("return type:"+methods[i].getReturnType().getSimpleName());
                classInfo.put("method"+(i+1),methodInfo);
            }
        }
        return classInfo;
    }


    @Test
    public void execute(){

        try {
            executeAddMethod(UserServiceImpl.class,"method2");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws NoSuchMethodException {
        getAnnotationInfo();
    }

    /**
     * 通过JDK的反射，获取类的所有信息
     * @param clz   类的Class对象。类的模板
     * @return返回map套map
     */
    private static Map<String,Map<String, Object>> getMethodMapInfo(Class<?> clz){
        Map<String, Map<String, Object>> classInfo = new HashMap<>();
        Method[] methods = clz.getDeclaredMethods();
        for(int i=0;i<methods.length;i++){
            Map<String, Object> methodInfo = new HashMap<>();
            if(methods[i]!=null){
                methodInfo.put("methodName",methods[i].getName());
                methodInfo.put("method modifier",Modifier.toString(methods[i].getModifiers()));
                Parameter[] parameters = methods[i].getParameters();
                ArrayList<Object> paramNames = new ArrayList<>();
                ArrayList<Object> paramTypes = new ArrayList<>();
                for (int j=0;j<parameters.length;j++){
                    paramNames.add(parameters[j].getName());
                    paramTypes.add(parameters[j].getType().getName());
                }
                methodInfo.put("paramNames",paramNames);
                methodInfo.put("paramTypes",paramTypes);
                methodInfo.put("return type",methods[i].getReturnType().getSimpleName());
                classInfo.put("method"+(i+1),methodInfo);
            }
        }
        return classInfo;
    }

    private void printSettersGetters(Class  clz){
        Method[] methods = clz.getMethods();

        for (Method method:methods){
            if(isGetter(method)) System.out.println("getter:"+method);
            if(isSetter(method)) System.out.println("setter:"+method);
        }
    }

    /**
     * 是否是get方法
     * @param method
     * @return
     */
    private boolean isGetter(Method method){
        if(method.getName().startsWith("get")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 是够是set方法
     * @param method
     * @return
     */
    private boolean isSetter(Method method){
        if(method.getName().startsWith("set")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取私有成员
     * @param clz
     * @return
     */
    private List<String> getPrivateMember(Class clz){
        ArrayList<String> privateMembers = new ArrayList<>();
        Field[] declaredFields = clz.getDeclaredFields();
        for(int i=0;i<declaredFields.length;i++){
            privateMembers.add(declaredFields[i].getType()+":"+declaredFields[i].getName());
        }
        return privateMembers;
    }

    /**
     * 获取注解信息
     */
    private static void getAnnotationInfo(){
        Class clz = UserServiceImpl.class;
        Method[] methods = clz.getMethods();
        for(int i=0;i<methods.length;i++){
            Annotation[] declaredAnnotations = methods[i].getDeclaredAnnotations();
            for(Annotation annotation:declaredAnnotations){
                if(annotation instanceof TestAnnotation){
                    TestAnnotation testAnnotation = (TestAnnotation) annotation;
                    System.out.println("name:"+testAnnotation.name());
                    System.out.println("value:"+testAnnotation.value());
                }
            }
        }

    }
}
