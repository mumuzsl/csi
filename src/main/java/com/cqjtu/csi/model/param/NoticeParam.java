package com.cqjtu.csi.model.param;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.Notice;

import javax.persistence.Column;

/**
 * @author mumu
 * @date 2020/2/16
 */
public class NoticeParam implements InputConverter<Notice> {

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
        return "NoticeParam{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", username=" + username +
                '}';
    }
}
