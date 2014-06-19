package com.lambdaworks.redis.output;

import java.nio.ByteBuffer;

import com.lambdaworks.redis.support.LettuceStrings;
import com.lambdaworks.redis.ScanCursor;
import com.lambdaworks.redis.codec.RedisCodec;
import com.lambdaworks.redis.protocol.CommandOutput;

/**
 * Cursor handling output.
 * 
 * @param <K> Key type.
 * @param <V> Value type.
 * @param <T> Cursor type.
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 */
public abstract class ScanOutput<K, V, T extends ScanCursor> extends CommandOutput<K, V, T> {

    public ScanOutput(RedisCodec<K, V> codec, T cursor) {
        super(codec, cursor);
    }

    @Override
    public void set(ByteBuffer bytes) {

        if (output.getCursor() == null) {
            output.setCursor(decodeAscii(bytes));
            if (LettuceStrings.isNotEmpty(output.getCursor()) && "0".equals(output.getCursor())) {
                output.setFinished(true);
            }
            return;
        }

        setOutput(bytes);

    }

    protected abstract void setOutput(ByteBuffer bytes);
}