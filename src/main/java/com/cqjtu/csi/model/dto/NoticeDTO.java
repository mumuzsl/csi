package com.cqjtu.csi.model.dto;

import com.cqjtu.csi.model.dto.base.OutputConverter;
import com.cqjtu.csi.model.entity.Notice;

/**
 * @author mumu
 * @date 2020/2/16
 */
public class NoticeDTO extends BaseDTO implements OutputConverter<NoticeDTO, Notice> {

    private String title;

    private String content;

    private String username;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "NoticeDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
