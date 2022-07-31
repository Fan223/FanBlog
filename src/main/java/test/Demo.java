package test;

import fan.fanblog.menu.entity.MenuDO;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.utils.MapStructRule;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
    }
}

@Data
@AllArgsConstructor
class Person {
    private String name;
    private List<String> age;
}
