package RedisBaseModule.aliyun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.ObjectMetadata;

import RedisBaseModule.aliyun.IdleConnectionReaper;
import RedisBaseModule.common.DateUtility;
import RedisBaseModule.common.DebugLog;
import RedisBaseModule.configurations.ServerConfig;




public class AliyunMain {

	private static final String myAccessKeyID = ServerConfig.AliyunAccessKeyID;
	private static final String mySecretKey = ServerConfig.AliyunAccessKeySecrete;
	static Logger logger = Logger.getLogger(AliyunMain.class);


	public static String backupRedisDumpFile(){

		OSSClient client = new OSSClient(myAccessKeyID, mySecretKey);	
		String imgAddress = "";
		File file = null;
		try{			
			file = new File(ServerConfig.RedisDumpFilePath);
			InputStream content = new FileInputStream(file);
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(file.length());
			String key = getRedisKey();
			client.putObject(ServerConfig.AlyunRedisBucket, key, content, meta);
			imgAddress = getOSSUrlPrefix(ServerConfig.AlyunRedisBucket) + key;
		} catch(ClientException | OSSException e){
			e.printStackTrace();  
			DebugLog.d(e);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			DebugLog.d(e);
		} finally{
			IdleConnectionReaper.shutdown();
			if(file != null){
				file.delete();
			}
		}		
		System.out.println("File Address is: " + imgAddress);
		return  imgAddress;	

	}

	private static String getRedisKey(){
		return "redis-backup-" + DateUtility.getTimeStamp();
	}

	private static String getOSSUrlPrefix(String Bucket){		
		return "http://" + Bucket + ".oss-internal.aliyuncs.com/";		

	}
}
