package com.myaccounts.vechicleserviceapp.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.Printernew.InitializePrinter;
import com.myaccounts.vechicleserviceapp.Utils.ProjectMethods;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Vishal on 6/20/2017.
 */

public class GMailSender extends javax.mail.Authenticator {
    private static SharedPreferences otpSharedpreferences;
    public static final String OTP_PREFERENCES = "OTP Preferences";
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    Context ctx;
    int otp;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(Context ctx,String user, String password,int otp) {
        this.user = user;
        this.password = password;
        this.ctx=ctx;
        this.otp=otp;
        otpSharedpreferences = ctx.getSharedPreferences(OTP_PREFERENCES, Context.MODE_PRIVATE);
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
        try{
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);
            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            Transport.send(message);
            DatabaseHelper db = new DatabaseHelper(ctx);
            db.insert_EmailidDetails(recipients, "admin");
            SharedPreferences.Editor edit1 = otpSharedpreferences.edit();
            edit1.putInt(InitializePrinter.OTP,otp);
            edit1.commit();
            Toast.makeText(ctx,"OTP Sent Successfully",Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Log.d("mylog", "Error in sending: " + e.toString());
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}
