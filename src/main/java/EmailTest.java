import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import sun.net.www.http.HttpClient;
import sun.security.krb5.Credentials;

import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.dao.TblSubscriber;


public class EmailTest {
	public static void main(String[] args) throws IOException {
		 

		    URL url = new URL("http://kimegitsem.zapto.org/kimegitsem/in/Ekol_Otomotiv");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.flush();

		    // Get the response
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		       System.out.println(line);
		    }
	}
	public static void main1(String[] args) {
				final ApplicationContext context=new ClassPathXmlApplicationContext("mailContext.xml");
				System.setProperty("javax.net.ssl.trustStore", "c:/truststore");
		     //   System.setProperty("javax.net.ssl.keyStorePassword", "turkiye51");
				String[] addresses=new String[]{"eozkurt@gmail.com"};
				for(int i=0;i<addresses.length;i++){
					final String address=addresses[i];
					MimeMessagePreparator mimepreparator = new MimeMessagePreparator() {
						public void prepare(MimeMessage mimeMessage) throws Exception {
							MimeMessageHelper message = new MimeMessageHelper(
									mimeMessage);
							// mail sending parameters
							message.setTo(address);
							message.setFrom("davet@kimegitsem.com");
							message.setSubject("sana kimegitsem?com’dan arkadaslik istegi gonderdi");
							Map model = new HashMap();
							model.put("name","erkan");
							model.put("surname", "özkurt");
							model.put("profile","yok");
							model.put("logo","yok");
							model.put("connect","yoko");
							model.put("context",ApplicationConstants.getContext());
							
							VelocityEngine velocityEngine=(VelocityEngine)context.getBean("velocityEngine");
							/*velocityEngine.setProperty( VelocityEngine.INPUT_ENCODING, "Windows-1254");
							velocityEngine.setProperty( VelocityEngine.OUTPUT_ENCODING, "Windows-1254");
							velocityEngine.setProperty(VelocityEngine.ENCODING_DEFAULT, "Windows-1254");*/

							String mailContent = VelocityEngineUtils
									.mergeTemplateIntoString(velocityEngine,
											"invitation.vm", "UTF-8", model);
							message.setText(mailContent, true);
							
		
						}
					};
					JavaMailSender mailSender=(JavaMailSender)context.getBean("mailSender");
					mailSender.send(mimepreparator);
				}
			}

		}