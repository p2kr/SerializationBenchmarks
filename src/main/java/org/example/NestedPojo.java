package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NestedPojo implements Serializable {
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private Long longField1;
    private Integer intField1;
    private Double doubleField1;
    private DeepNestedPojo deepNested;
}
