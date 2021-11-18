package ai.montru.dust.node;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts.GiskardAgent;

public abstract class DustNodeAgentCtrl implements DustNodeConsts, GiskardAgent {

	public static class DustVisitor extends DustNodeAgentCtrl {

		@Override
		public GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException {
			return GiskardResponse.Reject;
		}

	}

	public static class DustIterator extends DustNodeAgentCtrl {

		@Override
		public GiskardResponse gisAgentProcess(GiskardAction action) throws GiskardException {
			GiskardResponse ret = GiskardResponse.Read;

			Integer min;
			Integer max;
			Integer idx;
			Integer cnt;

			switch ( action ) {
			case Init:
				break;
			case Begin:
				Giskard.access(GiskardAccess.Set, 0, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICCOUNT);

				idx = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);
				if ( null != idx ) {
					max = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_RANGEINTMAX);
					if ( (null != max) && (0 > max.compareTo(idx)) ) {
						return GiskardResponse.Reject;
					}
				}

				min = Giskard.access(GiskardAccess.Peek, 0, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_RANGEINTMIN);
				Giskard.access(GiskardAccess.Set, min, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);

				break;
			case Process:
				idx = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);

				Giskard.broadcastEvent(null, "Iterator",
						Giskard.access(GiskardAccess.Peek, "huh?", null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_ID), "index", idx);

				cnt = Giskard.access(GiskardAccess.Peek, 0, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICCOUNT);
				Giskard.access(GiskardAccess.Set, ++cnt, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICCOUNT);

				Giskard.access(GiskardAccess.Set, ++idx, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_GENERICINDEX);
				max = Giskard.access(GiskardAccess.Peek, null, null, GIS_ATT_DUST_THISINSTANCE, GIS_ATT_UTIL_RANGEINTMAX);
				ret = (0 <= max.compareTo(idx)) ? GiskardResponse.Read : GiskardResponse.Accept;

				break;
			case End:
				break;
			case Release:
				break;
			}

			return ret;
		}

	}

}
