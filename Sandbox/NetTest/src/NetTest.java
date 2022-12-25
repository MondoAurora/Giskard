import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NetTest {

	public static void main(String[] args) throws Exception {

		sslCertDisable();

		String root = "https://www.valasztas.hu/egyeni-valasztokeruletek-eredmenye";
		InputStream is = getPage(root, "head");
		Document doc = Jsoup.parse(is, "UTF-8", root);
		Pattern pt = Pattern.compile(".*megyeKod=(\\d+)&.*oevkKod=(\\d+)");
		Pattern ptVote = Pattern.compile("\\s*([ 0123456789]+)\\([,0123456789]+%\\)");

		Map<String, Integer> ptCol = new HashMap<>();
		XSSFWorkbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		XSSFSheet sheet = workbook.createSheet("Votes");
		int rowNum = 0;

		Row rowHead = sheet.createRow(rowNum++);
		int colNum = 0;
		Cell cell = rowHead.createCell(colNum++);
		cell.setCellValue("Const");
		cell = rowHead.createCell(colNum++);
		cell.setCellValue("County");
		cell = rowHead.createCell(colNum++);
		cell.setCellValue("Num");

		for (Element e : doc.getElementsByClass("nvi-link")) {
			Element link = e.getElementsByTag("a").first();
			String addr = link.attr("href");

			Matcher m = pt.matcher(addr);

			if ( m.matches() ) {
				String name = link.text();
				System.out.println(name);

				String fName = name + "_" + m.group(1) + "_" + m.group(2);
				InputStream ispg = getPage(addr, fName);
				Document docPg = Jsoup.parse(ispg, "UTF-8", addr);

				Row row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(name);
				XSSFHyperlink l = (XSSFHyperlink)createHelper.createHyperlink(0);
	      l.setAddress(addr);
	      cell.setHyperlink((XSSFHyperlink) l);
				cell = row.createCell(colNum++);
				cell.setCellValue(m.group(1));
				cell = row.createCell(colNum++);
				cell.setCellValue(m.group(2));

				for (Element e1 : docPg.getElementsByClass("list-item")) {
					String orgName = null;
					String votes = "?";
					for (Element e2 : e1.getElementsByTag("a")) {
						if ( e2.attr("href").contains("jelolo-szervezet-adatlap") ) {
							orgName = e2.getElementsByTag("span").first().ownText();
							break;
						}
					}

					if ( null != orgName ) {
						Integer c = ptCol.get(orgName);
						if ( null == c ) {
							c = ptCol.size() + colNum;
							ptCol.put(orgName, c);
							cell = rowHead.createCell(c);
							cell.setCellValue(orgName);
						}

						for (Element e2 : e1.getElementsMatchingOwnText(ptVote)) {
							votes = e2.text();
							Matcher vm = ptVote.matcher(votes);
							if ( vm.matches() ) {
								votes = vm.group(1);
								votes = votes.replaceAll(" ", "");
								break;
							}
						}

						cell = row.createCell(c);
						cell.setCellValue(votes);

						System.out.println("   " + orgName + ": " + votes);
					}

				}
			}
		}

		workbook.write(new FileOutputStream("result/Vote2018.xlsx", false));
		workbook.close();

	}

	public static void sslCertDisable() throws NoSuchAlgorithmException, KeyManagementException {
		/*
		 * fix for Exception in thread "main" javax.net.ssl.SSLHandshakeException:
		 * sun.security.validator.ValidatorException: PKIX path building failed:
		 * sun.security.provider.certpath.SunCertPathBuilderException: unable to find
		 * valid certification path to requested target
		 */
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/*
		 * end of the fix
		 */
	}

	public static InputStream getPage(String address, String fn) throws Exception {
		File f = new File("web/" + fn + ".html");

		if ( !f.isFile() ) {
			URL myurl = new URL(address);

			InputStream ins = myurl.openStream();
			InputStreamReader isr = new InputStreamReader(ins);
			BufferedReader in = new BufferedReader(isr);
			String inputLine;

			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			while ((inputLine = in.readLine()) != null) {
				bw.write(inputLine);
			}
			bw.close();

			in.close();
		}

		return new FileInputStream(f);
	}

}
