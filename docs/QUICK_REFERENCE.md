# Quick Reference Guide

## JavaScript Deserialization Benchmarks

### Quick Start

```bash
# Install dependencies
npm install

# Run benchmarks
npm run benchmark

# Run with GC control (recommended)
node --expose-gc js/benchmark.js
```

### What It Does

- Benchmarks **deserialization** performance in JavaScript/Node.js
- Tests JSON.parse (baseline) and MessagePack formats
- Uses same test data structure as Java benchmarks
- Measures average time and data size

### Results Location

Results are printed to console in a formatted table showing:
- Average deserialization time (milliseconds)
- Percentage difference vs JSON.parse baseline
- Serialized data size (bytes)
- Percentage size difference

### Key Files

```
js/
├── benchmark.js          # Main benchmark suite
├── testDataFactory.js    # Test data generation
└── README.md            # Detailed documentation

package.json             # Node.js dependencies
```

### Customization

Edit `js/benchmark.js` constants:

```javascript
const WARMUP_ITERATIONS = 100;      // Warmup runs
const BENCHMARK_ITERATIONS = 1000;  // Test runs
const LIST_SIZE = 20;               // Objects per test
```

### Requirements

- Node.js 14.0+ (tested with v22.0.0)
- npm or yarn

### Java vs JavaScript

| Aspect        | Java (src/)          | JavaScript (js/)       |
|---------------|----------------------|------------------------|
| Operation     | Serialization        | Deserialization        |
| Baseline      | Jackson              | JSON.parse             |
| Formats       | 4 (Jackson, Gson, MP, Protobuf) | 3 (JSON, MessagePack, Protobuf) |
| Test Data     | LargePojo (150+ fields) | Matching structure  |
| Iterations    | 100 warmup, 1000 test | Same                 |

### Interpreting Results

- **Negative %**: Faster/smaller than baseline (better)
- **Positive %**: Slower/larger than baseline (worse)
- **JSON.parse**: Highly optimized, usually fastest for deserialization
- **MessagePack**: Smaller size, may be slower to decode

### Example Output

```
┌─────────────┬──────────────┬────────────┬─────────────────┬────────────┐
│ Deserializer│ Avg Time (ms)│  % Diff    │  Size (bytes)   │  % Diff    │
├─────────────┼──────────────┼────────────┼─────────────────┼────────────┤
│ JSON.parse  │       0.8993 │       0.0% │        170,027  │       0.0% │
│ MessagePack │       4.5642 │    +407.5% │        127,177  │     -25.2% │
│ Protobuf    │       0.6576 │     -35.7% │         71,586  │     -61.4% │
└─────────────┴──────────────┴────────────┴─────────────────┴────────────┘
```

This shows:
- JSON.parse: ~0.9ms average, 170KB size (baseline)
- MessagePack: ~4.6ms average (slower), 127KB size (25% smaller)
- Protobuf: ~0.7ms average (36% faster!), 72KB size (61% smaller!)

### Tips for Accurate Results

1. Run with `--expose-gc` flag
2. Close other applications
3. Run multiple times and average
4. Use consistent Node.js version
5. Ensure system is not under load

### Adding New Formats

1. Install package: `npm install <package>`
2. Import in benchmark.js
3. Add to setUp() method
4. Create benchmark method
5. Call in runBenchmarkSuite()

See `js/README.md` for detailed instructions.

### Troubleshooting

**Error: Cannot find module**
- Run `npm install` first

**Unexpected performance**
- Try with `--expose-gc` flag
- Close other applications
- Check Node.js version
- Ensure system has free memory

**Want to test more data**
- Increase `LIST_SIZE` constant
- Note: Very large sizes may cause memory issues

### More Information

- Full docs: `js/README.md`
- Java benchmarks: Main `README.md`
- Summary: `JS_BENCHMARKS_SUMMARY.md`
