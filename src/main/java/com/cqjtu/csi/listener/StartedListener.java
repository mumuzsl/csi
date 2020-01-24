package com.cqjtu.csi.listener;

import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.persistence.NonUniqueResultException;

/**
 * @author mumu
 * @date 2020/1/21
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.initAdminInDB();
    }

    private void initAdminInDB() {
        try {
            System.out.println("check admin...");
            userService.getByLoginNameOfNonNull("root");
            System.out.println("admin exists.");
        } catch (NonUniqueResultException e) {
            System.out.println("admin not only, place delete admin info in db.");
        } catch (NotFoundException e) {
            System.out.println("admin not exists.");
            System.out.println("create admin...");
            userService.registerAdmin();
            System.out.println("create admin finish.");
        }
    }
}
