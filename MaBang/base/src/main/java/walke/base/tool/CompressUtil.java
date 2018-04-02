package walke.base.tool;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressUtil {
    // 压缩字符串与解压字符串

    // 这里需要特别注意的是，如果你想把压缩后的byte[]保存到字符串中，
    // 不能直接使用new String(byte)或者byte.toString()，因为这样转换之后容量是增加的。
    // 同样的道理，如果是字符串的话，也不能直接使用new String().getBytes()获取byte[]传入到decompress中进行解压缩。
    // 如果保存压缩后的二进制，可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)将其转换为字符串。
    // 同样解压缩的时候首先使用new BASE64Decoder().decodeBuffer 方法将字符串转换为字节，然后解压就可以了。

    /**
     * 压缩字符串为 byte[] 储存可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)方法 保存为字符串
     *
     * @param str 压缩前的文本
     * @return
     */
    public static final byte[] compress(String str) {
        if (str == null)
            return null;

        byte[] compressed;
        ByteArrayOutputStream out = null;

        GZIPOutputStream zout = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new GZIPOutputStream(out);
            zout.write(str.getBytes(Code.UTF_8));
            zout.close();

            compressed = out.toByteArray();
        } catch (IOException e) {
            compressed = null;
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return compressed;
    }

    /**
     * 将压缩后的 byte[] 数据解压缩
     *
     * @param compressed 压缩后的 byte[] 数据
     * @return 解压后的字符串
     */
    public static final String decompress(byte[] compressed) {
        if (compressed == null)
            return null;

        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream zin = null;

        String decompressed;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString(Code.UTF_8);
        } catch (IOException e) {
            decompressed = null;
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return decompressed;
    }
}
