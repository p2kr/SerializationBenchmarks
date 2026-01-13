package org.example.util;

import org.example.DeepNestedPojo;
import org.example.LargePojo;
import org.example.NestedPojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataFactory {

    public static List<LargePojo> createPojoList(int size) {
        LargePojo pojo = createPojo();
        return new ArrayList<>(Collections.nCopies(size, pojo));
    }

    public static LargePojo createPojo() {
        LargePojo pojo = new LargePojo();

        // Populate only limited fields (about 30 fields out of 200+)
        // Most fields will remain null

        // Populate some nested objects (5 out of 20)
        pojo.setRef1(createNestedPojo());
        pojo.setRef5(createNestedPojo());
        pojo.setRef10(createNestedPojo());
        pojo.setRef15(createNestedPojo());
        pojo.setRef20(createNestedPojo());

        // Populate some primitive fields
        pojo.setField21(42);
        pojo.setField22(100);
        pojo.setField23(200);
        pojo.setField31(123456789L);
        pojo.setField32(987654321L);
        pojo.setField41(3.14159);
        pojo.setField42(2.71828);
        pojo.setField51(true);
        pojo.setField52(false);
        pojo.setField61(1.23f);
        pojo.setField62(4.56f);
        pojo.setField71((short) 100);
        pojo.setField81((byte) 1);
        pojo.setField91('A');
        pojo.setField92('B');

        // Populate some boxed types
        pojo.setField101(999);
        pojo.setField102(888);
        pojo.setField106(777777L);
        pojo.setField109(9.99);
        pojo.setField111(true);

        // Populate collections
        List<String> stringList = new ArrayList<>();
        stringList.add("Item 1");
        stringList.add("Item 2");
        stringList.add("Item 3");
        pojo.setField113(stringList);

        List<Integer> intList = new ArrayList<>();
        intList.add(10);
        intList.add(20);
        intList.add(30);
        pojo.setField114(intList);

        Map<String, String> map = new HashMap<>();
        map.put("Key1", "Value1");
        map.put("Key2", "Value2");
        pojo.setField115(map);

        // Populate some strings (10 out of 85)
        pojo.setField116("Sample data for field 116");
        pojo.setField120("Sample data for field 120");
        pojo.setField130("Sample data for field 130");
        pojo.setField140("Sample data for field 140");
        pojo.setField150("Sample data for field 150");
        pojo.setField160("Sample data for field 160");
        pojo.setField170("Sample data for field 170");
        pojo.setField180("Sample data for field 180");
        pojo.setField190("Sample data for field 190");
        pojo.setField200("Sample data for field 200");

        return pojo;
    }

    private static NestedPojo createNestedPojo() {
        NestedPojo np = new NestedPojo();
        np.setField1("Nested field 1");
        np.setField2("Nested field 2");
        np.setField3("Nested field 3");
        np.setLongField1(123456789L);
        np.setIntField1(42);
        np.setDoubleField1(3.14159);

        DeepNestedPojo dnp = new DeepNestedPojo();
        dnp.setData("Deep nested data");
        byte[] blob = new byte[500];
        Arrays.fill(blob, (byte) 'A');
        dnp.setBlob(blob);
        np.setDeepNested(dnp);

        return np;
    }
}
