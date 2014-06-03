package RedisBaseModule.serverMain;

import RedisBaseModule.aliyun.AliyunMain;
import RedisBaseModule.common.DebugLog;


public class RedisBackUpMain {

		public static void main(String... args) throws Exception {
			DebugLog.initializeLogger();
			AliyunMain.backupRedisDumpFile();
		}

}
