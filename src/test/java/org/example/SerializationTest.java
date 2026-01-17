package org.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.proto.LargePojoProto;
import org.example.util.ProtobufConverter;
import org.example.util.TestDataFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Serialization Performance Benchmarks")
class SerializationTest {

    private static final int WARMUP_ITERATIONS = 100;
    private static final int BENCHMARK_ITERATIONS = 1000;
    private static final int LIST_SIZE = 20;

    // Table formatting constants
    private static final String TABLE_TOP = "┌─────────────┬──────────────┬────────────┬─────────────────┬────────────┐";
    private static final String TABLE_HEADER = "│ Serializer  │ Avg Time (ms)│  % Diff    │  Size (bytes)   │  % Diff    │";
    private static final String TABLE_SEPARATOR = "├─────────────┼──────────────┼────────────┼─────────────────┼────────────┤";
    private static final String TABLE_BOTTOM = "└─────────────┴──────────────┴────────────┴─────────────────┴────────────┘";
    private static final String SUMMARY_TOP = "┌─────────────┬────────────────────┬────────────────────┐";
    private static final String SUMMARY_HEADER = "│ Serializer  │ Total Time (ms)    │ Config             │";
    private static final String SUMMARY_SEPARATOR = "├─────────────┼────────────────────┼────────────────────┤";
    private static final String SUMMARY_BOTTOM = "└─────────────┴────────────────────┴────────────────────┘";

    private static List<LargePojo> testData;
    private static LargePojoProto.LargePojoList protobufData;

    // Total time tracking
    private static final java.util.Map<String, Double> totalTimes = new java.util.concurrent.ConcurrentHashMap<>();

    // Reusable mapper instances
    private static ObjectMapper jacksonMapper;
    private static ObjectMapper jacksonMapperNoNulls;
    private static ObjectMapper msgpackMapper;
    private static ObjectMapper msgpackMapperNoNulls;
    private static Gson gsonWithNulls;
    private static Gson gsonWithoutNulls;

    @BeforeAll
    static void setUp() {
        // Initialize test data
        testData = TestDataFactory.createPojoList(LIST_SIZE);
        protobufData = ProtobufConverter.convertListToProto(testData);

        // Initialize Jackson mappers
        jacksonMapper = new ObjectMapper();
        jacksonMapperNoNulls = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Initialize MessagePack mappers
        msgpackMapper = new ObjectMapper(new MessagePackFactory());
        msgpackMapperNoNulls = new ObjectMapper(new MessagePackFactory())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Initialize Gson instances
        gsonWithNulls = new GsonBuilder().serializeNulls().create();
        gsonWithoutNulls = new Gson();
    }

    @Test
    @DisplayName("Benchmark Jackson, Gson, MessagePack, and Protobuf with null fields serialized")
    void benchmarkSerializersWithNullFieldsSerialized() {
        runBenchmarkSuite(
            "WITH NULLS",
            jacksonMapper,
            gsonWithNulls,
            msgpackMapper
        );

        // Verify benchmark suite executed successfully
        org.junit.jupiter.api.Assertions.assertTrue(totalTimes.containsKey("Jackson"),
            "Jackson benchmark should have recorded results");
    }

    @Test
    @DisplayName("Benchmark Jackson, Gson, MessagePack, and Protobuf with null fields excluded")
    void benchmarkSerializersWithNullFieldsExcluded() {
        runBenchmarkSuite(
            "WITHOUT NULLS",
            jacksonMapperNoNulls,
            gsonWithoutNulls,
            msgpackMapperNoNulls
        );

        // Verify benchmark suite executed successfully
        org.junit.jupiter.api.Assertions.assertTrue(totalTimes.containsKey("Jackson"),
            "Jackson benchmark should have recorded results");
    }

    private void runBenchmarkSuite(String suiteName, ObjectMapper jacksonMapper,
                                    Gson gson, ObjectMapper msgpackMapper) {
        printBenchmarkHeader(suiteName);
        printTableHeader();

        final double[] jacksonMetrics = benchmarkJackson(jacksonMapper);
        final double[] gsonMetrics = benchmarkGson(gson);
        final double[] msgpackMetrics = benchmarkMessagePack(msgpackMapper);
        final double[] protobufMetrics = benchmarkProtobuf();

        printMetricsWithPercentage("Gson", gsonMetrics, jacksonMetrics);
        printMetricsWithPercentage("MessagePack", msgpackMetrics, jacksonMetrics);
        printMetricsWithPercentage("Protobuf", protobufMetrics, jacksonMetrics);

        printTableFooter();
        printTotalTimesSummary(suiteName);
    }

    private void printBenchmarkHeader(String suiteName) {
        System.out.println("\n========================================");
        System.out.println("  Serialization Benchmark - " + suiteName);
        System.out.println("  (List of " + LIST_SIZE + " LargePojo objects)");
        System.out.println("========================================\n");
    }

    private void printTableHeader() {
        System.out.println(TABLE_TOP);
        System.out.println(TABLE_HEADER);
        System.out.println(TABLE_SEPARATOR);
    }

    private void printTableFooter() {
        System.out.println(TABLE_BOTTOM);
        System.out.println("\n========================================\n");
    }

    /**
     * Generic benchmark method that handles warmup, timing, and metrics calculation
     *
     * @param serializerName Name of the serializer (for display purposes)
     * @param serializer Supplier that performs the serialization and returns the result
     * @param sizeCalculator Function to calculate the size from the result
     * @param printBaseline Whether to print as baseline (Jackson)
     * @return Array containing [avgTimeMs, size, totalTimeMs]
     */
    private <T> double[] runBenchmark(String serializerName, Supplier<T> serializer,
                                      SizeCalculator<T> sizeCalculator, boolean printBaseline) {
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            serializer.get();
        }

        // Suggest GC between warmup and actual benchmark for cleaner results
        System.gc();
        // Allow time for GC to potentially run (note: gc() is only a suggestion)

        // Benchmark
        final long startTime = System.nanoTime();
        T result = null;
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            result = serializer.get();
        }
        final long endTime = System.nanoTime();

        final double avgTimeMs = (endTime - startTime) / 1_000_000.0 / BENCHMARK_ITERATIONS;
        final double totalTimeMs = (endTime - startTime) / 1_000_000.0;
        final int size = sizeCalculator.calculateSize(result);

        // Track total time
        totalTimes.merge(serializerName, totalTimeMs, Double::sum);

        if (printBaseline) {
            System.out.printf("│ %-11s │ %12.4f │ %9.1f%% │ %,15d │ %9.1f%% │%n",
                serializerName, avgTimeMs, 0.0, size, 0.0);
        }

        return new double[]{avgTimeMs, size, totalTimeMs};
    }

    private double[] benchmarkJackson(ObjectMapper mapper) {
        return runBenchmark("Jackson",
            () -> {
                try {
                    return mapper.writeValueAsBytes(testData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            },
            bytes -> bytes.length,
            true);
    }

    private double[] benchmarkGson(Gson gson) {
        return runBenchmark("Gson (hidden)",
            () -> gson.toJson(testData),
            str -> str.getBytes(StandardCharsets.UTF_8).length,
            false);
    }

    private double[] benchmarkMessagePack(ObjectMapper mapper) {
        return runBenchmark("MessagePack (hidden)",
            () -> {
                try {
                    return mapper.writeValueAsBytes(testData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            },
            bytes -> bytes.length,
            false);
    }

    private double[] benchmarkProtobuf() {
        return runBenchmark("Protobuf (hidden)",
            () -> protobufData.toByteArray(),
            bytes -> bytes.length,
            false);
    }

    @FunctionalInterface
    private interface SizeCalculator<T> {
        int calculateSize(T result);
    }

    private void printTotalTimesSummary(String suiteName) {
        System.out.println("\n========================================");
        System.out.println("  TOTAL SERIALIZATION TIME - " + suiteName);
        System.out.println("========================================\n");
        System.out.println(SUMMARY_TOP);
        System.out.println(SUMMARY_HEADER);
        System.out.println(SUMMARY_SEPARATOR);

        String[] serializers = {"Jackson", "Gson (hidden)", "MessagePack (hidden)", "Protobuf (hidden)"};
        String[] displayNames = {"Jackson", "Gson", "MessagePack", "Protobuf"};

        for (int i = 0; i < serializers.length; i++) {
            Double totalTime = totalTimes.get(serializers[i]);
            if (totalTime != null) {
                System.out.printf("│ %-11s │ %18.4f │ %-18s │%n",
                    displayNames[i], totalTime, suiteName);
            }
        }

        System.out.println(SUMMARY_BOTTOM);
        System.out.println("\n========================================\n");
    }

    private void printMetricsWithPercentage(String name, double[] metrics, double[] jacksonMetrics) {
        final double timeChange = ((metrics[0] - jacksonMetrics[0]) / jacksonMetrics[0]) * 100;
        final double sizeChange = ((metrics[1] - jacksonMetrics[1]) / jacksonMetrics[1]) * 100;

        System.out.printf("│ %-11s │ %12.4f │ %+9.1f%% │ %,15.0f │ %+9.1f%% │%n",
            name, metrics[0], timeChange, metrics[1], sizeChange);
    }
}
