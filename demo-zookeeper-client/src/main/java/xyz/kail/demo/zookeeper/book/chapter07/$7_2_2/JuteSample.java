package xyz.kail.demo.zookeeper.book.chapter07.$7_2_2;

import org.apache.jute.BinaryInputArchive;
import org.apache.jute.BinaryOutputArchive;
import org.apache.zookeeper.server.ByteBufferInputStream;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

//使用Jute进行序列化
public class JuteSample {

	public static void main( String[] args ) throws Exception {
		//开始序列化
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BinaryOutputArchive boa = BinaryOutputArchive.getArchive(baos);
		new MockReqHeader( 0x34221eccb92a34el, "ping" ).serialize(boa, "header");
		//这里通常是TCP网络传输对象
		ByteBuffer bb = ByteBuffer.wrap( baos.toByteArray() );
		//开始反序列化
		ByteBufferInputStream bbis = new ByteBufferInputStream(bb);
		BinaryInputArchive bbia = BinaryInputArchive.getArchive(bbis);
		MockReqHeader header2 = new MockReqHeader();
		header2.deserialize(bbia, "header");
		bbis.close();
		baos.close();
	}
}
