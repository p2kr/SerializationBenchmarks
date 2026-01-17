# JavaScript Deserialization Benchmarks

This directory contains JavaScript/Node.js benchmarks for testing deserialization performance of various serialization formats.

## Overview

While the Java portion of this project benchmarks **serialization** performance, this JavaScript module focuses on **deserialization** performance - measuring how quickly data can be parsed from different formats.

## Supported Formats

- **JSON (JSON.parse)** - Native JavaScript JSON parsing (baseline)
- **MessagePack** - Binary serialization format using msgpack5
- **Protocol Buffers** - Google's binary format using protobufjs

## Requirements

- Node.js 14.0 or higher (ES modules support)
- npm or yarn

## Installation

From the project root directory:

```bash
npm install
```

Or with yarn:

```bash
yarn install
```

## Running the Benchmarks

### Basic Usage

```bash
npm run benchmark
```

Or directly:

```bash
node js/benchmark.js
```

### With GC Control (Recommended)

For more accurate results with better garbage collection control:

```bash
node --expose-gc js/benchmark.js
```

## Benchmark Methodology

The benchmark suite follows similar principles to the Java version:

1. **Warmup Phase**: 100 iterations to ensure V8 optimization
2. **Benchmark Phase**: 1000 iterations for accurate timing
3. **Metrics Collected**:
   - Average deserialization time (milliseconds)
   - Serialized data size (bytes)
   - Percentage comparison against baseline (JSON.parse)

## Output Format

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

## Test Data

The test data structure mirrors the Java `LargePojo` class:
- 20 nested objects per large POJO
- 150+ fields of various types (int, long, double, boolean, etc.)
- List of 20 large POJOs (configurable)

## Customization

You can modify the benchmark parameters in `js/benchmark.js`:

```javascript
const WARMUP_ITERATIONS = 100;      // Warmup iterations
const BENCHMARK_ITERATIONS = 1000;  // Benchmark iterations
const LIST_SIZE = 20;               // Number of objects in test data
```

## Performance Tips

1. **Run with --expose-gc**: Allows manual garbage collection between tests
2. **Close other applications**: Reduce CPU/memory contention
3. **Multiple runs**: Run the benchmark several times for consistency
4. **Node.js version**: Newer versions may have better V8 optimizations

## Comparison with Java Serialization

| Language   | Operation      | Focus                          |
|------------|----------------|--------------------------------|
| Java       | Serialization  | Writing objects to bytes/JSON  |
| JavaScript | Deserialization| Reading bytes/JSON to objects  |

This complementary approach provides a full picture of serialization ecosystems:
- Java backend services typically serialize data
- JavaScript frontend/Node.js services typically deserialize data

## Adding New Serialization Formats

To add a new format:

1. Install the package: `npm install <package-name>`
2. Import in `benchmark.js`
3. Pre-serialize test data in `setUp()`
4. Add a benchmark method following the pattern:
   ```javascript
   benchmarkNewFormat() {
       return this.runBenchmark(
           'Format Name',
           (data) => newFormat.decode(data),
           this.serializedData.newFormat,
           false
       );
   }
   ```
5. Call it in `runBenchmarkSuite()` and print results

## Dependencies

- **msgpack5** (^6.0.2) - MessagePack implementation
- **protobufjs** (^7.4.0) - Protocol Buffers implementation

## License

Same as parent project.
