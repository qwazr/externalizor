# Externalizor

[![Build Status](https://travis-ci.org/qwazr/QWAZR.svg?branch=master)](https://travis-ci.org/qwazr/externalizor)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.qwazr/QWAZR/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.qwazr/externalizor)
[![Coverage Status](https://coveralls.io/repos/github/qwazr/externalizor/badge.svg?branch=master)](https://coveralls.io/github/qwazr/externalizor?branch=master)

Efficient (fast and small) JAVA serialization using Externalizable interface.

- Support of inner Externalizable class
- Concrete Collections (Map, Set, Vector)
- Primitive types: int, long, short, double, float, boolean, char, byte, enum
- Primitive array: with compression based on Snappy

Apache2 license.

## Usage

```java

import java.io.Externalizable;
import com.qwazr.externalizor.Externalizor;

/**
* The class should implement Externalizable
*/

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


	// The serialization part:
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

## Maven dependency

```xml
<dependency>
    <groupId>com.qwazr</groupId>
    <artifactId>externalizor</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```