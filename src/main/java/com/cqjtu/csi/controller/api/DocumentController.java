package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.utils.BaseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Api("文档管理接口")
@RestController
@RequestMapping(value = "/api/document")
public class DocumentController {

    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault Pageable pageable) {
        return null == keyword ? documentService.pageBy(pageable) : documentService.search(keyword, pageable);
    }

    /**
     * 添加（插入）
     *
     * @param document 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody Document document) {
//        documentService.insert(JSON.parseObject(json, Document.class));
        documentService.insert(document);
        return BaseUtils.insertSucceed();
    }

    /**
     * 删除
     *
     * @param ids 需要删除的id集
     * @return 删除成功BaseResponse
     */
    @PostMapping("a/delete")
    public BaseResponse delete(@RequestBody List<Integer> ids) {
//        Collection<Integer> collection = JSON.parseObject(ids, new TypeReference<Collection<Integer>>() {});
        documentService.removeInBetch(ids);
        return BaseUtils.deleteSucceed();
    }

    /**
     * 更新
     *
     * @param document 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update")
    public BaseResponse update(@RequestBody Document document) {
        documentService.update(document);
        return BaseUtils.updateSucceed();
    }
}
