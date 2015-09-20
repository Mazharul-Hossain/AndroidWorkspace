package net.sf.andpdf.pdfviewer;

import java.nio.Buffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class NioByteBuffer extends ByteBuffer {

	private java.nio.ByteBuffer nioBuf;
	
	public byte[] array() {
		return nioBuf.array();
	}


	public int arrayOffset() {
		return nioBuf.arrayOffset();
	}


	public CharBuffer asCharBuffer() {
		return nioBuf.asCharBuffer();
	}


	public DoubleBuffer asDoubleBuffer() {
		return nioBuf.asDoubleBuffer();
	}


	public FloatBuffer asFloatBuffer() {
		return nioBuf.asFloatBuffer();
	}


	public IntBuffer asIntBuffer() {
		return nioBuf.asIntBuffer();
	}


	public LongBuffer asLongBuffer() {
		return nioBuf.asLongBuffer();
	}


	public java.nio.ByteBuffer asReadOnlyBuffer() {
		return nioBuf.asReadOnlyBuffer();
	}


	public ShortBuffer asShortBuffer() {
		return nioBuf.asShortBuffer();
	}


	public int capacity() {
		return nioBuf.capacity();
	}


	public Buffer clear() {
		return nioBuf.clear();
	}


	public java.nio.ByteBuffer compact() {
		return nioBuf.compact();
	}


	public int compareTo(java.nio.ByteBuffer otherBuffer) {
		return nioBuf.compareTo(otherBuffer);
	}


	public NioByteBuffer duplicate() {
		return new NioByteBuffer(nioBuf.duplicate());
	}


	public boolean equals(Object other) {
		return nioBuf.equals(other);
	}


	public void flip() {
		nioBuf.flip();
	}


	public byte get() {
		return nioBuf.get();
	}


	public void get(byte[] dest, int off, int len) {
		nioBuf.get(dest, off, len);
	}


	public void get(byte[] dest) {
		nioBuf.get(dest);
	}


	public byte get(int index) {
		return nioBuf.get(index);
	}


	public char getChar() {
		return nioBuf.getChar();
	}


	public char getChar(int index) {
		return nioBuf.getChar(index);
	}


	public double getDouble() {
		return nioBuf.getDouble();
	}


	public double getDouble(int index) {
		return nioBuf.getDouble(index);
	}


	public float getFloat() {
		return nioBuf.getFloat();
	}


	public float getFloat(int index) {
		return nioBuf.getFloat(index);
	}


	public int getInt() {
		return nioBuf.getInt();
	}


	public int getInt(int index) {
		return nioBuf.getInt(index);
	}


	public long getLong() {
		return nioBuf.getLong();
	}


	public long getLong(int index) {
		return nioBuf.getLong(index);
	}


	public short getShort() {
		return nioBuf.getShort();
	}


	public short getShort(int index) {
		return nioBuf.getShort(index);
	}


	public boolean hasArray() {
		return nioBuf.hasArray();
	}


	public int hashCode() {
		return nioBuf.hashCode();
	}


	public boolean hasRemaining() {
		return nioBuf.hasRemaining();
	}


	public boolean isDirect() {
		return nioBuf.isDirect();
	}


	public boolean isReadOnly() {
		return nioBuf.isReadOnly();
	}


	public int limit() {
		return nioBuf.limit();
	}


	public void limit(int newLimit) {
		nioBuf.limit(newLimit);
	}


	public void mark() {
		nioBuf.mark();
	}


	public ByteOrder order() {
		return nioBuf.order();
	}


	public java.nio.ByteBuffer order(ByteOrder byteOrder) {
		return nioBuf.order(byteOrder);
	}


	public int position() {
		return nioBuf.position();
	}


	public void position(int newPosition) {
		nioBuf.position(newPosition);
	}


	public void put(byte b) {
		nioBuf.put(b);
	}


	public NioByteBuffer put(byte[] src, int off, int len) {
		nioBuf.put(src, off, len);
		return this;
	}


	public void put(byte[] src) {
		nioBuf.put(src);
	}


	public ByteBuffer put(java.nio.ByteBuffer src) {
		nioBuf.put(src);
		return this;
	}


	public void put(ByteBuffer src) {
		nioBuf.put(src.toNIO());
	}


	public void put(int index, byte b) {
		nioBuf.put(index, b);
	}


	public void putChar(char value) {
		nioBuf.putChar(value);
	}


	public NioByteBuffer putChar(int index, char value) {
		nioBuf.putChar(index, value);
		return this;
	}


	public NioByteBuffer putDouble(double value) {
		nioBuf.putDouble(value);
		return this;
	}


	public NioByteBuffer putDouble(int index, double value) {
		nioBuf.putDouble(index, value);
		return this;
	}


	public NioByteBuffer putFloat(float value) {
		nioBuf.putFloat(value);
		return this;
	}


	public NioByteBuffer putFloat(int index, float value) {
		nioBuf.putFloat(index, value);
		return this;
	}


	public void putInt(int index, int value) {
		nioBuf.putInt(index, value);
	}


	public void putInt(int value) {
		nioBuf.putInt(value);
	}


	public void  putLong(int index, long value) {
		nioBuf.putLong(index, value);
	}


	public void putLong(long value) {
		nioBuf.putLong(value);
	}


	public void putShort(int index, short value) {
		nioBuf.putShort(index, value);
	}


	public void putShort(short value) {
		nioBuf.putShort(value);
	}


	public int remaining() {
		return nioBuf.remaining();
	}


	public void reset() {
		nioBuf.reset();
	}


	public void rewind() {
		nioBuf.rewind();
	}


	public NioByteBuffer slice() {
		return new NioByteBuffer(nioBuf.slice());
	}


	public String toString() {
		return nioBuf.toString();
	}


	public NioByteBuffer(java.nio.ByteBuffer nioBuf) {
		this.nioBuf = nioBuf;
	}


	public java.nio.ByteBuffer toNIO() {
		return nioBuf; 
	}

	public static NioByteBuffer fromNIO(java.nio.ByteBuffer nioBuf) {
		return new NioByteBuffer(nioBuf);
	}


	public static NioByteBuffer allocate(int i) {
		return new NioByteBuffer(java.nio.ByteBuffer.allocate(i));
	}


	public static NioByteBuffer wrap(byte[] bytes) {
		return new NioByteBuffer(java.nio.ByteBuffer.wrap(bytes));
	}



}
