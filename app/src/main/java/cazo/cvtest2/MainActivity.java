package cazo.cvtest2;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.core.Mat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button capture_button;
    private ImageView output_picture;

    private int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    public final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CVtesterout/";


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        File newdir = new File(dir);
        newdir.mkdirs();

        capture_button = (Button) findViewById(R.id.Capture_Button);
        capture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                takePicture();


                /*
                output_picture = (ImageView) findViewById(R.id.Image_Taken);

                int targetW = output_picture.getWidth();
                int targetH = output_picture.getHeight();

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(file, bmOptions);
                output_picture.setImageBitmap(bitmap);
                output_picture.setVisibility(View.VISIBLE);
                */
            }

        });


    }


    public void takePicture(){
        count++;
        String file = dir+count+".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        }
        catch (IOException e) {
        }

        Uri outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
