package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.service.*;
import com.cqjtu.csi.service.base.CrudService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/2/16
 */
@RestController
@RequestMapping(value = "/api")
public class RootController {

    //    private final NoticeService noticeService;
//    private final DocumentService documentService;
//    private final EmployeeService employeeService;
    private final UserService userService;
    private final DeptService deptService;
    private final JobService jobService;

    private Map<String, CrudService> serviceMap = new HashMap<>(6);

    public RootController(UserService userService,
//                          NoticeService noticeService,
                          JobService jobService,
//                          EmployeeService employeeService,
//                          DocumentService documentService,
                          DeptService deptService) {

//        this.noticeService = noticeService;
//        this.employeeService = employeeService;
//        this.documentService = documentService;
        this.userService = userService;
        this.jobService = jobService;
        this.deptService = deptService;

//        serviceMap.put("notice", noticeService);
//        serviceMap.put("document", documentService);
//        serviceMap.put("employee", employeeService);
        serviceMap.put("user", userService);
        serviceMap.put("dept", deptService);
        serviceMap.put("job", jobService);
    }

    @GetMapping(value = "{table}/{id:\\d+}")
    public Object one(@PathVariable("table") String table, @PathVariable("id") Integer id) {
        return Optional.ofNullable(serviceMap.get(table))
                .map(crudService -> crudService.getOne(id))
                .orElse(null);
    }
}
