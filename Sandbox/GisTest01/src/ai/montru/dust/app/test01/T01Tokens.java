package ai.montru.dust.app.test01;

import ai.montru.giskard.Giskard;
import ai.montru.tokens.MontruDustTokens;
import ai.montru.tokens.MontruMindTokens;
import ai.montru.tokens.MontruUtilTokens;

public interface T01Tokens extends MontruDustTokens, MontruMindTokens, MontruUtilTokens {

	GiskardEntityRef GIS_UNI_TEST01 = Giskard.getToken("Montru/Dust/Test01/1/0", null);

	GiskardEntityRef GIS_TYP_TEST01AGENT01 = Giskard.getToken("Test01Agent01", GIS_UNI_TEST01);

}
