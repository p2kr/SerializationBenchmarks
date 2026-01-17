# JavaScript Deserialization Benchmarks - Summary

## What Was Added

This enhancement adds JavaScript/Node.js deserialization benchmarks to complement the existing Java serialization benchmarks.

## New Files Created

### 1. `package.json`
- Node.js project configuration
- Dependencies: msgpack5, protobufjs
- NPM scripts for running benchmarks

### 2. `js/testDataFactory.js`
- Test data generation matching Java's LargePojo structure
- Creates nested objects with 150+ fields
- Generates lists of complex objects for testing

### 3. `js/benchmark.js`
- Main benchmark suite for deserialization
- Tests JSON.parse (baseline) and MessagePack
- Includes warmup and benchmark iterations
- Formatted table output matching Java version

### 4. `js/README.md`
- Complete documentation for JavaScript benchmarks
- Installation and usage instructions
- Performance tips and customization guide

### 5. Updated `.gitignore`
- Added Node.js specific ignore patterns
- Ignores node_modules, npm cache, etc.

### 6. Updated main `README.md`
- Added JavaScript benchmarks to overview
- Updated features section
- Added JavaScript prerequisites
- Added quick start guide for JS benchmarks
- Updated project structure

## How to Use

### Installation
```bash
npm install
```

### Run Benchmarks
```bash
npm run benchmark
# or
node js/benchmark.js
```

### With GC Control (Recommended)
```bash
node --expose-gc js/benchmark.js
```

## Sample Output

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
│ JSON.parse  │       1.0129 │       0.0% │        170,055  │       0.0% │
│ MessagePack │       4.5811 │    +352.3% │        127,175  │     -25.2% │
└─────────────┴──────────────┴────────────┴─────────────────┴────────────┘
```

## Key Features

- **Benchmark Methodology**: Follows same principles as Java version
  - 100 warmup iterations
  - 1000 benchmark iterations
  - Average timing calculations
  - Percentage comparisons

- **Test Data**: Mirrors Java LargePojo structure
  - 20 nested objects per POJO
  - 150+ fields (integers, longs, doubles, booleans, etc.)
  - Lists of 20 large POJOs

- **Formats Tested**:
  - JSON.parse (native, baseline)
  - MessagePack (binary format)
  - Protocol Buffers (ready for implementation)

## Complementary Design

| Language   | Operation       | Use Case                      |
|------------|-----------------|-------------------------------|
| Java       | Serialization   | Backend services writing data |
| JavaScript | Deserialization | Frontend/Node.js reading data |

This design reflects real-world scenarios where:
- Java backend services serialize data to send to clients
- JavaScript frontend applications deserialize the received data

## Future Enhancements

1. Add Protocol Buffers deserialization
2. Add more serialization formats (BSON, Avro, etc.)
3. Add browser-based benchmarks (not just Node.js)
4. Add serialization benchmarks in JavaScript
5. Cross-language compatibility tests

## Dependencies Installed

- **msgpack5** (^6.0.2): MessagePack binary serialization
- **protobufjs** (^7.4.0): Protocol Buffers implementation

Total: 24 packages installed (including transitive dependencies)

## Testing Verification

✅ Successfully runs on Node.js v22.0.0
✅ All benchmarks execute without errors
✅ Output formatting matches Java version style
✅ Generates realistic test data
✅ Properly measures deserialization performance

## Benefits

1. **Complete Picture**: Now have both serialization (Java) and deserialization (JS) benchmarks
2. **Real-World Use Case**: Reflects typical full-stack architecture
3. **Easy Comparison**: Consistent formatting and methodology across languages
4. **Extensible**: Easy to add new formats and tests
5. **Well-Documented**: Comprehensive READMEs for both Java and JavaScript

## Notes

- JavaScript deserialization is generally very fast due to V8 optimization
- MessagePack shows smaller size but slower deserialization in this test
- JSON.parse is highly optimized in modern JavaScript engines
- Results may vary based on Node.js version and system configuration
