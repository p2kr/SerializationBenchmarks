// Factory for generating test data similar to the Java version

export class TestDataFactory {
    // Create a nested object
    static createNestedPojo(id) {
        return {
            field1: `Field1-${id}`,
            field2: `Field2-${id}`,
            field3: `Field3-${id}`,
            field4: `Field4-${id}`,
            field5: `Field5-${id}`,
            longField1: Math.floor(Math.random() * Number.MAX_SAFE_INTEGER),
            intField1: Math.floor(Math.random() * 1000000),
            doubleField1: Math.random() * 10000,
            deepNested: {
                data: `DeepData-${id}`,
                blob: Buffer.from(`BlobData-${id}`)
            }
        };
    }

    // Create a large POJO similar to Java's LargePojo
    static createLargePojo(index) {
        const pojo = {
            // Nested references (20 fields)
            ref1: this.createNestedPojo(index * 20 + 1),
            ref2: this.createNestedPojo(index * 20 + 2),
            ref3: this.createNestedPojo(index * 20 + 3),
            ref4: this.createNestedPojo(index * 20 + 4),
            ref5: this.createNestedPojo(index * 20 + 5),
            ref6: this.createNestedPojo(index * 20 + 6),
            ref7: this.createNestedPojo(index * 20 + 7),
            ref8: this.createNestedPojo(index * 20 + 8),
            ref9: this.createNestedPojo(index * 20 + 9),
            ref10: this.createNestedPojo(index * 20 + 10),
            ref11: this.createNestedPojo(index * 20 + 11),
            ref12: this.createNestedPojo(index * 20 + 12),
            ref13: this.createNestedPojo(index * 20 + 13),
            ref14: this.createNestedPojo(index * 20 + 14),
            ref15: this.createNestedPojo(index * 20 + 15),
            ref16: this.createNestedPojo(index * 20 + 16),
            ref17: this.createNestedPojo(index * 20 + 17),
            ref18: this.createNestedPojo(index * 20 + 18),
            ref19: this.createNestedPojo(index * 20 + 19),
            ref20: this.createNestedPojo(index * 20 + 20)
        };

        // Integer fields (10 fields: 21-30)
        for (let i = 21; i <= 30; i++) {
            pojo[`field${i}`] = Math.floor(Math.random() * 1000000);
        }

        // Long fields (10 fields: 31-40)
        for (let i = 31; i <= 40; i++) {
            pojo[`field${i}`] = Math.floor(Math.random() * Number.MAX_SAFE_INTEGER);
        }

        // Double fields (10 fields: 41-50)
        for (let i = 41; i <= 50; i++) {
            pojo[`field${i}`] = Math.random() * 10000;
        }

        // Boolean fields (10 fields: 51-60)
        for (let i = 51; i <= 60; i++) {
            pojo[`field${i}`] = i % 2 === 0;
        }

        // Float fields (10 fields: 61-70)
        for (let i = 61; i <= 70; i++) {
            pojo[`field${i}`] = Math.random() * 100;
        }

        // Short fields (10 fields: 71-80)
        for (let i = 71; i <= 80; i++) {
            pojo[`field${i}`] = Math.floor(Math.random() * 32767);
        }

        // Byte fields (10 fields: 81-90)
        for (let i = 81; i <= 90; i++) {
            pojo[`field${i}`] = Math.floor(Math.random() * 255);
        }

        // Char fields (10 fields: 91-100)
        for (let i = 91; i <= 100; i++) {
            pojo[`field${i}`] = Math.floor(Math.random() * 65535);
        }

        // Integer/Long/Double/Boolean fields (101-112)
        pojo.field101 = Math.floor(Math.random() * 1000000);
        pojo.field102 = Math.floor(Math.random() * 1000000);
        pojo.field103 = Math.floor(Math.random() * 1000000);
        pojo.field104 = Math.floor(Math.random() * 1000000);
        pojo.field105 = Math.floor(Math.random() * 1000000);
        pojo.field106 = Math.floor(Math.random() * Number.MAX_SAFE_INTEGER);
        pojo.field107 = Math.floor(Math.random() * Number.MAX_SAFE_INTEGER);
        pojo.field108 = Math.floor(Math.random() * Number.MAX_SAFE_INTEGER);
        pojo.field109 = Math.random() * 10000;
        pojo.field110 = Math.random() * 10000;
        pojo.field111 = Math.random() > 0.5;
        pojo.field112 = Math.random() > 0.5;

        // List and Map fields (113-115)
        pojo.field113 = ['item1', 'item2', 'item3', 'item4'];
        pojo.field114 = [1, 2, 3, 4, 5];
        pojo.field115 = {
            key1: 'value1',
            key2: 'value2',
            key3: 'value3'
        };

        // String fields (116-150)
        for (let i = 116; i <= 150; i++) {
            pojo[`field${i}`] = `String_${i}_${Math.random().toString(36).substring(7)}`;
        }

        return pojo;
    }

    // Create a list of large POJOs
    static createPojoList(size) {
        const list = [];
        for (let i = 0; i < size; i++) {
            list.push(this.createLargePojo(i));
        }
        return list;
    }

    // Create a version with some null fields
    static createPojoListWithNulls(size) {
        const list = this.createPojoList(size);
        // Add some nulls randomly
        list.forEach((pojo, idx) => {
            if (idx % 3 === 0) {
                pojo.ref1 = null;
                pojo.ref5 = null;
                pojo.ref10 = null;
            }
        });
        return list;
    }
}
