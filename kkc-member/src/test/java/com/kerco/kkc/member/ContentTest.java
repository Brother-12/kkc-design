package com.kerco.kkc.member;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentTest {
    public static void main(String[] args) {
        String content = "# SPI机制详解\n" +
                "> SPI（Service Provider Interface），是JDK内置的一种 **服务提供发现机制**，可以用来启用框架扩展和替换组件，主要是被框架的开发人员使用。\n" +
                "## 什么是SPI机制\n" +
                "SPI（Service Provider Interface），是JDK内置的一种`服务提供发现机制`，可以用来启用框架扩展和替换组件，主要是被框架的开发人员使用，比如java.sql.Driver接口，其他不同厂商可以针对同一接口做出不同的实现，MySQL和PostgreSQL都有不同的实现提供给用户，而Java的SPI机制可以为某个接口寻找服务实现。Java中**SPI机制主要思想是将装配的控制权移到程序之外**，在模块化设计中这个机制尤其重要，其核心思想就是 **解耦**。\n" +
                "SPI整体机制图如下：\n" +
                "![批注 20230114 103257.jpg](https://kkc-picture.oss-cn-shenzhen.aliyuncs.com/2023-01-18/asdasd/批注2023-01-14103257.jpg)当服务的提供者提供了一种接口的实现之后，需要在classpath下的`META-INF/services/`目录里创建一个以服务接口命名的文件，这个文件里的内容就是这个接口的具体的实现类。当其他的程序需要这个服务的时候，就可以通过查找这个jar包（一般都是以jar包做依赖）的`META-INF/services/`中的配置文件，配置文件中有接口的具体实现类名，可以根据这个类名进行加载实例化，就可以使用该服务了。JDK中查找服务的实现的工具类是：`java.util.ServiceLoader`";

        //第一张图片处理
        String regex = "https://kkc-picture.oss-cn-shenzhen.aliyuncs.com/(.*?)(\\.jpg|\\.jpeg|\\.png)";
        Pattern p = Pattern.compile(regex);
        Matcher ma = p.matcher(content);

        if(ma.find()){
            String group = ma.group();
            System.out.println(group);
        }

        //文本处理
        String regexs = "(#|>|\\*\\*|`| |!\\[.*?\\]|\\(https.*?\\))";
        Pattern compile = Pattern.compile(regexs);
        Matcher matcher = compile.matcher(content);
        if(matcher.find()){
            String s = matcher.replaceAll("");
            System.out.println("----------------");
            System.out.println(s);
        }
    }
}
