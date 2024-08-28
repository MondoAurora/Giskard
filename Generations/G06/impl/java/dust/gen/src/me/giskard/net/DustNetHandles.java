package me.giskard.net;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustNetHandles extends DustConsts {


	MindHandle DH_NET_HOST = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:0");
	MindHandle DH_NET_HOST_PORT = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:1");
	MindHandle DH_NET_HOST_IPV4 = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:2");

	MindHandle DH_NET_SSLINFO = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:3");
	MindHandle DH_NET_SSLINFO_PORT = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:4");
	MindHandle DH_NET_SSLINFO_STOREPATH = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:5");
	MindHandle DH_NET_SSLINFO_STOREPASS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:6");
	MindHandle DH_NET_SSLINFO_KEYMANAGERPASS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:7");

	MindHandle DH_NET_SRVCALL = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:8");
	MindHandle DH_NET_SRVCALL_REQUEST = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:9");
	MindHandle DH_NET_SRVCALL_RESPONSE = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:10");
	MindHandle DH_NET_SRVCALL_METHOD = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:11");
	MindHandle DH_NET_SRVCALL_PATHINFO = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:12");
	MindHandle DH_NET_SRVCALL_PAYLOAD = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:13");
	MindHandle DH_NET_SRVCALL_HEADERS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:14");
	MindHandle DH_NET_SRVCALL_ATTRIBUTES = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:15");
	MindHandle DH_NET_SRVCALL_STATUS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:16");

	MindHandle DH_NET_SRVRESP = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:17");
	MindHandle DH_NET_SRVRESP_STATUS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:18");
	MindHandle DH_NET_SRVRESP_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:19");
	MindHandle DH_NET_SRVRESP_HEADER = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:20");
	MindHandle DH_NET_SRVRESP_PAYLOAD = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:21");

	MindHandle DH_NET_DOWNLOAD = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:22");

	MindHandle DH_NET_HTTPSRV = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:23");

	MindHandle DH_NET_HTTPSVCFILES = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:24");

	MindHandle DH_NET_HTTPSVCJSONAPI = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:25");

	MindHandle DH_NET_HTTPCLICOMM = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:26");
}

