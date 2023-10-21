package ru.mclord.classic;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class PacketHandler {
    /* package-private */ final byte packetId;
    /**
     * Packet length without the first packetId byte.
     * For example the length of PlayerIdentification (0x00)
     * packet should be 131 - 1 = 130 bytes.
     */
    /* package-private */ final int packetLength;
    /* package-private */ final boolean fastHandler;

    public PacketHandler(byte packetId, int packetLength) {
        this(packetId, packetLength, false);
    }

    public PacketHandler(byte packetId, int packetLength, boolean fastHandler) {
        this.packetId = packetId;
        this.packetLength = packetLength;
        this.fastHandler = fastHandler;
    }

    public final byte getPacketId() {
        return packetId;
    }

    public final int getPacketLength() {
        return packetLength;
    }

    public final boolean isFastHandler() {
        return fastHandler;
    }

    public final void handle0(DataInputStream stream) {
        McLordClassic.game().addTask(() -> {
            try {
                handle(stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /*
     * Call this method ONLY if you're completely
     * sure the code will be executed by the main thread.
     *
     * Otherwise, call handle0().
     */
    public abstract void handle(DataInputStream stream) throws IOException;
}
