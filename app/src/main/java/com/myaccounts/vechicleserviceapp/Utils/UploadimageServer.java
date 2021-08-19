package com.myaccounts.vechicleserviceapp.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.adeel.library.easyFTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class UploadimageServer extends AsyncTask<Void, Void, String> {

    private File f;
    private String Image;
    private Uri fileUri;
    private Context context;
    private ProgressDialog pdDialog;

    public UploadimageServer(File f, String image, Uri fileUri, Context context) {
        this.f = f;
        Image = image;
        this.fileUri = fileUri;
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
            //InputStream targetStream = getResources().openRawResource(+R.drawable.ic_launcher);
            if (fileUri == null) {
                InputStream targetStream = new FileInputStream(f);
                ftp.uploadFile(targetStream, Image);

            } else {
                InputStream stream = context.getContentResolver().openInputStream(fileUri);
                ftp.uploadFile(stream, Image);
            }
            Log.e("Status", status + "");
            if (status == true) {
                s = "File Attached Successfully...";
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
