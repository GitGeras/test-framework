package com.db.gerasin.testframework;

import lombok.Data;
import lombok.SneakyThrows;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Data
public class CommandMBean {
    
    @SneakyThrows
    public CommandMBean() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(this, new ObjectName("command mbeans", "name", "commandMBean"));
    }

    public void doSmthng() {

    }
}
