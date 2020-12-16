package com.softcraft.calendar.MarriageMatch;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.webkit.WebView;

import com.softcraft.calendar.History.TamilCalendarApplication;
import com.softcraft.calendar.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTMLViewActivity extends AppCompatActivity {
    private TamilCalendarApplication application;
    private WebView webView;

    private class ThemeHTMLFile extends AsyncTask<String, String, String> {
        private ThemeHTMLFile() {
        }

        protected String doInBackground(String... strArr) {
            return HTMLViewActivity.this.setHTMLTheme(HTMLViewActivity.this.getHTMLAsString());
        }

        protected void onPostExecute(String str) {
            HTMLViewActivity.this.webView.loadData(str, "text/html; charset=utf-8", "UTF-8");
        }
    }

    public void finish() {
        setResult(-1, new Intent());
        super.finish();
    }

    protected void onCreate(Bundle bundle) {
        this.application = (TamilCalendarApplication) getApplication();

        super.onCreate(bundle);
        setContentView((int) R.layout.htmlview_layout);
        this.webView = (WebView) findViewById(R.id.htmlview);
        setTitle(getIntent().getStringExtra("TITLE"));
        new ThemeHTMLFile().execute(new String[0]);
    }



    public void onBackPressed() {

        super.onBackPressed();
    }

    public String setHTMLTheme(String str) {
        return str;//.replaceAll("#FCEEC9", convertColorToHex(this.application.theme.liteColor)).replaceAll("#BA2026", convertColorToHex(this.application.theme.darkColor));
    }

    private String convertColorToHex(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        stringBuilder.append(Integer.toHexString(i & ViewCompat.MEASURED_SIZE_MASK));
        return stringBuilder.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x007d A:{SYNTHETIC, Splitter: B:39:0x007d} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0085 A:{Catch:{ Exception -> 0x0081 }} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0066 A:{SYNTHETIC, Splitter: B:29:0x0066} */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006e A:{Catch:{ Exception -> 0x006a }} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007d A:{SYNTHETIC, Splitter: B:39:0x007d} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0085 A:{Catch:{ Exception -> 0x0081 }} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0066 A:{SYNTHETIC, Splitter: B:29:0x0066} */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006e A:{Catch:{ Exception -> 0x006a }} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007d A:{SYNTHETIC, Splitter: B:39:0x007d} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0085 A:{Catch:{ Exception -> 0x0081 }} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public String getHTMLAsString() {
        IOException e;
        Throwable th;
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader bufferedReader;
        try {
            AssetManager assets = getAssets();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("htm/");
            stringBuilder2.append(getIntent().getStringExtra("HTML_URL"));
            InputStream open = assets.open(stringBuilder2.toString());
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(open, "UTF-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        stringBuilder.append(readLine);
                    } catch (IOException e2) {
                        InputStream inputStream2 = open;
                        e = e2;
                        inputStream = inputStream2;
                        try {
                            e.printStackTrace();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return stringBuilder.toString();
                        } catch (Throwable th2) {
                            th = th2;
                            if (inputStream != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        inputStream = open;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                throw th;
                            }
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                }
                bufferedReader.close();
                if (open != null) {
                    try {
                        open.close();
                    } catch (Exception e32) {
                        e32.printStackTrace();
                    }
                }
                bufferedReader.close();
            } catch (IOException e4) {
                IOException iOException = e4;
                bufferedReader = null;
                inputStream = open;
                e = iOException;
                e.printStackTrace();
                if (inputStream != null) {
                }
                if (bufferedReader != null) {
                }
                return stringBuilder.toString();
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = null;
                inputStream = open;
                if (inputStream != null) {
                }
                if (bufferedReader != null) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            bufferedReader = null;
            e.printStackTrace();
            if (inputStream != null) {
            }
            if (bufferedReader != null) {
            }
            return stringBuilder.toString();
        } catch (Throwable th5) {
            th = th5;
            bufferedReader = null;
            if (inputStream != null) {
            }
            if (bufferedReader != null) {
            }
            try {
                throw th;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
