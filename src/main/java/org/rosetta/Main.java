package org.rosetta;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.LifecycleException;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);

        tomcat.start();
        tomcat.getServer().await();
    }
}
