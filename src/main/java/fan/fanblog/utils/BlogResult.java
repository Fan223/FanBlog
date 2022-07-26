package fan.fanblog.utils;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BlogResult implements Serializable {

    private static final long serialVersionUID = -4788095866758688441L;
    private int code;
    private String msg;
    private Object data;

    public static BlogResult success(int code, String msg, Object data) {
        return BlogResult.builder().code(code).msg(msg).data(data).build();
    }

    public static BlogResult success(String msg, Object data) {
        return BlogResult.builder().code(200).msg(msg).data(data).build();
    }

    public static BlogResult success(Object data) {
        return BlogResult.builder().code(200).msg("操作成功").data(data).build();
    }

    public static BlogResult fail(int code, String msg, Object data) {
        return BlogResult.builder().code(code).msg(msg).data(data).build();
    }

    public static BlogResult fail(int code, String msg) {
        return BlogResult.builder().code(code).msg(msg).build();
    }

    public static BlogResult fail(String msg) {
        return BlogResult.builder().code(500).msg(msg).build();
    }
}
