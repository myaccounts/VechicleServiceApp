package com.myaccounts.vechicleserviceapp.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.adeel.library.easyFTP;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadImageSignature extends AsyncTask<Void, Void, String> {

    //    private File f;
    private String mImageName,image;
    //    private Uri fileUri;
    private Context context;
    private ProgressDialog pdDialog;

    public DownloadImageSignature(String image,Context context) {
//        this.f = f;
        this.image = image;
//        this.fileUri = fileUri;
        this.context = context;

    }

    @Override
    protected String doInBackground(Void... params) {
        //uploadFile(f);
        String s;
        try {
            easyFTP ftp = new easyFTP();
            ftp.connect(ProjectVariables.FTP_HOST, ProjectVariables.FTP_USER, ProjectVariables.FTP_PASS);
            boolean status = false;
            status = ftp.setWorkingDirectory(ProjectVariables.IMAGE_FOLD);
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
            File mediaFile;
            mImageName = "MI_SIGNATURE" + timeStamp + ".jpg";
            ContextWrapper cw = new ContextWrapper(context);
            SessionManager sessionManager = new SessionManager(context);
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("captureImgDir", Context.MODE_PRIVATE);
            File mypath=new File(directory,mImageName);
            //InputStream targetStream = getResources().openRawResource(+R.drawable.ic_launcher);
//                InputStream targetStream = new FileInputStream(f);
//                ftp.uploadFile(targetStream, Image);
            ftp.downloadFile(ProjectVariables.IMAGE_FOLD+"/"+this.image,mypath.getAbsolutePath());
            Log.e("Status", status + "");
            if (status == true) {
                s = "File Attached Successfully...";
                sessionManager.storeSignatureImage1LocalPath(mypath.getAbsolutePath());
            } else {
                s = "File Attached Failure...";

            }
            pdDialog.dismiss();
            return new String(s.toString());
        } catch (Exception e) {
            pdDialog.dismiss();
            String t = "Failure : " + e.getLocalizedMessage();
            return t;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdDialog = new ProgressDialog(context);
        pdDialog.setMessage("Loading......");
        pdDialog.setCanceledOnTouchOutside(false);
        pdDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pdDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
