package org.ihtsdo.elasticsnomed.core.rf2.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ExportWriter<T> implements AutoCloseable {

	private static final int FLUSH_SIZE = 1000;
	static final String TAB = "\t";

	final BufferedWriter bufferedWriter;
	final List<T> componentBuffer;

	ExportWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
		componentBuffer = new ArrayList<>();
	}

	abstract void writeHeader() throws IOException;

	void write(T component) {
		componentBuffer.add(component);
		if (componentBuffer.size() == FLUSH_SIZE) {
			flush();
		}
	}

	abstract void flush();

	@Override
	public void close() throws IOException {
		flush();
		bufferedWriter.flush();
	}
}
