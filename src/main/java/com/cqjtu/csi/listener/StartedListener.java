package com.cqjtu.csi.listener;

import cn.hutool.core.io.FileUtil;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(StartedListener.class);

    public StartedListener(UserService userService) {this.userService = userService;}

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.initAdminInDataBase();
        this.createDir();
    }

    private void initAdminInDataBase() {
        try {
            log.info("check admin...");
            userService.getByLoginNameOfNonNull("root");
            log.info("admin exists.");
        } catch (NonUniqueResultException e) {
            log.info("admin not only, place delete admin info in db.");
        } catch (NotFoundException e) {
            log.info("admin not exists.");
            log.info("create admin...");
            userService.registerAdmin();
            log.info("create admin finish.");
        }
    }

    private void createDir() {
        FileUtil.mkdir(CsiConst.DOCUMENT_DIR);
        log.info("create document dir");
    }
}
