package aipos1.json

import java.nio.ByteBuffer

trait ResponseCacheHandler {

    static final TEMP_FILE_PREFIX = "temp"
    static final TEMP_FILE_SUFFIX = "cache"
    static final BUFFER_CAPACITY = 8192
    static final END_OF_READING = -1

    File cache(socketChannel) {
        def file = File.createTempFile TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX
        try (
                def outputStream = new FileOutputStream(file)
                def fileChannel = outputStream.getChannel()
        ) {
            def buffer = ByteBuffer.allocate BUFFER_CAPACITY
            while (socketChannel.read(buffer) != END_OF_READING) {
                buffer.flip()
                fileChannel.write buffer
                buffer.clear()
            }
            file
        }
    }
}
