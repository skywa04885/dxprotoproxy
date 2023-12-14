package com.github.skywa04885.dxprotoproxy.dxprotoproxy.net;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NetOutputStreamWrapper {
    private @NotNull OutputStream outputStream;
    private boolean isClosed;

    /**
     * Constructs a new output stream wrapper that wraps the given output stream.
     * @param outputStream the output stream to wrap.
     */
    public NetOutputStreamWrapper(@NotNull OutputStream outputStream) {
        this.outputStream = outputStream;

        isClosed = false;
    }

    /**
     * Closes the output stream.
     * @throws IOException gets thrown if closing fails.
     */
    public void close() throws IOException {
        assert !isClosed;

        outputStream.close();
    }

    /**
     * Flushes the output stream.
     * @throws IOException gets thrown if flushing fails.
     */
    public void flush() throws IOException {
        assert !isClosed;

        outputStream.flush();
    }

    /**
     * Writes the given string and encodes it using the given charset.
     * @param string the string.
     * @param charset the charset to use for encoding.
     * @throws IOException gets thrown if the writing fails.
     */
    public void write(final String string, final Charset charset) throws IOException {
        assert !isClosed;

        write(string.getBytes(charset));
    }

    /**
     * Writes the given string assuming that it's contents are UTF-8.
     * @param string the UTF-8 string.
     * @throws IOException gets thrown if the writing fails.
     */
    public void write(final String string) throws IOException {
        assert !isClosed;

        write(string, StandardCharsets.UTF_8);
    }

    /**
     * Writes the given string followed by a line termination.
     * @param string the string to be sent before the line termination.
     * @throws IOException gets thrown if the writing failed.
     */
    public void writeLine(final String string) throws IOException {
        assert !isClosed;

        // Writes the contents of the line.
        write(string);

        // Writes the new line.
        writeLine();;
    }

    /**
     * Writes an empty line.
     * @throws IOException gets thrown if the writing fails.
     */
    public void writeLine() throws IOException {
        write("\r\n");
    }


    /**
     * Writes the given chunk.
     * @param chunk the chunk to write.
     * @throws IOException gets thrown if the writing fails.
     */
    public void write(final byte[] chunk) throws IOException {
        outputStream.write(chunk);
    }

}
