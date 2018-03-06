package io.recommendation.web.vo;

import io.recommendation.common.bean.Comment;

import java.util.List;

public class CommentVo {
    private Long id;
    private String img;
    private String replyName;
    private String beReplyName;
    private String content;
    private String time;
    private String address;
    private String osname;
    private String browse;
    private List<CommentVo> replyBody;

    //扩展
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getBeReplyName() {
        return beReplyName;
    }

    public void setBeReplyName(String beReplyName) {
        this.beReplyName = beReplyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public List<CommentVo> getReplyBody() {
        return replyBody;
    }

    public void setReplyBody(List<CommentVo> replyBody) {
        this.replyBody = replyBody;
    }

    public static CommentVo createVo(Comment comment){
        CommentVo vo = new CommentVo();
        vo.setAddress("");
        vo.setBeReplyName(comment.getUserName());
        vo.setBrowse("");
        vo.setContent(comment.getContent());
        vo.setId(comment.getId());
        vo.setImg(comment.getUserLogo());
        vo.setReplyName(comment.getUserName());
        vo.setTime(comment.getCreateTime());
        vo.setParentId(comment.getParentId());

        return vo;
    }
}
