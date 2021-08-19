package com.myaccounts.vechicleserviceapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myaccounts.vechicleserviceapp.R;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PayNowActivity2016 extends Activity {
    SharedPreferences payNowSharedpreferences;
    Context context;
    String netAmount;
    String jobcardId,status;
    Button ok_btn;
    EditText cardEdt,cashEdt,upiEdt,invEdt;
    TextView totalTv,balanceTv;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout);
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            this.setFinishOnTouchOutside(false);
            context = this.getApplicationContext();
            sharedpreferences = this.getSharedPreferences("Mypref", Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            netAmount=getIntent().getStringExtra("Amount");
            jobcardId=getIntent().getStringExtra("Jobcard");
            status=getIntent().getStringExtra("SavingValues");
            cardEdt=(EditText)findViewById(R.id.cardAmount);
            cashEdt=(EditText)findViewById(R.id.cashEdt);
            upiEdt=(EditText)findViewById(R.id.upiAmount);
            invEdt=(EditText)findViewById(R.id.invoiceAmount);
            ok_btn=(Button)findViewById(R.id.btn_ok);
            totalTv=(TextView)findViewById(R.id.totalTv);
            balanceTv=(TextView)findViewById(R.id.balTv);
//            totalTv.setText(netAmount);
            Log.d("ANUSHA ","netamount "+netAmount);
            balanceTv.setText("");
            balanceTv.setText("Balance : "+netAmount);
            Log.d("ANUSHA ","netamount "+status);
            if(status.equalsIgnoreCase("TRUE"))
            setTextSharedPreferencesValue();
            cardEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        CalculateCardAmt();

                        //leditor.putString(KEY_SLECTED_STRING, status);
//                        leditor.commit();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            cashEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        CalculateCashAmt();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            upiEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        CalculateUPIAmt();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            invEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        CalculateInvAmt();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    caliculateOkValue();
                }
            });

        } catch (Exception e) {
            Log.d("ANUSHA ","EXCEPTION "+e.toString());
        }

    }

    private void setTextSharedPreferencesValue() {
        String cashEdtText=getshredPrefences("cashEdt");
        String cardEdtText=getshredPrefences("cardEdt");
        String upiEdtText=getshredPrefences("upiEdt");
        String invoiceEdtText=getshredPrefences("invEdt");//shredPrefencesSaving("balanceTv",balanceTv.getText().toString());
//        String balanceEdtText=getshredPrefences("balanceTv");
        float cardAmt=0,cashAmt=0,upiAmt=0,invoiceAmt=0;
        if(!cardEdtText.isEmpty()) {
            cardAmt = Float.parseFloat(cardEdtText);
            cardEdt.setText(cardEdtText);

        }else {
            cardEdt.setText("");
        }
        if(!cashEdtText.isEmpty()) {
            cashAmt = Float.parseFloat(cashEdtText);
            cashEdt.setText(cashEdtText);

        }else{
            cashEdt.setText("");

        }
        if(!upiEdtText.isEmpty()) {
            upiAmt = Float.parseFloat(upiEdtText);
            upiEdt.setText(upiEdtText);

        }else{
            upiEdt.setText("");

        }
        if(!invoiceEdtText.isEmpty()) {
            invoiceAmt = Float.parseFloat(invoiceEdtText);
            invEdt.setText(invoiceEdtText);
        }else{
            invEdt.setText("");
        }

        float totalAmt=cardAmt+cashAmt+upiAmt+invoiceAmt;
        float balAmt=(Float.valueOf(netAmount)-totalAmt);
        String total=String.valueOf(totalAmt);
        String balance=String.valueOf(balAmt);
        totalTv.setText("Total : "+total);
        balanceTv.setText("Balance : "+balance);

    }

    private void CalculateInvAmt() {
        try {
            String strCardAmt = cardEdt.getText().toString().trim();
            String strCashAmt = cashEdt.getText().toString().trim();
            String strUpiAmt = upiEdt.getText().toString().trim();
            String strInvoiceAmt = invEdt.getText().toString().trim();
            String strSubTotal = netAmount;

            if (strInvoiceAmt.length() <= 0) {
                strInvoiceAmt = "0";
            }
            if (strCardAmt.length() <= 0) {
                strCardAmt = "0";
            }
            if (strUpiAmt.length() <= 0) {
                strUpiAmt = "0";
            }
            if (strCashAmt.length() <= 0) {
                strCashAmt = "0";
            }
            if (strSubTotal.length() <= 0) {
                strSubTotal = "0";
            }

            float subtotal = Float.valueOf(strSubTotal);
            float CardAmt = Float.parseFloat(strCardAmt);
            float CashAmt = Float.parseFloat(strCashAmt);
            float UpiAmt = Float.parseFloat(strUpiAmt);
            float InvoiceAmt = Float.parseFloat(strInvoiceAmt);

            if ((CardAmt + CashAmt + UpiAmt+InvoiceAmt) > subtotal) {
                float RcAmt = (CardAmt + CashAmt) - subtotal;
                String strRCAmt = String.format("%.2f", RcAmt);
                invEdt.setError("Cash amount shoudn't be greater than Pay Amount !");
                invEdt.setText("");
                invEdt.requestFocus();
                //   txtViwRCAmt.setText(strRCAmt);
            }
            totalTv.setText("");
            balanceTv.setText("");
            float cardAmt=0,cashAmt=0,upiAmt=0,invoiceAmt=0;
            if(!cardEdt.getText().toString().isEmpty())
                cardAmt=Float.parseFloat(cardEdt.getText().toString());
            if(!cashEdt.getText().toString().isEmpty())
                cashAmt=Float.parseFloat(cashEdt.getText().toString());
            if(!upiEdt.getText().toString().isEmpty())
                upiAmt=Float.parseFloat(upiEdt.getText().toString());
            if(!invEdt.getText().toString().isEmpty())
                invoiceAmt=Float.parseFloat(invEdt.getText().toString());
            float totalAmt=cardAmt+cashAmt+upiAmt+invoiceAmt;
            float balAmt=(Float.valueOf(netAmount)-totalAmt);
            String total=String.valueOf(totalAmt);
            String balance=String.valueOf(balAmt);
            totalTv.setText("Total : "+total);
            balanceTv.setText("Balance : "+balance);
            shredPrefencesSaving("cardEdt",cardEdt.getText().toString());
            shredPrefencesSaving("cashEdt",cashEdt.getText().toString());
            shredPrefencesSaving("upiEdt",upiEdt.getText().toString());
            shredPrefencesSaving("invEdt",invEdt.getText().toString());
            shredPrefencesSaving("totalTv",totalTv.getText().toString());
            shredPrefencesSaving("balanceTv",balanceTv.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void caliculateOkValue() {
        try {
            String strCardAmt = cardEdt.getText().toString().trim();
            String strCashAmt = cashEdt.getText().toString().trim();
            String strUpiAmt = upiEdt.getText().toString().trim();
            String strInvoiceAmt = invEdt.getText().toString().trim();
            String strSubTotal = netAmount;

            if (strInvoiceAmt.length() <= 0) {
                strInvoiceAmt = "0";
            }
            if (strCardAmt.length() <= 0) {
                strCardAmt = "0";
            }
            if (strUpiAmt.length() <= 0) {
                strUpiAmt = "0";
            }
            if (strCashAmt.length() <= 0) {
                strCashAmt = "0";
            }
            if (strSubTotal.length() <= 0) {
                strSubTotal = "0";
            }

            float subtotal = Float.valueOf(strSubTotal);
            float CardAmt = Float.parseFloat(strCardAmt);
            float CashAmt = Float.parseFloat(strCashAmt);
            float UpiAmt = Float.parseFloat(strUpiAmt);
            float InvoiceAmt = Float.parseFloat(strInvoiceAmt);

            if ((CardAmt + CashAmt + UpiAmt + InvoiceAmt) > subtotal) {
//                upiEdt.setError("Total Amount shoudn't be greater than Pay Amount !");
                Toast.makeText(context,"Total Amount shoudn't be greater than Pay Amount !",Toast.LENGTH_LONG).show();

            }if((CardAmt + CashAmt + UpiAmt + InvoiceAmt) < subtotal) {
//                upiEdt.setError("Total Amount shoudn't be lesser than Pay Amount !");
                Toast.makeText(context,"Total Amount shoudn't be lesser than Pay Amount !",Toast.LENGTH_LONG).show();

            }if((CardAmt + CashAmt + UpiAmt +InvoiceAmt) == subtotal) {
              //  upiEdt.setError("Cash amount shoudn't be greater than Pay Amount !");
//                finish();
                shredPrefencesSaving("cardEdt",cardEdt.getText().toString());
                shredPrefencesSaving("cashEdt",cashEdt.getText().toString());
                shredPrefencesSaving("upiEdt",upiEdt.getText().toString());
                shredPrefencesSaving("invEdt",invEdt.getText().toString());
                shredPrefencesSaving("totalTv",totalTv.getText().toString());
                shredPrefencesSaving("balanceTv",balanceTv.getText().toString());
                Intent itemIntent = new Intent();
                itemIntent.putExtra("SavingValues", "TRUE");
                itemIntent.putExtra("cardEdt", cardEdt.getText().toString());
                itemIntent.putExtra("cashEdt", cashEdt.getText().toString());
                itemIntent.putExtra("upiEdt", upiEdt.getText().toString());
                itemIntent.putExtra("invEdt", invEdt.getText().toString());
                Log.d("ANUSHA "," cardedt "+cardEdt.getText().toString());
                Log.d("ANUSHA "," cashEdt "+cashEdt.getText().toString());
                Log.d("ANUSHA "," upiEdt "+upiEdt.getText().toString());
                Log.d("ANUSHA "," invEdt "+invEdt.getText().toString());
                setResult(RESULT_OK, itemIntent);
                finish();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CalculateUPIAmt() {
        try {
            String strCardAmt = cardEdt.getText().toString().trim();
            String strCashAmt = cashEdt.getText().toString().trim();
            String strUpiAmt = upiEdt.getText().toString().trim();
            String strInvoiceAmt = invEdt.getText().toString().trim();
            String strSubTotal = netAmount;

            if (strInvoiceAmt.length() <= 0) {
                strInvoiceAmt = "0";
            }
            if (strCardAmt.length() <= 0) {
                strCardAmt = "0";
            }
            if (strUpiAmt.length() <= 0) {
                strUpiAmt = "0";
            }
            if (strCashAmt.length() <= 0) {
                strCashAmt = "0";
            }
            if (strSubTotal.length() <= 0) {
                strSubTotal = "0";
            }

            float subtotal = Float.valueOf(strSubTotal);
            float CardAmt = Float.parseFloat(strCardAmt);
            float CashAmt = Float.parseFloat(strCashAmt);
            float UpiAmt = Float.parseFloat(strUpiAmt);
            float InvoiceAmt = Float.parseFloat(strInvoiceAmt);

            if ((CardAmt + CashAmt + UpiAmt+InvoiceAmt) > subtotal) {
                float RcAmt = (CardAmt + CashAmt) - subtotal;
                String strRCAmt = String.format("%.2f", RcAmt);
                upiEdt.setError("Cash amount shoudn't be greater than Pay Amount !");
                upiEdt.setText("");
                upiEdt.requestFocus();
                //   txtViwRCAmt.setText(strRCAmt);
            }
            totalTv.setText("");
            balanceTv.setText("");
            float cardAmt=0,cashAmt=0,upiAmt=0,invoiceAmt=0;
            if(!cardEdt.getText().toString().isEmpty())
                cardAmt=Float.parseFloat(cardEdt.getText().toString());
            if(!cashEdt.getText().toString().isEmpty())
                cashAmt=Float.parseFloat(cashEdt.getText().toString());
            if(!upiEdt.getText().toString().isEmpty())
                upiAmt=Float.parseFloat(upiEdt.getText().toString());
            if(!invEdt.getText().toString().isEmpty())
                invoiceAmt=Float.parseFloat(invEdt.getText().toString());
            float totalAmt=cardAmt+cashAmt+upiAmt+invoiceAmt;
            float balAmt=(Float.valueOf(netAmount)-totalAmt);
            String total=String.valueOf(totalAmt);
            String balance=String.valueOf(balAmt);
            totalTv.setText("Total : "+total);
            balanceTv.setText("Balance : "+balance);
            shredPrefencesSaving("cardEdt",cardEdt.getText().toString());
            shredPrefencesSaving("cashEdt",cashEdt.getText().toString());
            shredPrefencesSaving("upiEdt",upiEdt.getText().toString());
            shredPrefencesSaving("invEdt",invEdt.getText().toString());
            shredPrefencesSaving("totalTv",totalTv.getText().toString());
            shredPrefencesSaving("balanceTv",balanceTv.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CalculateCashAmt() {
        try {
            String strCardAmt = cardEdt.getText().toString().trim();
            String strCashAmt = cashEdt.getText().toString().trim();
            String strUpiAmt = upiEdt.getText().toString().trim();
            String strInvoiceAmt = invEdt.getText().toString().trim();
            String strSubTotal = netAmount;

            if (strInvoiceAmt.length() <= 0) {
                strInvoiceAmt = "0";
            }
            if (strCardAmt.length() <= 0) {
                strCardAmt = "0";
            }
            if (strCashAmt.length() <= 0) {
                strCashAmt = "0";
            }
            if (strSubTotal.length() <= 0) {
                strSubTotal = "0";
            }
            if (strUpiAmt.length() <= 0) {
                strUpiAmt = "0";
            }

            float subtotal = Float.valueOf(strSubTotal);
            float CardAmt = Float.parseFloat(strCardAmt);
            float CashAmt = Float.parseFloat(strCashAmt);
            float UpiAmt = Float.parseFloat(strUpiAmt);
            float InvoiceAmt = Float.parseFloat(strInvoiceAmt);

            if ((CardAmt + CashAmt + UpiAmt + InvoiceAmt) > subtotal) {
                float RcAmt = (CardAmt + CashAmt) - subtotal;
                String strRCAmt = String.format("%.2f", RcAmt);
                cashEdt.setError("Cash amount shoudn't be greater than Pay Amount !");
                cashEdt.setText("");
                cashEdt.requestFocus();
             //   txtViwRCAmt.setText(strRCAmt);
            }
            totalTv.setText("");
            balanceTv.setText("");
            float cardAmt=0,cashAmt=0,upiAmt=0,invoiceAmt=0;
            if(!cardEdt.getText().toString().isEmpty())
                cardAmt=Float.parseFloat(cardEdt.getText().toString());
            if(!cashEdt.getText().toString().isEmpty())
                cashAmt=Float.parseFloat(cashEdt.getText().toString());
            if(!upiEdt.getText().toString().isEmpty())
                upiAmt=Float.parseFloat(upiEdt.getText().toString());
            if(!invEdt.getText().toString().isEmpty())
                invoiceAmt=Float.parseFloat(invEdt.getText().toString());
            float totalAmt=cardAmt+cashAmt+upiAmt+invoiceAmt;
            float balAmt=(Float.valueOf(netAmount)-totalAmt);
            String total=String.valueOf(totalAmt);
            String balance=String.valueOf(balAmt);
            totalTv.setText("Total : "+total);
            balanceTv.setText("Balance : "+balance);
            shredPrefencesSaving("cardEdt",cardEdt.getText().toString());
            shredPrefencesSaving("cashEdt",cashEdt.getText().toString());
            shredPrefencesSaving("upiEdt",upiEdt.getText().toString());
            shredPrefencesSaving("invEdt",invEdt.getText().toString());
            shredPrefencesSaving("totalTv",totalTv.getText().toString());
            shredPrefencesSaving("balanceTv",balanceTv.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CalculateCardAmt() {
        try {
            String strCashAmt = cashEdt.getText().toString().trim();
            String strCardAmt = cardEdt.getText().toString().trim();
            String strUpiAmt = upiEdt.getText().toString().trim();
            String strInvoiceAmt = invEdt.getText().toString().trim();

            if (strInvoiceAmt.length() <= 0) {
                strInvoiceAmt = "0";
            }
            if (strCardAmt.length() <= 0) {
                strCardAmt = "0";
            }
            if (strCashAmt.length() <= 0) {
                strCashAmt = "0";
            }
            if (strUpiAmt.length() <= 0) {
                strUpiAmt = "0";
            }

            String strSubTotal = netAmount;
            if (strSubTotal.length() <= 0) {
                strSubTotal = "0";
            }

            float subtotal = Float.valueOf(strSubTotal);
            float CashAmt = Float.parseFloat(strCashAmt);
            float CardAmt = Float.parseFloat(strCardAmt);
            float UpiAmt = Float.parseFloat(strUpiAmt);
            float InviceAmt = Float.parseFloat(strInvoiceAmt);

            if (CashAmt+CardAmt + UpiAmt +InviceAmt > subtotal) {
                try {
                    cardEdt.setError("Card amount shoudn't be greater than Pay Amount !");
                    cardEdt.setText("");
                    cardEdt.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } /*else {
                float cashAmt = subtotal - CardAmt;
                String strCashAmt = String.format("%.2f", cashAmt);
                cashEdt.setText(strCashAmt);
            }*/
            totalTv.setText("");
            balanceTv.setText("");
            float cardAmt=0,cashAmt=0,upiAmt=0,invAmt=0;
            if(!cardEdt.getText().toString().isEmpty())
                cardAmt=Float.parseFloat(cardEdt.getText().toString());
            if(!cashEdt.getText().toString().isEmpty())
                cashAmt=Float.parseFloat(cashEdt.getText().toString());
            if(!upiEdt.getText().toString().isEmpty())
                upiAmt=Float.parseFloat(upiEdt.getText().toString());
            if(!invEdt.getText().toString().isEmpty())
                invAmt=Float.parseFloat(invEdt.getText().toString());
            float totalAmt=cardAmt+cashAmt+upiAmt+invAmt;
            float balAmt=(Float.valueOf(netAmount)-totalAmt);
            String total=String.valueOf(totalAmt);
            String balance=String.valueOf(balAmt);
            totalTv.setText("Total : "+total);
            balanceTv.setText("Balance : "+balance);
            Log.d("ANUSHA ","total "+total);
            shredPrefencesSaving("cardEdt",cardEdt.getText().toString());
            shredPrefencesSaving("cashEdt",cashEdt.getText().toString());
            shredPrefencesSaving("upiEdt",upiEdt.getText().toString());
            shredPrefencesSaving("invEdt",invEdt.getText().toString());
            shredPrefencesSaving("totalTv",totalTv.getText().toString());
            shredPrefencesSaving("balanceTv",balanceTv.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void shredPrefencesSaving(String keyvalue,String value){
        editor.putString(keyvalue, value);
        editor.commit();
        Log.d("ANUSHA ","___key"+keyvalue);
        Log.d("ANUSHA ","___value"+value);
        Log.d("ANUSHA ","___shared preferences value"+sharedpreferences.getString(keyvalue,null));
    }
    public String getshredPrefences(String keyvalue){
        String getSharedPreferencesValue=sharedpreferences.getString(keyvalue,null);
        Log.d("ANUSHA ","___shared preferences value"+sharedpreferences.getString(keyvalue,null));
        return getSharedPreferencesValue;
    }
}