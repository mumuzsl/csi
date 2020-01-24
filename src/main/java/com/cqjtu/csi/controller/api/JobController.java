package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.repository.JobRepository;
import com.cqjtu.csi.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mumu
 * @date 2020/1/20
 */
@RestController
@RequestMapping(value = "/job")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault Pageable pageable) {
        return null == keyword ? jobService.pageBy(pageable) : jobService.search(keyword, pageable);
    }
}
