package test;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Person person = new Person("张三", new ArrayList<String>(){{
            this.add("1");
            this.add("2");
        }});
        person.getAge().add("3");
        System.out.println(person);
    }
}

@Data
@AllArgsConstructor
class Person {
    private String name;
    private List<String> age;
}
