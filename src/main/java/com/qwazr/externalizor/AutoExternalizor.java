/**
 * Copyright 2016 Emmanuel Keller / QWAZR
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qwazr.externalizor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class AutoExternalizor implements Externalizable {

	private transient final Externalizor externalizor;
	private transient final Object object;

	/**
	 * Use this constructor to decorate an existing object
	 *
	 * @param object the instance to decorate
	 */
	protected AutoExternalizor(final Object object) {
		this.externalizor = Externalizor.of(object.getClass());
		this.object = object;
	}

	/**
	 * Empty constructor used to extend a class
	 */
	protected AutoExternalizor() {
		this.externalizor = Externalizor.of(this.getClass());
		this.object = this;
	}

	// The serialization part
	@Override
	final public void writeExternal(final ObjectOutput out) throws IOException {
		externalizor.writeExternal(object, out);
	}

	@Override
	final public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		externalizor.readExternal(object, in);
	}

}
