# Externalizor

[![Build Status](https://travis-ci.org/qwazr/QWAZR.svg?branch=master)](https://travis-ci.org/qwazr/externalizor)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.qwazr/externalizor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.qwazr/externalizor)
[![Coverage Status](https://coveralls.io/repos/github/qwazr/externalizor/badge.svg?branch=master)](https://coveralls.io/github/qwazr/externalizor?branch=master)
[![Javadocs](http://www.javadoc.io/badge/qwazr/externalizor.svg)](http://www.javadoc.io/doc/qwazr/externalizor)

Efficient (fast and small) JAVA serialization using Externalizable interface.

- Support of inner Externalizable objects
- Concrete Collections (Map, Set, Vector)
- Primitive types: int, long, short, double, float, boolean, char, byte, enum
- Primitive array: with compression/decompression based on [Snappy](https://github.com/xerial/snappy-java)
- Smaller by a factor from 50 to 150 compared to default JAVA serialization

## Usage

The class to serialize should implements
[Externalizable](https://docs.oracle.com/javase/8/docs/api/java/io/Externalizable.html).

```java

import java.io.Externalizable;
import com.qwazr.externalizor.Externalizor;

public class MyClass implements Externalizable {

	String aStringValue;
	int anIntValue;
	double[] anArrayOfDouble;

	enum MyEnum {
		on, off
	}

	MyEnum anEnum;
	HashSet<Status> aSetOfEnum;

	public MyClass() {
	}

	// Here is the serialization part:
    // Create the serialization handler for this class. 
	private final static Externalizor<MyClass> externalizor = Externalizor.of(MyClass.class);

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		externalizor.writeExternal(this, out);
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		externalizor.readExternal(this, in);
	}
}
```

The typical JAVA code for serialize and unserialize an object:

```java

// Serialization

MyClass myClass = new MyClass();
try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
    try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
        oos.writeObject(myClass);
    }
}

// Deserialization

try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
    try (ObjectInputStream ois = new ObjectInputStream(bis)) {
        MyClass myClass = ois.readObject();
    }
}
```

## Maven dependency

Available from Maven's central repository: http://central.maven.org/maven2/com/qwazr/externalizor.

Add the following dependency to your pom.xml:

```xml
<dependency>
    <groupId>com.qwazr</groupId>
    <artifactId>externalizor</artifactId>
    <version>1.0</version>
</dependency>
```
## Issues

Post bug reports or feature request to the Issue Tracker:
https://github.com/qwazr/externalizor/issues

## Open source license

[Apache2 license](https://github.com/qwazr/externalizor/blob/master/LICENSE)

