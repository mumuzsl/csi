package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.model.dto.DocumentDTO;
import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.param.DocumentParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import com.cqjtu.csi.utils.FileUtils;
import com.cqjtu.csi.utils.FileUtils.FileSuffixFilter;
import com.cqjtu.csi.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Api("文档管理接口")
@RestController
@RequestMapping(value = "/api/document")
public class DocumentController {

    private final DocumentService documentService;
    private final UserService userService;
    private final FileSuffixFilter suffixFilter = FileUtils.buildSuffixFilter().adds("doc", "docx", "pdf");

    public DocumentController(DocumentService documentService, UserService userService) {
        this.documentService = documentService;
        this.userService = userService;
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault Pageable pageable) {
        return null == keyword ? documentService.pageBy(pageable) : documentService.search(keyword, PageUtils.of(PageUtils.of(pageable)));
    }

    @GetMapping(value = "{id:\\d+}")
    public DocumentDTO one(@PathVariable("id") Integer id) {
        return documentService.convertById(id);
    }

    /**
     * 添加（插入）
     * <p>
     * //     * @param title
     * //     * @param remark
     * //     * @param userId
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PutMapping(value = "a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(
//            @RequestParam(value = "title", required = false) String title,
//                               @RequestParam(value = "remark", required = false) String remark,
//                               @RequestParam(value = "userId", required = false) Integer userId,
                               @RequestPart("file") MultipartFile file) throws IOException {
        Document document = new Document();
//        document.setTitle(title);
//        document.setRemark(remark);
//        document.setUserId(userId);
        document.setFilename(check(file));
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
     * @param id 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update")
    public BaseResponse update(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "remark", required = false) String remark,
                               @RequestParam(value = "userId", required = false) Integer userId,
                               @RequestParam("file") MultipartFile file) throws IOException {
        Document document = new Document();
        document.setTitle(title);
        document.setRemark(remark);
        document.setUserId(userId);
        document.setId(id);
        document.setFilename(check(file));
        documentService.update(document);
        return BaseUtils.updateSucceed();
    }

    public String check(MultipartFile file) throws IOException {
        String fn = suffixFilter.check(file.getOriginalFilename(), new BadRequestException("不接受该格式的文档"));
        File dest = new File(CsiConst.DOCUMENT_DIR + fn);
        file.transferTo(dest);
        return fn;
    }
}
