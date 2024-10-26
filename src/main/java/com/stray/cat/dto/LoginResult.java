package com.stray.cat.dto;

public class LoginResult {
    private boolean flag;
    private String msg;
    private String url;

    public LoginResult() {
        super();
    }

    public LoginResult(boolean flag, String msg) {
        super();
        this.flag = flag;
        this.msg = msg;
    }

    public LoginResult(boolean flag, String msg, String url) {
        super();
        this.flag = flag;
        this.msg = msg;
        this.url = url;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl(){return url;}

    private  void setUrl(String url){this.url=url;}
}
