package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.model.entity.Job;
import com.cqjtu.csi.repository.JobRepository;
import com.cqjtu.csi.service.JobService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class JobServiceImpl extends AbstractCrudService<Job, Integer> implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository repository) {
        super(repository);
        this.jobRepository = repository;
    }

    @Override
    public Page<Job> search(String keyword, Pageable pageable) {
        return jobRepository.findByNameContaining(keyword, pageable);
    }
}
