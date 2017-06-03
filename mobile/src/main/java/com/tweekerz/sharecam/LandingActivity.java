package com.tweekerz.sharecam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Koustuv Ganguly on 04-Sep-16.
 */
public class LandingActivity extends BaseActivity {

    private static final int START_IMAGE_CAPTURE = 5;
    Bitmap seletedImage, bitmap;
    private ImageView mShareImageView;
    private int mSelectPhoto = 5;
    private Uri selectedImage;
    private ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_image_preview);
            mShareImageView = (ImageView) findViewById(R.id.selectedImage);
            mLoadingBar = (ProgressBar) findViewById(R.id.loadingPbar);

            findViewById(R.id.btnUpload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (selectedImage != null) {
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/jpeg");
                            share.putExtra(Intent.EXTRA_STREAM, selectedImage);
                            startActivity(Intent.createChooser(share, "Share Image"));
                            mLoadingBar.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(LandingActivity.this,"Please try again!",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            findViewById(R.id.uploadImgBack).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            pickPhoto();
            Common.setmLandingActivity(LandingActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            pickPhoto();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pickPhoto() {
        try {
            super.pickPhoto();
            selectedImage = null;
            bitmap = null;
            mShareImageView.setImageBitmap(null);
            startActivityForResult(new Intent(this, CameraActivity.class), this.mSelectPhoto);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        try {
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
            switch (requestCode) {
                case START_IMAGE_CAPTURE /*5*/:
                    try {

                        try {
                            Bundle args = new Bundle();
                            args = Common.getmStoringBundle();
                            if (args != null) {
                                int cropHeight;

                                String mBitmapKey = "bitmap_byte_array", mRotationKey = "rotation";
                                int rotation = args.getInt(mRotationKey);
                                byte[] data1 = args.getByteArray(mBitmapKey);
                                bitmap = ImageUtility.decodeSampledBitmapFromByte(LandingActivity.this, data1);
                                if (rotation != 0) {
                                    Bitmap oldBitmap = bitmap;
                                    Matrix matrix = new Matrix();
                                    matrix.postRotate((float) rotation);
                                    bitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(), matrix, false);
                                }
                                if (bitmap.getHeight() > bitmap.getWidth()) {
                                    cropHeight = bitmap.getWidth();
                                } else {
                                    cropHeight = bitmap.getHeight();
                                }
                                bitmap = ThumbnailUtils.extractThumbnail(bitmap, cropHeight, cropHeight);
                                mShareImageView.setImageBitmap(bitmap);
                            }


                            Log.d("Line=>", "148");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected void onPreExecute() {
                                try {
                                    super.onPreExecute();
                                    mLoadingBar.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            protected Void doInBackground(Void... voids) {
                                try {
                                    boolean success = true;
                                    File folder = null;
                                    File file;
                                    folder = new File(getExternalCacheDir() + "/mule/tempcamimages");
                                    if (!folder.exists()) {
                                        success = folder.mkdirs();
                                    }
                                    Bitmap rotatedBitmap = bitmap;
                                    if (success) {
                                        file = new File((folder.getAbsolutePath() + File.separator).toString() + "ShareImage.jpeg");
                                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                                        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                                        fileOutputStream.flush();
                                        fileOutputStream.close();
                                        file.setReadable(true,false);
                                        selectedImage = Uri.fromFile(file);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                try {
                                    Log.d("Line=>", "149");
                                    mLoadingBar.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.execute();
                        break;
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
