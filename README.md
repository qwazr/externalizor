# Externalizor

[![Build Status](https://travis-ci.org/qwazr/externalizor.svg?branch=master)](https://travis-ci.org/qwazr/externalizor)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.qwazr/externalizor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.qwazr/externalizor)
[![Coverage Status](https://coveralls.io/repos/github/qwazr/externalizor/badge.svg?branch=master)](https://coveralls.io/github/qwazr/externalizor?branch=master)
[![Javadocs](http://www.javadoc.io/badge/com.qwazr/externalizor.svg)](http://www.javadoc.io/doc/com.qwazr/externalizor)

Fast and compact serialization of Java object.

- Concrete Collections (Map, Set, Vector, List) with compression/decompression using [Snappy](https://github.com/xerial/snappy-java)
- Primitive types: int, long, short, double, float, boolean, char, byte, enum
- Primitive array: with compression/decompression using [Snappy](https://github.com/xerial/snappy-java)
- Time types: Date, LocalDate, LocalTime, LocalDateTime, Instant, Duration, Period, MonthDay, Year
- Other types are serialized using Java's default serialization

## Usage

Use the provided static methods to serialize and/or deserialize your object(s). There is two ways:
- Raw serialization: the fastest
- Compressed serialization: slower but more compact

### Raw serialization (fastest)

The serialization used our Snappy based specialized compressors.

```java
import java.io.*;
import com.qwazr.externalizor.Externalizor;

public class FastSerialization {

    public FastSerialization() {

        MyClass object = new MyClass();
        byte[] bytes;
        
        // Serialization
        
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Externalizor.serializeRaw(object, output);
            bytes = output.toByteArray();
        }
        
        // Deserialization
        
        try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
            MyClass object = Externalizor.deserializeRaw(input);
        }
    }
 }
 ```

### Compressed serialization (slower but more compact)
 
In addition of the specialized compression (snappy) a GZIP compression is used on the final serialized stream
(slower but more compact).

```java
import java.io.*;
import com.qwazr.externalizor.Externalizor;

public class CompactSerialization {

    public CompactSerialization() {

        MyClass object = new MyClass();
        byte[] bytes;
        
        // Serialization
        
        try (ByteArrayOutputStream input = new ByteArrayOutputStream()) {
            Externalizor.serialize(object, output);
            bytes = output.toByteArray();
        }
        
        // Deserialization
        
        try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
            object = Externalizor.deserialize(input);
        }
    }
}
```

## Maven dependency

In Maven's central repository:
[central.maven.org/maven2/com/qwazr/externalizor](http://central.maven.org/maven2/com/qwazr/externalizor)

Add the following dependency to your pom.xml:

```xml
<dependency>
    <groupId>com.qwazr</groupId>
    <artifactId>externalizor</artifactId>
    <version>1.3.2</version>
</dependency>
```

## Benchmark

The code of the benchmark is here:
[BenchmarkTest](src/test/java/com/qwazr/externalizor/BenchmarkTest.java)

- Serialization raw: Default Java serialization without compression.
- Serialization compressed: Default Java serialization with Gzip compression.
- Externalizor raw: Using Externalizor without compression.
- Externalizor compressed: Using Externalizor with Gzip compression.

### Average size of the serialized objects

Bytes sizes. Smaller is better.

![Byte size](byte_size.png)

### Serialization/Deserialization rate

Number of serialization and deserialization per seconds. Bigger is better.

![Rate](rate.png)

## Issues

Post bug reports or feature request to the Issue Tracker:
https://github.com/qwazr/externalizor/issues

## Open source license

[Apache2 license](https://github.com/qwazr/externalizor/blob/master/LICENSE)

