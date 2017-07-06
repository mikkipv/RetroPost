package com.example.codemac.retropost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

import static android.R.attr.description;
import static android.R.attr.theme;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText latitude;
    EditText longitude;
    EditText address;
    EditText location;
    Button image;
    Button submit;
    private ArrayList<login> loginArrayList;

    private static final int REQUEST_CODE_ADD_IMAGE_GALLERY = 1000;
    private static final int REQUEST_CODE_ADD_IMAGE_CAMERA = 1001;
    private static final int MAXIMUM_UPLOAD_IMAGE_WIDTH = 1024;
    private static final int MAXIMUM_UPLOAD_IMAGE_HEIGHT = 1024;
    private Uri mainImageUri;
    private Uri tempCameraImageUri;
    private File resizedMainImage;
    private ImageView mainImageView;
    private TextView mainImageDeleteView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.edName);
        latitude = (EditText) findViewById(R.id.edLat);
        longitude = (EditText) findViewById(R.id.edLong);
        address= (EditText) findViewById(R.id.edAdd);
        location= (EditText) findViewById(R.id.edLoc);
        image = (Button) findViewById(R.id.buttImage);
        submit = (Button) findViewById(R.id.button);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (mainImageUri != null) {

                    Toast.makeText(MainActivity.this, "Please remove some images to add new.", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE_ADD_IMAGE_GALLERY);
            }
        });

        mainImageView = (ImageView) findViewById(R.id.imageView);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProductDetails();

              //  Post(name1, pass1);


            }
        });



    }

    private void submitProductDetails() {



        String name1 = name.getText().toString();
        String lat = latitude.getText().toString();
        String longi=longitude.getText().toString();
        String add=address.getText().toString();
        String loc=location.getText().toString();

        if (TextUtils.isEmpty(name1)) {


            Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(lat)) {
            Toast.makeText(this, "Please enter the latitude", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(longi)) {
            Toast.makeText(this, "Please enter the longitude", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(add)) {
            Toast.makeText(this, "Please enter the address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(loc)) {
            Toast.makeText(this, "Please enter the location", Toast.LENGTH_SHORT).show();
            return;
        }

        if(resizedMainImage == null) {
            Toast.makeText(this, "Please add a product image", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name1);
        RequestBody latBody = RequestBody.create(MediaType.parse("text/plain"), lat);
        RequestBody longiBody = RequestBody.create(MediaType.parse("text/plain"), longi);
        RequestBody addBody= RequestBody.create(MediaType.parse("text/plain"), add);
        RequestBody locBody = RequestBody.create(MediaType.parse("text/plain"), loc);

        MultipartBody.Part imagePart = null;
        if (resizedMainImage != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), resizedMainImage);
            imagePart = MultipartBody.Part.createFormData("item_image", resizedMainImage.getName(), imageBody);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading your documents..");
        progressDialog.show();

        ApiService api = RetroClient.getApiService();



        Call<ResponseBody> call = api.AddMall1(nameBody, latBody,
                longiBody,
               addBody,
                locBody,
                imagePart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                     //   List<model> responseList = new ArrayList<model>();
                        try {
                            String responseValue = response.body().string();
                            progressDialog.dismiss();

                            Toast.makeText(MainActivity.this, "Value is" + responseValue, Toast.LENGTH_SHORT).show();
                            Log.e("inside", "response" + responseValue);

                        } catch (Exception e) {
                            Log.e("exce", e.getMessage());
                        }

                    }else{
                        Log.e("inside", "failed"+response.code() );
                        progressDialog.dismiss();
                    }


                }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                }
            });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_IMAGE_GALLERY) {
            if (resultCode == RESULT_OK) {

                Log.e("image","recieved");
                Uri uri=data.getData();

                mainImageView.setImageURI(uri);
                calcuateImagePosition(uri);
            }
        }


    }

    private void calcuateImagePosition(Uri uri) {
        if (uri == null)
        {
            Log.e("uri","null");
            return;

        }
        else
        {


           resizedMainImage = prepareImage(uri);
        }
    }
    private File prepareImage(Uri uri) {
        String tempFileName = "temp_" + System.currentTimeMillis();
        File resizedImage = resizeFile(ImageReaderUtil.getPath(this, uri), tempFileName, MAXIMUM_UPLOAD_IMAGE_WIDTH, MAXIMUM_UPLOAD_IMAGE_HEIGHT);
        Log.e("file",resizedImage.getName());
        return resizedImage;
    }

    public File resizeFile(String pathName, String outputName, int maxWidth, int maxHeight) {
        File file = null;
        try {
            File storageDir = getCacheDir(); //new File(publicDir, APP_PICTURE_DIRECTORY);
            Log.e("file resized",storageDir.getName());
            if (storageDir.exists() || storageDir.mkdir()) {
                file = File.createTempFile(outputName, ".jpg", storageDir);

                FileOutputStream fos = new FileOutputStream(file);

                if (!file.exists()) file.createNewFile();

                Bitmap bitmap = ImageReaderUtil.decodeSampledBitmapFromFile(pathName, maxWidth, maxHeight);
                bitmap = ImageReaderUtil.rotate(ImageReaderUtil.getCameraPhotoOrientation(pathName), bitmap);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);

                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }



}









































/*


    public void Post(String name, String pass) {


    login loginObj=new login();
        loginObj.setName(name);
        loginObj.setPass(pass);
        Toast.makeText(this, loginObj.getName().toString()+ "   " +loginObj.getPass().toString(), Toast.LENGTH_SHORT).show();

        ApiService api = RetroClient.getApiService();


        Call<List<model>>  call = api.validate(loginObj);
        call.enqueue(new Callback <List<model>> () {
            @Override
            public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                Log.e("inside", "response");

                //   Toast.makeText(MainActivity.this, model.getId().toString(), Toast.LENGTH_SHORT).show();

                //  ArrayList<details> cars = new ArrayList<>()
                //
                // ;

                if (response.isSuccessful()) {
                    List<model> responseList = new ArrayList<model>();
                    try {
                        responseList = response.body();

                        Toast.makeText(MainActivity.this, "Value is" + responseList, Toast.LENGTH_SHORT).show();
                        Log.e("inside", "response" + responseList);

                    } catch (Exception e) {
                        Log.e("exce", e.getMessage());
                    }

                }else{
                    Log.e("inside", "failed"+response.code() );
                }
            }

            @Override
            public void onFailure(Call<List<model>>  call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
*/
