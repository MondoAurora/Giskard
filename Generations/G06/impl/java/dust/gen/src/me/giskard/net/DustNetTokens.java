package me.giskard.net;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustNetTokens extends DustConsts {


	MindToken TOKEN_NET_HOST = Dust.access(null, "giskard.me:net_1.0:0");
	MindToken TOKEN_NET_HOST_PORT = Dust.access(null, "giskard.me:net_1.0:1");
	MindToken TOKEN_NET_HOST_IPV4 = Dust.access(null, "giskard.me:net_1.0:2");

	MindToken TOKEN_NET_SSLINFO = Dust.access(null, "giskard.me:net_1.0:3");
	MindToken TOKEN_NET_SSLINFO_PORT = Dust.access(null, "giskard.me:net_1.0:4");
	MindToken TOKEN_NET_SSLINFO_STOREPATH = Dust.access(null, "giskard.me:net_1.0:5");
	MindToken TOKEN_NET_SSLINFO_STOREPASS = Dust.access(null, "giskard.me:net_1.0:6");
	MindToken TOKEN_NET_SSLINFO_KEYMANAGERPASS = Dust.access(null, "giskard.me:net_1.0:7");

	MindToken TOKEN_NET_SRVCALL = Dust.access(null, "giskard.me:net_1.0:8");
	MindToken TOKEN_NET_SRVCALL_REQUEST = Dust.access(null, "giskard.me:net_1.0:9");
	MindToken TOKEN_NET_SRVCALL_RESPONSE = Dust.access(null, "giskard.me:net_1.0:10");
	MindToken TOKEN_NET_SRVCALL_METHOD = Dust.access(null, "giskard.me:net_1.0:11");
	MindToken TOKEN_NET_SRVCALL_PATHINFO = Dust.access(null, "giskard.me:net_1.0:12");
	MindToken TOKEN_NET_SRVCALL_PAYLOAD = Dust.access(null, "giskard.me:net_1.0:13");
	MindToken TOKEN_NET_SRVCALL_HEADERS = Dust.access(null, "giskard.me:net_1.0:14");
	MindToken TOKEN_NET_SRVCALL_ATTRIBUTES = Dust.access(null, "giskard.me:net_1.0:15");
	MindToken TOKEN_NET_SRVCALL_STATUS = Dust.access(null, "giskard.me:net_1.0:16");

	MindToken TOKEN_NET_SRVRESP = Dust.access(null, "giskard.me:net_1.0:17");
	MindToken TOKEN_NET_SRVRESP_STATUS = Dust.access(null, "giskard.me:net_1.0:18");
	MindToken TOKEN_NET_SRVRESP_TYPE = Dust.access(null, "giskard.me:net_1.0:19");
	MindToken TOKEN_NET_SRVRESP_HEADER = Dust.access(null, "giskard.me:net_1.0:20");
	MindToken TOKEN_NET_SRVRESP_PAYLOAD = Dust.access(null, "giskard.me:net_1.0:21");

	MindToken TOKEN_NET_DOWNLOAD = Dust.access(null, "giskard.me:net_1.0:22");

	MindToken TOKEN_NET_HTTPSRV = Dust.access(null, "giskard.me:net_1.0:23");

	MindToken TOKEN_NET_HTTPSVCFILES = Dust.access(null, "giskard.me:net_1.0:24");

	MindToken TOKEN_NET_HTTPSVCJSONAPI = Dust.access(null, "giskard.me:net_1.0:25");

	MindToken TOKEN_NET_HTTPCLICOMM = Dust.access(null, "giskard.me:net_1.0:26");
}

