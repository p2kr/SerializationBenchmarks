# Serialization Benchmarks - Copilot Instructions

## Project Overview
This is a **dual-language benchmarking suite** comparing serialization library performance:
- **Java** (`src/`): Tests **serialization** (object → bytes) for Jackson (JSON), Gson (JSON), MessagePack, and Protobuf
- **JavaScript** (`js/`): Tests **serialization AND deserialization** (object ↔ bytes) for JSON, MessagePack, and Protobuf

The asymmetry is intentional: backend services (Java) serialize data that frontend apps (JavaScript) deserialize.

## Architecture & Data Flow

### Test Data Structure
All benchmarks use identical data structures across languages:
- **LargePojo**: 150+ fields including primitives, boxed types, strings, collections, and 20 nested objects
- **NestedPojo**: Medium-complexity nested structure with 6 fields + DeepNestedPojo
- **DeepNestedPojo**: Deep nesting test case

Key insight: Test data is **sparsely populated** (~30 of 150+ fields) to simulate real-world scenarios with null values. This is why "WITH NULLS" vs "WITHOUT NULLS" benchmarks exist.

### Protobuf Integration
Protobuf requires parallel schemas and converter utilities:
- **Schemas**: `src/main/proto/*.proto` (Java) and `js/proto/messages.proto` (JS)
- **Converters**: `ProtobufConverter.java` and `protobufConverter.js` convert POJOs ↔ Protobuf messages
- **Build**: Java schemas auto-compile via `protobuf-maven-plugin` → `target/generated-sources/protobuf/java/`
- JS uses `protobufjs` runtime loader (no pre-compilation)

When modifying POJOs, update THREE locations: POJO class, proto schema, and converter.

## Critical Developer Workflows

### Running Benchmarks Locally

**Java** (JUnit tests):
```bash
mvn clean test -Dtest=SerializationTest
```

**JavaScript** (with GC control for accuracy):
```bash
node --expose-gc js/benchmark.js
# Or via npm: npm run benchmark
```

### Building After Proto Changes
1. Edit `src/main/proto/*.proto`
2. Run `mvn compile` to regenerate Java classes
3. Update `ProtobufConverter.java` mappings
4. For JS: edit `js/proto/messages.proto` + `protobufConverter.js`

Maven handles protobuf compilation automatically via the `protobuf-maven-plugin` extension.

### Adding New Test POJOs
Follow the existing pattern:
1. Create POJO in `src/main/java/org/example/` with Lombok `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`
2. Create matching `.proto` schema in `src/main/proto/`
3. Add converter methods to `ProtobufConverter.java`
4. Add factory method to `TestDataFactory.java`
5. Add benchmark method to `SerializationTest.java`
6. Mirror in JavaScript: `testDataFactory.js`, `protobufConverter.js`, `benchmark.js`

## Project-Specific Conventions

### Benchmark Methodology (Both Languages)
- **Warmup**: 100 iterations (JIT/V8 optimization)
- **Benchmark**: 1000 iterations (timing accuracy)
- **GC hints**: `System.gc()` (Java) or `--expose-gc` + `global.gc()` (JS) between phases
- **Baseline**: Jackson (Java), JSON (JS) - all comparisons are % diff vs baseline
- **Concurrency**: Java tests run in parallel (`@Execution(ExecutionMode.CONCURRENT)`)

### Mapper Initialization Pattern (Java)
Serializer instances are initialized once in `@BeforeAll` to avoid per-test overhead:
```java
private static ObjectMapper jacksonMapper;
private static ObjectMapper msgpackMapper;
private static Gson gsonWithNulls;
```

### Total Time Tracking
Both languages track cumulative time across iterations:
- Java: `ConcurrentHashMap<String, Double> totalTimes`
- JS: `totalTimes.serialization` and `totalTimes.deserialization` objects

Display via `printTotalTimesSummary()` after main benchmark tables.

### Table Formatting
Both languages use consistent Unicode box-drawing for output:
- Top/Bottom: `┌─┬─┐` / `└─┴─┘`
- Separator: `├─┼─┤`
- Data rows: `│ data │`

When adding columns, update all 5+ table constants (TOP, HEADER, SEPARATOR, BOTTOM, SUMMARY variants).

## Testing & CI/CD

### GitHub Actions Workflows
- **`benchmark.yml`**: Runs benchmarks on push/PR/schedule (weekly), parses output, updates README between `<!-- BENCHMARK_RESULTS_START/END -->` markers
- **`maven.yml`**: Standard Maven CI (not actively used)

Workflow uses inline Node.js scripts to parse benchmark output with regex. If output format changes, update regex patterns in `parse-benchmarks.js` section.

### Verifying Changes
1. Run both Java and JS benchmarks locally
2. Check output formatting (tables should align)
3. Verify percentages are calculated vs correct baseline
4. For proto changes: confirm data round-trips correctly

## External Dependencies

**Java** (Maven):
- Lombok 1.18.34 (compile-time annotation processing)
- Jackson 2.17.2, Gson 2.11.0, MessagePack 0.9.11, Protobuf 4.29.3
- JUnit Jupiter 6.0.2 (test framework)

**JavaScript** (npm):
- `msgpack5` 6.0.2 (MessagePack encoding/decoding)
- `protobufjs` 7.4.0 (runtime proto loading)
- ES modules (`"type": "module"` in package.json)

## Key Files Reference
- `SerializationTest.java`: Main Java benchmark suite (259 lines) - generic `runBenchmark()` method at line 147
- `benchmark.js`: Main JS benchmark suite (290 lines) - `BenchmarkSuite` class structure
- `TestDataFactory.java`/`testDataFactory.js`: Test data generators - modify to change data characteristics
- `ProtobufConverter.java`/`protobufConverter.js`: POJO ↔ Proto conversion - update when schemas change
- `pom.xml`: Maven config - Java 17, protobuf plugin at line 59
- `docs/QUICK_REFERENCE.md`: User-facing quick start guide

## Common Pitfalls
- **Forgetting to update converters**: Changing POJOs requires updating Java AND JS converters + proto schemas
- **GC interference**: Always use GC flags for consistent results (especially JS)
- **Baseline confusion**: Java baseline is Jackson, JS baseline is JSON - don't mix comparisons
- **Proto compilation**: Changes to `.proto` files require `mvn compile` before tests run
- **README automation**: Don't manually edit between benchmark result markers - GitHub Actions owns that section
