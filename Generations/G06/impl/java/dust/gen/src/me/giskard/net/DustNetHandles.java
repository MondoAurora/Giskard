package me.giskard.net;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustNetHandles extends DustConsts {


	MindHandle NET_ASP_HOST = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:0");
	MindHandle NET_ATT_HOST_PORT = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:1");
	MindHandle NET_ATT_HOST_IPV4 = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:2");

	MindHandle NET_ASP_SSLINFO = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:3");
	MindHandle NET_ATT_SSLINFO_PORT = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:4");
	MindHandle NET_ATT_SSLINFO_STOREPATH = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:5");
	MindHandle NET_ATT_SSLINFO_STOREPASS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:6");
	MindHandle NET_ATT_SSLINFO_KEYMANAGERPASS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:7");

	MindHandle NET_ASP_SRVCALL = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:8");
	MindHandle NET_ATT_SRVCALL_REQUEST = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:9");
	MindHandle NET_ATT_SRVCALL_RESPONSE = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:10");
	MindHandle NET_ATT_SRVCALL_METHOD = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:11");
	MindHandle NET_ATT_SRVCALL_PATHINFO = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:12");
	MindHandle NET_ATT_SRVCALL_PAYLOAD = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:13");
	MindHandle NET_ATT_SRVCALL_HEADERS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:14");
	MindHandle NET_ATT_SRVCALL_ATTRIBUTES = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:15");
	MindHandle NET_ATT_SRVCALL_STATUS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:16");

	MindHandle NET_ASP_SRVRESP = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:17");
	MindHandle NET_ATT_SRVRESP_STATUS = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:18");
	MindHandle NET_ATT_SRVRESP_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:19");
	MindHandle NET_ATT_SRVRESP_HEADER = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:20");
	MindHandle NET_ATT_SRVRESP_PAYLOAD = Dust.access(MindAccess.Lookup, "giskard.me:net_1.0:21");
// No text assigned to handle giskard.me:net_1.0:26
}

