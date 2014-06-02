package RedisBaseModule.serverMain;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

import RedisBaseModule.aliyun.AliyunMain;
import RedisBaseModule.common.DebugLog;


public class ServerMain {

	//private static Log log = LogFactory.getLog(ServiceMain.class);

		private static ServerMain me;

		private Component component;

		public void init(String[] arguments) {

		}

		/**
		 * Start the Thread, accept incoming connections
		 * 
		 * Use this entry point to start with embedded HTTP Server
		 * 
		 * @throws Exception
		 */
		public void start() throws Exception {
			component = new Component();

			// Add a new HTTP server listening on port
			Server server = component.getServers().add(Protocol.HTTP, 8018);
			server.getContext().getParameters().add("maxThreads", "256");


			// Start the component.		
			DebugLog.d("ready to start");
			component.start();
			
			// Back up
			DebugLog.d("Start Backup");
			AliyunMain.backupRedisDumpFile();
		}
		

		public static ServerMain getInstance() {
			if (me == null) {
				me = new ServerMain();
			}
			
			return me;
		}
		
		

		public static void main(String... args) throws Exception {
			DebugLog.initializeLogger();				
			try {
				ServerMain.getInstance().init(args);
				ServerMain.getInstance().start();
				DebugLog.d("Excuting");
			} catch (Exception e) {
				DebugLog.d(e);
			}
		}
}
