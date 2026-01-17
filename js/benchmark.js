/**
 * JavaScript Deserialization Benchmarks
 *
 * Benchmarks various deserialization libraries in JavaScript/Node.js:
 * - Native JSON.parse
 * - MessagePack (msgpack5)
 * - Protocol Buffers (protobufjs)
 */

import { TestDataFactory } from './testDataFactory.js';
import { ProtobufConverter } from './protobufConverter.js';
import msgpack5 from 'msgpack5';

// Constants
const WARMUP_ITERATIONS = 100;
const BENCHMARK_ITERATIONS = 1000;
const LIST_SIZE = 20;

// Table formatting
const TABLE_TOP = "┌─────────────┬──────────────┬────────────┬─────────────────┬────────────┐";
const TABLE_HEADER = "│ Library     │ Avg Time (ms)│  % Diff    │  Size (bytes)   │  % Diff    │";
const TABLE_SEPARATOR = "├─────────────┼──────────────┼────────────┼─────────────────┼────────────┤";
const TABLE_BOTTOM = "└─────────────┴──────────────┴────────────┴─────────────────┴────────────┘";
const SUMMARY_TOP = "┌─────────────┬──────────────────────────┬──────────────────────────┐";
const SUMMARY_HEADER = "│ Library     │ Serialization Total (ms) │ Deserialization Total(ms)│";
const SUMMARY_SEPARATOR = "├─────────────┼──────────────────────────┼──────────────────────────┤";
const SUMMARY_BOTTOM = "└─────────────┴──────────────────────────┴──────────────────────────┘";

class BenchmarkSuite {
    constructor() {
        this.msgpack = msgpack5();
        this.testData = null;
        this.serializedData = {};
        this.totalTimes = {
            serialization: {},
            deserialization: {}
        };
    }

    /**
     * Initialize test data and pre-serialize it
     */
    async setUp() {
        console.log('Setting up benchmark suite...');

        // Initialize Protocol Buffers
        await ProtobufConverter.initialize();

        // Create test data
        this.testData = TestDataFactory.createPojoList(LIST_SIZE);

        // Pre-serialize data for deserialization benchmarks
        this.serializedData.json = JSON.stringify(this.testData);
        this.serializedData.msgpack = this.msgpack.encode(this.testData);
        this.serializedData.protobuf = ProtobufConverter.convertListToProto(this.testData);

        console.log('Test data created and serialized.');
        console.log(`  - List size: ${LIST_SIZE} objects`);
        console.log(`  - JSON size: ${this.serializedData.json.length} bytes`);
        console.log(`  - MessagePack size: ${this.serializedData.msgpack.length} bytes`);
        console.log(`  - Protobuf size: ${this.serializedData.protobuf.length} bytes`);
    }

    /**
     * Run a single benchmark (serialization or deserialization)
     */
    runBenchmark(name, operation, input, printBaseline = false, type = 'deserialization') {
        // Warmup
        for (let i = 0; i < WARMUP_ITERATIONS; i++) {
            operation(input);
        }

        // Suggest GC (if available)
        if (globalThis.gc) {
            globalThis.gc();
        }

        // Benchmark
        const startTime = process.hrtime.bigint();
        let result = null;
        for (let i = 0; i < BENCHMARK_ITERATIONS; i++) {
            result = operation(input);
        }
        const endTime = process.hrtime.bigint();

        const avgTimeMs = Number(endTime - startTime) / 1_000_000 / BENCHMARK_ITERATIONS;
        const totalTimeMs = Number(endTime - startTime) / 1_000_000;

        // Store total time
        if (!this.totalTimes[type][name]) {
            this.totalTimes[type][name] = 0;
        }
        this.totalTimes[type][name] += totalTimeMs;

        const size = type === 'serialization' ? Buffer.byteLength(result) : Buffer.byteLength(input);

        if (printBaseline) {
            console.log(`│ ${this.padRight(name, 11)} │ ${this.padLeft(avgTimeMs.toFixed(4), 12)} │ ${this.padLeft('0.0%', 10)} │ ${this.padLeft(size.toLocaleString(), 15)} │ ${this.padLeft('0.0%', 10)} │`);
        }

        return { avgTimeMs, size, result };
    }

    /**
     * Serialization Benchmarks
     */
    benchmarkJSONSerialization() {
        return this.runBenchmark(
            'JSON',
            (data) => JSON.stringify(data),
            this.testData,
            true,
            'serialization'
        );
    }

    benchmarkMessagePackSerialization() {
        return this.runBenchmark(
            'MessagePack',
            (data) => this.msgpack.encode(data),
            this.testData,
            false,
            'serialization'
        );
    }

    benchmarkProtobufSerialization() {
        return this.runBenchmark(
            'Protobuf',
            (data) => ProtobufConverter.convertListToProto(data),
            this.testData,
            false,
            'serialization'
        );
    }

    /**
     * Deserialization Benchmarks
     */
    benchmarkJSONDeserialization() {
        return this.runBenchmark(
            'JSON',
            (data) => JSON.parse(data),
            this.serializedData.json,
            true,
            'deserialization'
        );
    }

    benchmarkMessagePackDeserialization() {
        return this.runBenchmark(
            'MessagePack',
            (data) => this.msgpack.decode(data),
            this.serializedData.msgpack,
            false,
            'deserialization'
        );
    }

    benchmarkProtobufDeserialization() {
        return this.runBenchmark(
            'Protobuf',
            (data) => ProtobufConverter.decodeProtoToList(data),
            this.serializedData.protobuf,
            false,
            'deserialization'
        );
    }

    /**
     * Print benchmark results with percentage comparison
     */
    printMetricsWithPercentage(name, metrics, baselineMetrics) {
        const timeChange = ((metrics.avgTimeMs - baselineMetrics.avgTimeMs) / baselineMetrics.avgTimeMs) * 100;
        const sizeChange = ((metrics.size - baselineMetrics.size) / baselineMetrics.size) * 100;

        const timeSign = timeChange >= 0 ? '+' : '';
        const sizeSign = sizeChange >= 0 ? '+' : '';

        console.log(`│ ${this.padRight(name, 11)} │ ${this.padLeft(metrics.avgTimeMs.toFixed(4), 12)} │ ${this.padLeft(timeSign + timeChange.toFixed(1) + '%', 10)} │ ${this.padLeft(metrics.size.toLocaleString(), 15)} │ ${this.padLeft(sizeSign + sizeChange.toFixed(1) + '%', 10)} │`);
    }

    /**
     * Run complete benchmark suite
     */
    async runBenchmarkSuite() {
        // Serialization Benchmarks
        this.printBenchmarkHeader('SERIALIZATION');
        this.printTableHeader();

        const jsonSerMetrics = this.benchmarkJSONSerialization();
        const msgpackSerMetrics = this.benchmarkMessagePackSerialization();
        const protobufSerMetrics = this.benchmarkProtobufSerialization();

        this.printMetricsWithPercentage('MessagePack', msgpackSerMetrics, jsonSerMetrics);
        this.printMetricsWithPercentage('Protobuf', protobufSerMetrics, jsonSerMetrics);

        this.printTableFooter();

        // Deserialization Benchmarks
        this.printBenchmarkHeader('DESERIALIZATION');
        this.printTableHeader();

        const jsonDeserMetrics = this.benchmarkJSONDeserialization();
        const msgpackDeserMetrics = this.benchmarkMessagePackDeserialization();
        const protobufDeserMetrics = this.benchmarkProtobufDeserialization();

        this.printMetricsWithPercentage('MessagePack', msgpackDeserMetrics, jsonDeserMetrics);
        this.printMetricsWithPercentage('Protobuf', protobufDeserMetrics, jsonDeserMetrics);

        this.printTableFooter();

        // Print Summary
        this.printTotalTimesSummary();
    }

    /**
     * Helper methods for formatting output
     */
    printBenchmarkHeader(suiteName) {
        console.log('\n========================================');
        console.log(`  ${suiteName} Benchmark`);
        console.log(`  (List of ${LIST_SIZE} LargePojo objects)`);
        console.log('========================================\n');
    }

    printTableHeader() {
        console.log(TABLE_TOP);
        console.log(TABLE_HEADER);
        console.log(TABLE_SEPARATOR);
    }

    printTableFooter() {
        console.log(TABLE_BOTTOM);
        console.log('\n========================================\n');
    }

    printTotalTimesSummary() {
        console.log('\n========================================');
        console.log('  TOTAL TIME SUMMARY');
        console.log('========================================\n');
        console.log(SUMMARY_TOP);
        console.log(SUMMARY_HEADER);
        console.log(SUMMARY_SEPARATOR);

        ['JSON', 'MessagePack', 'Protobuf'].forEach(lib => {
            const serTime = this.totalTimes.serialization[lib] || 0;
            const deserTime = this.totalTimes.deserialization[lib] || 0;
            console.log(`│ ${this.padRight(lib, 11)} │ ${this.padLeft(serTime.toFixed(4), 24)} │ ${this.padLeft(deserTime.toFixed(4), 24)} │`);
        });

        console.log(SUMMARY_BOTTOM);
        console.log('\n========================================\n');
    }

    padRight(str, length) {
        return str.toString().padEnd(length, ' ');
    }

    padLeft(str, length) {
        return str.toString().padStart(length, ' ');
    }
}

/**
 * Main execution
 */
async function main() {
    console.log('╔════════════════════════════════════════╗');
    console.log('║  JavaScript Serialization Benchmarks  ║');
    console.log('╔════════════════════════════════════════╗');
    console.log('');
    console.log('This benchmark suite measures serialization and deserialization');
    console.log('performance for various JavaScript serialization libraries.');
    console.log('');

    const suite = new BenchmarkSuite();
    await suite.setUp();

    console.log('\nStarting benchmarks...\n');

    // Run benchmark
    await suite.runBenchmarkSuite();

    console.log('Benchmarks completed successfully!');
}

// Run the benchmarks
await main();
