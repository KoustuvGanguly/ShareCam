package com.tweekerz.sharecam;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;


public class CameraActivity extends AppCompatActivity implements Callback, PictureCallback {
    public static final String CAMERA_FLASH_KEY = "flash_mode";
    public static final String CAMERA_ID_KEY = "camera_id";
    public static final String IMAGE_INFO = "image_info";
    public static final String TAG;
    private static final int PICTURE_SIZE_MAX_WIDTH = 1280;
    private static final int PREVIEW_SIZE_MAX_WIDTH = 640;
    private static final int SELECT_PHOTO = 5;

    static {
        TAG = CameraActivity.class.getSimpleName();
    }

    private ImageButton backBtn;
    private LinearLayout bottomLyt;
    private ImageView changeCameraFlashModeBtn;
    private Button libraryBtn;
    private Camera mCamera;
    private int mCameraID;
    private Context mContext;
    private String mFlashMode;
    private ImageParameters mImageParameters;
    private boolean mIsSafeToTakePhoto;
    private CameraOrientationListener mOrientationListener;
    private SquareCameraPreview mPreviewView;
    private SurfaceHolder mSurfaceHolder;

    public CameraActivity() {
        this.mIsSafeToTakePhoto = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.squarecamera__fragment_camera);
            initView(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(Bundle savedInstanceState) {
        this.mContext = this;
        this.mOrientationListener = new CameraOrientationListener(this.mContext);
        if (savedInstanceState == null) {
            this.mCameraID = getBackCameraID();
            this.mFlashMode = CameraSettingPreferences.getCameraFlashMode(this.mContext);
            this.mImageParameters = new ImageParameters();
        } else {
            this.mCameraID = savedInstanceState.getInt(CAMERA_ID_KEY);
            this.mFlashMode = savedInstanceState.getString(CAMERA_FLASH_KEY);
            this.mImageParameters = (ImageParameters) savedInstanceState.getParcelable(IMAGE_INFO);
        }
        this.mOrientationListener.enable();
        this.mPreviewView = (SquareCameraPreview) findViewById(R.id.camera_preview_view);
        this.mPreviewView.getHolder().addCallback(this);
        LinearLayout topCoverView = (LinearLayout) findViewById(R.id.cover_top_view);
        LinearLayout btnCoverView = (LinearLayout) findViewById(R.id.cover_bottom_view);
        ImageView swapCameraBtn = (ImageView) findViewById(R.id.change_camera);
        this.changeCameraFlashModeBtn = (ImageView) findViewById(R.id.flash);
        ImageButton takePhotoBtn = (ImageButton) findViewById(R.id.capture_image_button);
        this.backBtn = (ImageButton) topCoverView.findViewById(R.id.camCrossBtn);
        this.bottomLyt = (LinearLayout) findViewById(R.id.bottomLyt);
        this.mImageParameters.mIsPortrait = getResources().getConfiguration().orientation == 1;
        if (savedInstanceState == null) {
            this.mPreviewView.getViewTreeObserver().addOnGlobalLayoutListener(new C04641(topCoverView, btnCoverView));
        } else if (this.mImageParameters.isPortrait()) {
            topCoverView.getLayoutParams().height = this.mImageParameters.mCoverHeight;
            btnCoverView.getLayoutParams().height = this.mImageParameters.mCoverHeight;
        } else {
            topCoverView.getLayoutParams().width = this.mImageParameters.mCoverWidth;
            btnCoverView.getLayoutParams().width = this.mImageParameters.mCoverWidth;
        }
        swapCameraBtn.setOnClickListener(new C04652());
        this.changeCameraFlashModeBtn.setOnClickListener(new C04663());
        takePhotoBtn.setOnClickListener(new C04674());
        this.backBtn.setOnClickListener(new C04696());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == -1) {
            if (data != null) {
                try {
                    data.putExtra(Constant.FROMLIBRARY, true);
                    setResult(resultCode, data);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            finish();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        try {
            outState.putInt(CAMERA_ID_KEY, this.mCameraID);
            outState.putString(CAMERA_FLASH_KEY, this.mFlashMode);
            outState.putParcelable(IMAGE_INFO, this.mImageParameters);
            super.onSaveInstanceState(outState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resizeTopAndBtmCover(LinearLayout topCover, LinearLayout bottomCover) {
        try {
            ResizeAnimation resizeTopAnimation = new ResizeAnimation(topCover, this.mImageParameters);
            resizeTopAnimation.setDuration(500);
            resizeTopAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            topCover.startAnimation(resizeTopAnimation);
            ResizeAnimation resizeBtmAnimation = new ResizeAnimation(bottomCover, this.mImageParameters);
            resizeBtmAnimation.setDuration(500);
            resizeBtmAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            bottomCover.startAnimation(resizeBtmAnimation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCamera(int cameraID) {
        try {
            int cameraId = -1;
            int numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++) {
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == CameraInfo.CAMERA_FACING_FRONT || info.facing == CameraInfo.CAMERA_FACING_BACK) {
                    cameraId = i;
                    break;
                }
            }
            if (cameraID == -1) {
                this.mCamera = Camera.open(cameraId);
            } else {
                this.mCamera = Camera.open(cameraID);
            }

            this.mPreviewView.setCamera(this.mCamera);
            //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restartPreview() {
        try {
            if (this.mCamera != null) {
                stopCameraPreview();
            }
            getCamera(this.mCameraID);
            startCameraPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startCameraPreview() {
        try {
            determineDisplayOrientation();
            setupCamera();
            try {
                this.mCamera.setPreviewDisplay(this.mSurfaceHolder);
                this.mCamera.startPreview();
                setSafeToTakePhoto(true);
                setCameraFocusReady(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void stopCameraPreview() {
        try {
            setSafeToTakePhoto(false);
            setCameraFocusReady(false);
            this.mCamera.stopPreview();
            this.mPreviewView.setCamera(null);
            this.mCamera.release();
            this.mCamera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSafeToTakePhoto(boolean isSafeToTakePhoto) {
        this.mIsSafeToTakePhoto = isSafeToTakePhoto;
    }

    private void setCameraFocusReady(boolean isFocusReady) {
        try {
            if (this.mPreviewView != null) {
                this.mPreviewView.setIsFocusReady(isFocusReady);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void determineDisplayOrientation() {
        try {
            int displayOrientation;
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(this.mCameraID, cameraInfo);
            int degrees = 0;
            switch (getWindowManager().getDefaultDisplay().getRotation()) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }
            if (cameraInfo.facing == 1) {
                displayOrientation = (360 - ((cameraInfo.orientation + degrees) % 360)) % 360;
            } else {
                displayOrientation = ((cameraInfo.orientation - degrees) + 360) % 360;
            }
            this.mImageParameters.mDisplayOrientation = displayOrientation;
            this.mImageParameters.mLayoutOrientation = degrees;
            this.mCamera.setDisplayOrientation(this.mImageParameters.mDisplayOrientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupCamera() {
        try {
            Parameters parameters = this.mCamera.getParameters();
            Size bestPreviewSize = determineBestPreviewSize(parameters);
            Size bestPictureSize = determineBestPictureSize(parameters);
            parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
            parameters.setPictureSize(bestPictureSize.width, bestPictureSize.height);
            if (parameters.getSupportedFocusModes().contains(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
            parameters.setJpegQuality(100);
            parameters.setAntibanding(Parameters.ANTIBANDING_AUTO);
            parameters.setWhiteBalance(Parameters.WHITE_BALANCE_AUTO);
            View changeCameraFlashModeBtn = findViewById(R.id.flash);
            List<String> flashModes = parameters.getSupportedFlashModes();
            if (flashModes == null || !flashModes.contains(this.mFlashMode)) {
                changeCameraFlashModeBtn.setVisibility(View.GONE);
            } else {
                parameters.setFlashMode(this.mFlashMode);
                changeCameraFlashModeBtn.setVisibility(View.VISIBLE);
            }
            this.mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Size determineBestPreviewSize(Parameters parameters) {
        return determineBestSize(parameters.getSupportedPreviewSizes(), PREVIEW_SIZE_MAX_WIDTH);
    }

    private Size determineBestPictureSize(Parameters parameters) {
        return determineBestSize(parameters.getSupportedPictureSizes(), PICTURE_SIZE_MAX_WIDTH);
    }

    private Size determineBestSize(List<Size> sizes, int widthThreshold) {
        Size bestSize = null;
        try {
            int numOfSizes = sizes.size();
            for (int i = 0; i < numOfSizes; i++) {
                boolean isDesireRatio;
                Size size = (Size) sizes.get(i);
                if (size.width / 4 == size.height / 3) {
                    isDesireRatio = true;
                } else {
                    isDesireRatio = false;
                }
                boolean isBetterSize;
                if (bestSize == null || size.width > bestSize.width) {
                    isBetterSize = true;
                } else {
                    isBetterSize = false;
                }
                if (isDesireRatio && isBetterSize) {
                    bestSize = size;
                }
            }
            if (bestSize == null) {
                Log.d(TAG, "cannot find the best camera size");
                return (Size) sizes.get(sizes.size() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bestSize;
    }

    private int getFrontCameraID() {
        if (getPackageManager().hasSystemFeature("android.hardware.camera.front")) {
            return 1;
        }
        return getBackCameraID();
    }

    private int getBackCameraID() {
        return 0;
    }

    private void takePicture() {
        try {
            if (this.mIsSafeToTakePhoto) {
                setSafeToTakePhoto(false);
                this.mOrientationListener.rememberOrientation();
                this.mCamera.takePicture(null, null, null, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getPhotoRotation() {
        try {
            int orientation = this.mOrientationListener.getRememberedNormalOrientation();
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(this.mCameraID, info);
            if (info.facing == 1) {
                return ((info.orientation - orientation) + 360) % 360;
            }
            return (info.orientation + orientation) % 360;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void onResume() {
        try {
            super.onResume();
            if (this.mCamera == null) {
                this.mFlashMode = "auto";
                this.changeCameraFlashModeBtn.setImageResource(R.drawable.ic_flash_auto);
                restartPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        try {
            if (this.mCamera != null) {
                this.mCamera.release();
            }
            super.onDestroy();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onStop() {
        try {
            this.mOrientationListener.disable();
            if (this.mCamera != null) {
                stopCameraPreview();
            }
            CameraSettingPreferences.saveCameraFlashMode(this.mContext, this.mFlashMode);
            super.onStop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            this.mSurfaceHolder = holder;
            getCamera(this.mCameraID);
            startCameraPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void onPictureTaken(byte[] data, Camera camera) {
        try {
            int mRotation = getPhotoRotation();
            Log.d("CAM START=>", String.valueOf(System.currentTimeMillis()));
            setSafeToTakePhoto(true);
            Bundle args = new Bundle();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] bytes = data;
           /* String BITMAP_KEY = "bitmap_byte_array";
            String ROTATION_KEY = "rotation";
            String IMAGE_INFO = this.IMAGE_INFO;*/
            args.putByteArray("bitmap_byte_array", bytes);
            args.putInt("rotation", mRotation);
            //args.putParcelable(IMAGE_INFO, this.mImageParameters.createCopy());
            //Intent resultIntent = new Intent();
            Common.setmStoringBundle(args);
           /* resultIntent.setData(Uri.fromFile(new File("a")));
            resultIntent.putExtra(Constant.FROMLIBRARY, false);
            resultIntent.putExtra("H", this.mPreviewView.getHeight());
            resultIntent.putExtra("W", this.mPreviewView.getWidth());*/
            stopCameraPreview();
            setResult(-1);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        try {
            stopCameraPreview();
            CameraActivity.this.setSafeToTakePhoto(true);
            finish();
            Common.getmLandingActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class CameraOrientationListener extends OrientationEventListener {
        private int mCurrentNormalizedOrientation;
        private int mRememberedNormalOrientation;

        public CameraOrientationListener(Context context) {
            super(context, 3);
        }

        public void onOrientationChanged(int orientation) {
            if (orientation != -1) {
                try {
                    this.mCurrentNormalizedOrientation = normalize(orientation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private int normalize(int degrees) {
            if (degrees > 315 || degrees <= 45) {
                return 0;
            }
            if (degrees > 45 && degrees <= 135) {
                return 90;
            }
            if (degrees > 135 && degrees <= 225) {
                return 180;
            }
            if (degrees > 225 && degrees <= 315) {
                return 270;
            }
            throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
        }

        public void rememberOrientation() {
            try {
                this.mRememberedNormalOrientation = this.mCurrentNormalizedOrientation;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int getRememberedNormalOrientation() {
            try {
                rememberOrientation();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.mRememberedNormalOrientation;
        }
    }

    /* renamed from: com.antelope.custom.CameraActivity.1 */
    class C04641 implements OnGlobalLayoutListener {
        final /* synthetic */ LinearLayout val$btnCoverView;
        final /* synthetic */ LinearLayout val$topCoverView;

        C04641(LinearLayout linearLayout, LinearLayout linearLayout2) {
            this.val$topCoverView = linearLayout;
            this.val$btnCoverView = linearLayout2;
        }

        public void onGlobalLayout() {
            try {
                CameraActivity.this.mImageParameters.mPreviewWidth = CameraActivity.this.mPreviewView.getWidth();
                CameraActivity.this.mImageParameters.mPreviewHeight = CameraActivity.this.mPreviewView.getHeight();
                ImageParameters access$000 = CameraActivity.this.mImageParameters;
                ImageParameters access$0002 = CameraActivity.this.mImageParameters;
                int calculateCoverWidthHeight = CameraActivity.this.mImageParameters.calculateCoverWidthHeight();
                access$0002.mCoverHeight = calculateCoverWidthHeight;
                access$000.mCoverWidth = calculateCoverWidthHeight;
                CameraActivity.this.resizeTopAndBtmCover(this.val$topCoverView, this.val$btnCoverView);
                if (VERSION.SDK_INT >= 16) {
                    CameraActivity.this.mPreviewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    CameraActivity.this.mPreviewView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.antelope.custom.CameraActivity.2 */
    class C04652 implements OnClickListener {
        C04652() {
        }

        public void onClick(View v) {
            try {
                if (CameraActivity.this.mCameraID == 1) {
                    CameraActivity.this.mCameraID = CameraActivity.this.getBackCameraID();
                } else {
                    CameraActivity.this.mCameraID = CameraActivity.this.getFrontCameraID();
                }
                restartPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.antelope.custom.CameraActivity.3 */
    class C04663 implements OnClickListener {
        C04663() {
        }

        public void onClick(View v) {
            try {
                if (CameraActivity.this.mFlashMode.equalsIgnoreCase("auto")) {
                    CameraActivity.this.mFlashMode = "on";
                    CameraActivity.this.changeCameraFlashModeBtn.setImageResource(R.drawable.icon_cam_flash);
                } else if (CameraActivity.this.mFlashMode.equalsIgnoreCase("on")) {
                    CameraActivity.this.mFlashMode = "off";
                    CameraActivity.this.changeCameraFlashModeBtn.setImageResource(R.drawable.ic_flash_off);
                } else if (CameraActivity.this.mFlashMode.equalsIgnoreCase("off")) {
                    CameraActivity.this.mFlashMode = "auto";
                    CameraActivity.this.changeCameraFlashModeBtn.setImageResource(R.drawable.ic_flash_auto);
                }
                CameraActivity.this.setupCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.antelope.custom.CameraActivity.4 */
    class C04674 implements OnClickListener {
        C04674() {
        }

        public void onClick(View v) {
            try {
                CameraActivity.this.takePicture();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.antelope.custom.CameraActivity.5 */
    class C04685 implements OnClickListener {
        C04685() {
        }

        public void onClick(View v) {
            try {
                CameraActivity.this.stopCameraPreview();
                CameraActivity.this.setSafeToTakePhoto(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.antelope.custom.CameraActivity.6 */
    class C04696 implements OnClickListener {
        C04696() {
        }

        public void onClick(View v) {
            try {
                onBackPressed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
