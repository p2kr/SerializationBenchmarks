# Serialization Benchmarks

A comprehensive benchmarking suite that compares the performance and size characteristics of popular serialization libraries across multiple languages:
- **Java**: Serialization benchmarks for Jackson (JSON), Gson (JSON), MessagePack, and Protocol Buffers (Protobuf)
- **JavaScript**: Serialization and deserialization benchmarks for JSON, MessagePack, and Protocol Buffers

## Overview

This project provides empirical performance data for different serialization frameworks by testing them against complex data structures with varying characteristics. The benchmarks measure both serialization and deserialization performance along with output size, making it easier to choose the right serialization strategy for your use case.

### Language-Specific Focus

- **Java** (in `src/`): Tests **serialization** performance - converting objects to bytes/strings
- **JavaScript** (in `js/`): Tests **serialization and deserialization** performance - converting objects to bytes/strings and vice versa

This dual approach reflects real-world use cases where backend services (Java) serialize data that frontend applications (JavaScript) deserialize.

<!-- BENCHMARK_RESULTS_START -->
## 📊 Latest Benchmark Results

> Benchmark results will be automatically updated by GitHub Actions after each run.
> To see the latest results, run the benchmarks manually or wait for the scheduled weekly run.

<!-- BENCHMARK_RESULTS_END -->

## Features

### Java Serialization Benchmarks

- **Multiple Serializers Tested:**
  - Jackson (JSON)
  - Gson (JSON)
  - MessagePack (binary JSON)
  - Protocol Buffers (Protobuf)

- **Comprehensive Test Scenarios:**
  - Serialization with null fields included
  - Serialization with null fields excluded
  - Large POJOs with 150+ fields
  - Nested object structures
  - Deep nested hierarchies

- **Performance Metrics:**
  - Average serialization time (milliseconds)
  - Output size (bytes)
  - Percentage comparison against baseline (Jackson)

### JavaScript Serialization & Deserialization Benchmarks

- **Multiple Libraries Tested:**
  - JSON (native)
  - MessagePack (msgpack5)
  - Protocol Buffers (protobufjs)

- **Performance Metrics:**
  - Average serialization time (milliseconds)
  - Average deserialization time (milliseconds)
  - Total time summary for both operations
  - Data size (bytes)
  - Percentage comparison against baseline (JSON)

### Best Practices (Both Languages)

- Warmup iterations to ensure JIT/V8 optimization
- Multiple benchmark iterations for accuracy
- Concurrent test execution support (Java)
- Formatted table output for easy comparison
- GC hints between test phases

## Prerequisites

### For Java Benchmarks
- Java 17 or higher
- Maven 3.6 or higher

### For JavaScript Benchmarks
- Node.js 14.0 or higher
- npm or yarn

## Dependencies

The project uses the following libraries:

- **Jackson** 2.17.2 - High-performance JSON processor
- **Gson** 2.11.0 - Google's JSON library
- **MessagePack** 0.9.11 - Efficient binary serialization format
- **Protocol Buffers** 4.29.3 - Google's language-neutral data serialization
- **Lombok** 1.18.34 - Reduces boilerplate code
- **JUnit Jupiter** 6.0.2 - Testing framework

## Project Structure

```
SerializationBenchmarks/
├── src/                                # Java benchmarks
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── LargePojo.java          # POJO with 150+ fields
│   │   │   ├── NestedPojo.java         # Medium complexity nested object
│   │   │   ├── DeepNestedPojo.java     # Deep nesting structure
│   │   │   └── models/                 # Additional model classes
│   │   └── proto/
│   │       ├── large_pojo.proto        # Protobuf schema for LargePojo
│   │       ├── nested.proto            # Protobuf schema for NestedPojo
│   │       └── deep_nested.proto       # Protobuf schema for DeepNestedPojo
│   └── test/
│       └── java/org/example/
│           ├── SerializationTest.java  # Main benchmark suite
│           └── util/
│               ├── ProtobufConverter.java  # Converter utilities
│               └── TestDataFactory.java    # Test data generators
├── js/                                 # JavaScript benchmarks
│   ├── benchmark.js                    # Main benchmark suite
│   ├── testDataFactory.js              # Test data generation
│   ├── protobufConverter.js            # Protobuf conversion utilities
│   ├── proto/                          # Protobuf schemas
│   │   └── messages.proto
│   └── README.md                       # JavaScript-specific docs
├── docs/                               # Documentation
│   ├── CROSS_VERIFICATION_REPORT.md    # Cross-language verification
│   ├── JS_BENCHMARKS_SUMMARY.md        # JavaScript benchmark summary
│   ├── PROTOBUF_ADDITION_SUMMARY.md    # Protobuf implementation details
│   └── QUICK_REFERENCE.md              # Quick reference guide
├── .github/
│   └── workflows/
│       └── benchmark.yml               # GitHub Actions workflow
├── package.json                        # Node.js dependencies
├── pom.xml                             # Maven configuration
├── LICENSE.md                          # License information
├── SECURITY.md                         # Security policy
└── README.md                           # This file
```

### Package Structure

```
org.example/
├── LargePojo.java              # Main test POJO (150+ fields)
├── NestedPojo.java             # Nested object structure
├── DeepNestedPojo.java         # Deep nesting test
└── proto/                      # Generated Protocol Buffer classes
    ├── LargePojoProto.java
    ├── NestedProto.java
    └── DeepNestedProto.java

org.example.util/
├── ProtobufConverter.java      # POJO to Protobuf conversion
└── TestDataFactory.java        # Test data generation
```

### Key Classes

- **SerializationTest.java**: Main benchmark suite with JUnit tests
- **TestDataFactory.java**: Creates test data with various characteristics
- **ProtobufConverter.java**: Converts POJOs to Protocol Buffer messages

## Getting Started

### Environment Setup

#### Required Software

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   java -version
   ```
   Should output version 17 or higher.

2. **Maven 3.6+** (Optional - project includes Maven wrapper)
   ```bash
   mvn -version
   ```

3. **IDE Recommendations**
   - IntelliJ IDEA (recommended - best Maven and Lombok support)
   - Eclipse with Maven and Lombok plugins
   - VS Code with Java Extension Pack

### Initial Setup Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd SerializationBenchmarks
   ```

2. **Enable Lombok in your IDE**
   
   **IntelliJ IDEA:**
   - Install Lombok plugin: File → Settings → Plugins → Search "Lombok" → Install
   - Enable annotation processing: File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing

   **Eclipse:**
   - Download lombok.jar from https://projectlombok.org/
   - Run: `java -jar lombok.jar`
   - Follow the installer to add Lombok to Eclipse

3. **Import the project**
   - IntelliJ: File → Open → Select `pom.xml` → Open as Project
   - Eclipse: File → Import → Maven → Existing Maven Projects

## Building the Project

### Clean Build

```bash
# Unix/Linux/Mac
./mvnw clean compile

# Windows (PowerShell or CMD)
.\mvnw.cmd clean compile
```

This will:
- Download all dependencies
- Compile Java source files
- Generate Protocol Buffer classes from `.proto` files
- Place compiled classes in `target/classes/`

### Verify Build

```bash
# Unix/Linux/Mac
./mvnw clean verify

# Windows
.\mvnw.cmd clean verify
```

This runs the complete build lifecycle including tests.

## Running Tests

### Run All Tests

```bash
# Unix/Linux/Mac
./mvnw test

# Windows
.\mvnw.cmd test
```

### Run Specific Test Class

```bash
# Unix/Linux/Mac
./mvnw test -Dtest=SerializationTest

# Windows
.\mvnw.cmd test -Dtest=SerializationTest
```

### Run Specific Test Method

```bash
# Unix/Linux/Mac
./mvnw test -Dtest=SerializationTest#benchmarkSerializersWithNullFieldsSerialized

# Windows
.\mvnw.cmd test "-Dtest=SerializationTest#benchmarkSerializersWithNullFieldsSerialized"
```

### Run with Specific JVM Options

```bash
# Unix/Linux/Mac
./mvnw test -DargLine="-Xms2G -Xmx2G -XX:+UseG1GC"

# Windows
.\mvnw.cmd test "-DargLine=-Xms2G -Xmx2G -XX:+UseG1GC"
```

## Running JavaScript Benchmarks

### Install Dependencies

First time setup:

```bash
npm install
```

Or with yarn:

```bash
yarn install
```

### Run Benchmarks

Basic execution:

```bash
npm run benchmark
```

Or directly:

```bash
node js/benchmark.js
```

With GC control (recommended for more accurate results):

```bash
node --expose-gc js/benchmark.js
```

### Output Example

```
╔════════════════════════════════════════╗
║  JavaScript Deserialization Benchmarks ║
╔════════════════════════════════════════╗

========================================
  Deserialization Benchmark - DESERIALIZATION
  (List of 20 LargePojo objects)
========================================

┌─────────────┬──────────────┬────────────┬─────────────────┬────────────┐
│ Deserializer│ Avg Time (ms)│  % Diff    │  Size (bytes)   │  % Diff    │
├─────────────┼──────────────┼────────────┼─────────────────┼────────────┤
│ JSON.parse  │       1.0227 │      0.0%  │        185,219  │      0.0%  │
│ MessagePack │       4.5923 │   +349.0%  │        125,421  │    -32.3%  │
│ Protobuf    │       0.6576 │    -35.7%  │         71,586  │    -61.4%  │
└─────────────┴──────────────┴────────────┴─────────────────┴────────────┘
```

For more details on JavaScript benchmarks, see [js/README.md](js/README.md).

## Understanding the Output

The benchmark produces formatted tables showing comparative performance:

```
========================================
  Serialization Benchmark - WITH NULLS
  (List of 20 LargePojo objects)
========================================

┌─────────────┬──────────────┬────────────┬─────────────────┬────────────┐
│ Serializer  │ Avg Time (ms)│  % Diff    │  Size (bytes)   │  % Diff    │
├─────────────┼──────────────┼────────────┼─────────────────┼────────────┤
│ Jackson     │       0.5000 │       0.0% │         100,000 │       0.0% │
│ Gson        │       0.6000 │      +20.0% │         100,500 │      +0.5% │
│ MessagePack │       0.4500 │      -10.0% │          45,000 │     -55.0% │
│ Protobuf    │       0.2000 │      -60.0% │          35,000 │     -65.0% │
└─────────────┴──────────────┴────────────┴─────────────────┴────────────┘
```

### Interpreting Results

- **Avg Time (ms)**: Average time taken to serialize the test dataset
- **% Diff (Time)**: Percentage difference compared to Jackson (baseline)
  - Negative values indicate faster performance
  - Positive values indicate slower performance
- **Size (bytes)**: Total size of serialized output
- **% Diff (Size)**: Percentage difference in size compared to Jackson
  - Negative values indicate smaller output
  - Positive values indicate larger output

## Benchmark Configuration

You can customize the benchmark parameters in `SerializationTest.java`:

```java
private static final int WARMUP_ITERATIONS = 100;      // JIT warmup iterations
private static final int BENCHMARK_ITERATIONS = 1000;  // Actual benchmark runs
private static final int LIST_SIZE = 20;               // Number of objects to serialize
```

**Guidelines:**
- **WARMUP_ITERATIONS**: 50-200 is usually sufficient for JIT warmup
- **BENCHMARK_ITERATIONS**: Higher values (1000-10000) give more precise averages
- **LIST_SIZE**: Test with realistic dataset sizes for your use case

### Changing Test Execution Mode

```java
// Current: Parallel execution
@Execution(ExecutionMode.CONCURRENT)

// Alternative: Sequential execution (more stable results)
@Execution(ExecutionMode.SAME_THREAD)
```

## Working with Protocol Buffers

### Protocol Buffer Files Location

All `.proto` files are located in: `src/main/proto/`

### Modifying Protocol Buffer Schemas

1. **Edit the `.proto` file**
   ```protobuf
   syntax = "proto3";
   package org.example.proto;
   option java_package = "org.example.proto";
   option java_outer_classname = "YourProtoClass";
   
   message YourMessage {
       string field1 = 1;
       int32 field2 = 2;
   }
   ```

2. **Regenerate Java classes**
   ```bash
   # Unix/Linux/Mac
   ./mvnw protobuf:compile
   
   # Windows
   .\mvnw.cmd protobuf:compile
   ```

3. **Generated classes location**
   - Source: `target/generated-sources/protobuf/java/`
   - Compiled: `target/classes/org/example/proto/`

### Protocol Buffer Best Practices

- Use `proto3` syntax (more efficient, better language support)
- Number fields sequentially starting from 1
- Use meaningful field names matching your POJOs
- Add `java_package` and `java_outer_classname` options
- Document complex messages with comments

## Use Cases

This benchmark helps you choose the right serialization library for:

1. **REST APIs**: When JSON readability matters (Jackson, Gson)
2. **Microservices Communication**: When size and speed matter (MessagePack, Protobuf)
3. **Data Storage**: When compact representation is critical (Protobuf)
4. **Real-time Systems**: When low latency is essential (Protobuf)
5. **Cross-language Communication**: When language interoperability matters (Protobuf)

## Key Findings

Based on typical benchmark runs:

- **Speed**: Protobuf is typically the fastest, followed by MessagePack
- **Size**: Protobuf produces the most compact output, MessagePack is second
- **Readability**: JSON (Jackson/Gson) wins for human-readable output
- **Null Handling**: Excluding nulls significantly reduces JSON output size
- **Binary vs Text**: Binary formats (MessagePack, Protobuf) are more efficient for large datasets

## Extending the Benchmarks

### Step 1: Create a New POJO

Create a new Java class in `src/main/java/org/example/`:

```java
package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YourPojo implements Serializable {
    private String field1;
    private int field2;
    private List<String> field3;
}
```

### Step 2: Create Protocol Buffer Schema

Create `your_pojo.proto` in `src/main/proto/`:

```protobuf
syntax = "proto3";
package org.example.proto;
option java_package = "org.example.proto";
option java_outer_classname = "YourPojoProto";

message YourPojo {
    string field1 = 1;
    int32 field2 = 2;
    repeated string field3 = 3;
}

message YourPojoList {
    repeated YourPojo items = 1;
}
```

### Step 3: Add Converter Methods

Add to `src/test/java/org/example/util/ProtobufConverter.java`:

```java
public static YourPojoProto.YourPojo convertToProto(YourPojo pojo) {
    return YourPojoProto.YourPojo.newBuilder()
        .setField1(pojo.getField1())
        .setField2(pojo.getField2())
        .addAllField3(pojo.getField3())
        .build();
}

public static YourPojoProto.YourPojoList convertListToProto(List<YourPojo> list) {
    return YourPojoProto.YourPojoList.newBuilder()
        .addAllItems(list.stream()
            .map(ProtobufConverter::convertToProto)
            .collect(Collectors.toList()))
        .build();
}
```

### Step 4: Add Test Data Factory

Add to `src/test/java/org/example/util/TestDataFactory.java`:

```java
public static YourPojo createYourPojo() {
    return new YourPojo(
        "test value",
        42,
        Arrays.asList("item1", "item2", "item3")
    );
}

public static List<YourPojo> createYourPojoList(int size) {
    return IntStream.range(0, size)
        .mapToObj(i -> createYourPojo())
        .collect(Collectors.toList());
}
```

### Step 5: Add Benchmark Methods

Add to `SerializationTest.java`:

```java
private static List<YourPojo> yourPojoTestData;
private static YourPojoProto.YourPojoList yourPojoProtobufData;

@BeforeAll
static void setUp() {
    // ...existing setup...
    yourPojoTestData = TestDataFactory.createYourPojoList(LIST_SIZE);
    yourPojoProtobufData = ProtobufConverter.convertListToProto(yourPojoTestData);
}

@Test
@DisplayName("Benchmark YourPojo serialization")
public void benchmarkYourPojo() throws Exception {
    // Add benchmark logic similar to existing tests
}
```

### Step 6: Compile and Test

```bash
# Unix/Linux/Mac
./mvnw clean test

# Windows
.\mvnw.cmd clean test
```

## Adding New Serialization Libraries

1. Add dependency to `pom.xml`
2. Create initialization code in `@BeforeAll` method
3. Add benchmark method following the existing pattern
4. Update benchmark suite to include new serializer

## Troubleshooting

### Problem: Protocol Buffer Classes Not Generated

**Solution:**
```bash
# Unix/Linux/Mac
./mvnw clean compile

# Windows
.\mvnw.cmd clean compile
```

If still failing, check:
- `.proto` files have correct syntax
- `protobuf-maven-plugin` is configured in `pom.xml`
- Internet connection (downloads protoc compiler)

### Problem: Lombok Annotations Not Working

**Solution:**
1. Install Lombok plugin in your IDE
2. Enable annotation processing in IDE settings
3. Rebuild project
4. Invalidate caches and restart IDE

### Problem: OutOfMemoryError During Tests

**Solution:**
```bash
# Increase heap size
# Unix/Linux/Mac
./mvnw test -DargLine="-Xms4G -Xmx4G"

# Windows
.\mvnw.cmd test "-DargLine=-Xms4G -Xmx4G"
```

### Problem: Inconsistent Benchmark Results

**Causes & Solutions:**
1. **Background processes**: Close unnecessary applications
2. **Thermal throttling**: Ensure good cooling
3. **Insufficient warmup**: Increase `WARMUP_ITERATIONS`
4. **GC interference**: Use consistent GC settings
5. **Small sample size**: Increase `BENCHMARK_ITERATIONS`

### Problem: Maven Wrapper Not Executable

**Unix/Linux/Mac:**
```bash
chmod +x mvnw
```

**Windows:**
Should work by default. If not, use:
```cmd
mvnw.cmd
```

## Performance Tips

1. **Always include warmup iterations** - JIT compilation affects initial runs
2. **Use consistent test data** - Initialize once in `@BeforeAll`
3. **Measure both time and size** - Both metrics are important
4. **Run multiple iterations** - Average out system variance
5. **Consider GC impact** - Suggest GC between tests for cleaner results
6. **Reuse serializer instances** - Don't create new instances per iteration
7. **Use appropriate data structures** - Lists vs Arrays vs Sets
8. **Consider null handling** - Excluding nulls can significantly reduce size
9. **Test realistic data** - Use data that matches your production scenarios

## Continuous Integration

### GitHub Actions Automation

This project includes automated benchmark execution via GitHub Actions. The workflow:

- **Automatically runs** on push to main/master branches
- **Scheduled execution** weekly on Sundays at 00:00 UTC
- **Manual trigger** available via workflow dispatch
- **Updates README** with latest benchmark results automatically

#### Workflow Configuration

The workflow (`.github/workflows/benchmark.yml`) performs:

1. Sets up Java 17 and Node.js 18 environments
2. Installs all dependencies (Maven, npm)
3. Runs Java benchmarks via Maven
4. Runs JavaScript benchmarks with Node.js
5. Parses benchmark output and extracts results
6. Updates README.md with formatted benchmark tables
7. Commits and pushes changes automatically
8. Uploads artifacts for historical tracking

#### Viewing Benchmark Results

After each workflow run:
- Check the README.md for updated benchmark tables
- View artifacts in the Actions tab for raw output
- Compare results across different runs

#### Manual Workflow Trigger

1. Go to the **Actions** tab in GitHub
2. Select **Run Benchmarks and Update README**
3. Click **Run workflow**
4. Choose the branch and click **Run workflow**

### Benchmark Result Format

The README automatically displays:
- **Java benchmarks**: WITH NULLS and WITHOUT NULLS configurations
- **JavaScript benchmarks**: Serialization and Deserialization metrics
- **Total times**: Aggregated performance metrics
- **Timestamp**: When benchmarks were last executed

## Documentation

### Documentation Folder

All detailed documentation is organized in the `docs/` folder:

#### Available Documentation

- **[CROSS_VERIFICATION_REPORT.md](docs/CROSS_VERIFICATION_REPORT.md)**: Cross-language benchmark verification and comparison
- **[JS_BENCHMARKS_SUMMARY.md](docs/JS_BENCHMARKS_SUMMARY.md)**: Detailed JavaScript benchmark results and analysis
- **[PROTOBUF_ADDITION_SUMMARY.md](docs/PROTOBUF_ADDITION_SUMMARY.md)**: Protocol Buffers implementation guide and insights
- **[QUICK_REFERENCE.md](docs/QUICK_REFERENCE.md)**: Quick reference for common tasks and commands

#### Documentation Purpose

The documentation folder provides:
- In-depth analysis of benchmark results
- Implementation details for each serialization library
- Cross-language performance comparisons
- Best practices and optimization tips
- Historical benchmark data and trends

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for:

- Additional serialization libraries
- New test scenarios
- Performance improvements
- Documentation enhancements
- GitHub Actions workflow improvements

### Contribution Guidelines

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Run benchmarks locally to verify
5. Update documentation if needed
6. Commit your changes (`git commit -m 'Add amazing feature'`)
7. Push to the branch (`git push origin feature/amazing-feature`)
8. Open a Pull Request

## Additional Resources

### Documentation Links

- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/) - Test execution
- [Protobuf Maven Plugin](https://www.xolstice.org/protobuf-maven-plugin/) - Protobuf compilation
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/) - Testing framework
- [Lombok Features](https://projectlombok.org/features/) - Code generation

### Performance Analysis Tools

- **JMH** - Java Microbenchmark Harness (consider for more advanced benchmarking)
- **VisualVM** - Profile memory and CPU usage
- **JProfiler** - Commercial profiler for detailed analysis
- **Async Profiler** - Low-overhead sampling profiler

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## References

- [Jackson Documentation](https://github.com/FasterXML/jackson)
- [Gson Documentation](https://github.com/google/gson)
- [MessagePack Specification](https://msgpack.org/)
- [Protocol Buffers Documentation](https://protobuf.dev/)

## Support

For questions, issues, or suggestions, please open an issue on the project repository.

---

**Note**: Benchmark results may vary based on hardware, JVM version, and system load. Always run your own benchmarks for production decision-making.
