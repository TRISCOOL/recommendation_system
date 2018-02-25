package io.recommendation.common.vo;

public class ResponseVo {
    private int code;
    private String msg;
    private Object data;

    public static ResponseVo ok(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(Code.SUCCESS.code);
        responseVo.setMsg(Code.SUCCESS.msg);

        return responseVo;
    }

    public static ResponseVo ok(Object object){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(Code.SUCCESS.code);
        responseVo.setMsg(Code.SUCCESS.msg);
        responseVo.setData(object);

        return responseVo;
    }

    public static ResponseVo error(Code code){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(code.code);
        responseVo.setMsg(code.msg);

        return responseVo;
    }

    public static ResponseVo error(Code code,String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(code.code);
        responseVo.setMsg(msg);

        return responseVo;
    }

    public static ResponseVo response(Code code,Object object){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(code.code);
        responseVo.setMsg(code.msg);
        responseVo.setData(object);

        return responseVo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
