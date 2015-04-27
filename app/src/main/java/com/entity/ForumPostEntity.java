package com.entity;

import java.sql.Timestamp;





public class ForumPostEntity {
    private int pid;
    private int fid;
    private int tid;
    private byte first;
    private String author;
    private int authorid;
    private String subject;
    private Timestamp dateline;
    private String useip;
    private Byte invisible;
    private Byte anonymous;
    private Byte usesig;
    private Byte htmlon;
    private Byte bbcodeoff;
    private Byte smileyoff;
    private Byte parseurloff;
    private Byte attachment;
    private Short rate;
    private Byte ratetimes;
    private Integer status;
    private String tags;
    private Byte comment;
    private Integer replycredit;
    private int position;
    private int rpid;
    
    public ForumPostEntity() {
	}
    
    
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }




    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }




    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }




    public byte getFirst() {
        return first;
    }

    public void setFirst(byte first) {
        this.first = first;
    }




    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    



    public Timestamp getDateline() {
        return dateline;
    }

    public void setDateline(Timestamp dateline) {
        this.dateline = dateline;
    }

    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getUseip() {
        return useip;
    }

    public void setUseip(String useip) {
        this.useip = useip;
    }



    public Byte getInvisible() {
        return invisible;
    }

    public void setInvisible(Byte invisible) {
        this.invisible = invisible;
    }



    public Byte getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Byte anonymous) {
        this.anonymous = anonymous;
    }



    public Byte getUsesig() {
        return usesig;
    }

    public void setUsesig(Byte usesig) {
        this.usesig = usesig;
    }



    public Byte getHtmlon() {
        return htmlon;
    }

    public void setHtmlon(Byte htmlon) {
        this.htmlon = htmlon;
    }



    public Byte getBbcodeoff() {
        return bbcodeoff;
    }

    public void setBbcodeoff(Byte bbcodeoff) {
        this.bbcodeoff = bbcodeoff;
    }



    public Byte getSmileyoff() {
        return smileyoff;
    }

    public void setSmileyoff(Byte smileyoff) {
        this.smileyoff = smileyoff;
    }



    public Byte getParseurloff() {
        return parseurloff;
    }

    public void setParseurloff(Byte parseurloff) {
        this.parseurloff = parseurloff;
    }



    public Byte getAttachment() {
        return attachment;
    }

    public void setAttachment(Byte attachment) {
        this.attachment = attachment;
    }



    public Short getRate() {
        return rate;
    }

    public void setRate(Short rate) {
        this.rate = rate;
    }



    public Byte getRatetimes() {
        return ratetimes;
    }

    public void setRatetimes(Byte ratetimes) {
        this.ratetimes = ratetimes;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }



    public Byte getComment() {
        return comment;
    }

    public void setComment(Byte comment) {
        this.comment = comment;
    }



    public Integer getReplycredit() {
        return replycredit;
    }

    public void setReplycredit(Integer replycredit) {
        this.replycredit = replycredit;
    }



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



    public int getRpid() {
        return rpid;
    }

    public void setRpid(int rpid) {
        this.rpid = rpid;
    }

   
}
