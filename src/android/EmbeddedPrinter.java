package cn.x1ongzhu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import com.qs.wiget.PrintUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class EmbeddedPrinter extends CordovaPlugin {


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        PrintUtils.initPrintUtils(cordova.getActivity());
        // 开启黑标指令
        byte[] bt2 = new byte[]{0x1F, 0x1B, 0x1F, (byte) 0x80, 0x04,
                0x05, 0x06, 0x44};
        //发送命令
        PrintUtils.send(bt2);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("printPic")) {
            String encodedImage = args.getString(0);
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Bitmap zbm = zoomImg(bm, 240, 200);
            PrintUtils.printBitmap(1, zbm);
            bm.recycle();
            zbm.recycle();
        }
        return false;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        return newbm;
    }

}
