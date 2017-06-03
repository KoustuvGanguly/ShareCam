package com.tweekerz.sharecam;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Koustuv Ganguly on 04-Sep-16.
 */
public class AppController extends Application {
    public static final String TAG;
    private static AppController mInstance;
    private Bundle bundle;
    private ProgressDialog commanDialog;
    private String fromActivity;
    private boolean isBuyer;
    private boolean isChatActivityApplied;
    private boolean isChatDefaultLoaded;
    private boolean isChatSortingApplied;
    private boolean isChatTabSelected;
    private boolean isClearedFilter;
    private boolean isDefaultLoaded;
    private boolean isDisplayedOnboard;
    private boolean isFeedback;
    private boolean isFilterAppled;
    private boolean isLocRcvrCalled;
    private boolean isLocationOff;
    private boolean isPostDefaultLoaded;
    private boolean isPostDeleted;
    private boolean isPostEdited;
    private boolean isPriceEdited;
    private boolean isRegisterBroadcastRcvd;
    private boolean isSearchApplied;
    private boolean isSortingApplied;
    private Context mContext;
    /*ArrayList<ItemChatDTO> listOfItemChats;
    ArrayList<ItemsDTO> listOfItems;
    ArrayList<PostItemDTO> listOfPostItems;
    ArrayList<ArrayMap<ChatUsersDTO, ArrayList<ChatUsersDTO>>> listOfUserChats;

    private ArrayList<DSModeDTO> mDsModeDtos;
    private ImageLoader mImageLoader;
    private NewPost mNewPost;
    private RequestQueue mRequestQueue;
    private UserAuthDTO mUserAuthDTO;
    private ArrayList<String> paymentEmailList;
    private ArrayList<PaymentMethodsDTO> paymentMethods;
    private int prevSelectedTabNo;
    private HashSet<Integer> selectedCategories;
    private int selectedTabNo;
    private int sorting;
    ArrayList<ItemsDTO> tempListOfItems;
    private UserDTO userDetails;
    private String userSearchKeyword;*/

    /* renamed from: com.antelope.commons.AppController.1 */
  /*  class C04561 implements UncaughtExceptionHandler {
        C04561() {
        }

        public void uncaughtException(Thread thread, Throwable e) {
            AppController.this.getSharedPreferences(Constant.PREFERENCE_REGISTER, 0).edit().putBoolean(Constant.PREFERENCE_USER_LOGIN_STATUS, false).commit();
            AppController.this.getSharedPreferences(Constant.PREFERENCE_REGISTER, 0).edit().putBoolean(Constant.PREFERENCE_USER_LOGGEDIN_STATUS, true).commit();
            AppController.this.handleUncaughtException(thread, e);
        }
    }*/

    public AppController() {
        //this.prevSelectedTabNo = -1;
        this.isRegisterBroadcastRcvd = false;
        this.fromActivity = Constant.FROM_ACTIVITY_HOME;
        this.isLocRcvrCalled = false;
        this.isLocationOff = false;
        this.isFeedback = false;
        this.isPostEdited = false;
        this.isPostDeleted = false;
        this.isPriceEdited = false;
        this.isChatTabSelected = false;
    }

    static {
        TAG = AppController.class.getSimpleName();
    }

    public static synchronized AppController getInstance() {
        AppController appController;
        synchronized (AppController.class) {
            appController = mInstance;
        }
        return appController;
    }

    /*public NewPost getmNewPost() {
        return this.mNewPost;
    }

    public void setmNewPost(NewPost mNewPost) {
        this.mNewPost = mNewPost;
    }*/

    public boolean isPostDeleted() {
        return this.isPostDeleted;
    }

    public void setIsPostDeleted(boolean isPostDeleted) {
        this.isPostDeleted = isPostDeleted;
    }

    public boolean isPriceEdited() {
        return this.isPriceEdited;
    }

    public void setIsPriceEdited(boolean isPriceEdited) {
        this.isPriceEdited = isPriceEdited;
    }

    /*public ArrayList<String> getPaymentEmailList() {
        return this.paymentEmailList;
    }

    public void setPaymentEmailList(ArrayList<String> paymentEmailList) {
        this.paymentEmailList = paymentEmailList;
    }*/

    public boolean isChatTabSelected() {
        return this.isChatTabSelected;
    }

    public void setChatTabSelected(boolean chatTabSelected) {
        this.isChatTabSelected = chatTabSelected;
    }

    /*public ArrayList<PaymentMethodsDTO> getPaymentMethods() {
        return this.paymentMethods;
    }

    public void setPaymentMethods(ArrayList<PaymentMethodsDTO> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public ArrayList<DSModeDTO> getmDsModeDtos() {
        return this.mDsModeDtos;
    }

    public void setmDsModeDtos(ArrayList<DSModeDTO> mDsModeDtos) {
        this.mDsModeDtos = mDsModeDtos;
    }*/

   /* public boolean isLocationOff() {
        try {
            this.isLocationOff = this.mContext.getSharedPreferences(Constant.PREFERENCE_REGISTER, 0).getBoolean(Constant.LOCATION_OFF, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.isLocationOff;
    }

    public void setIsLocationOff(boolean isLocationOff) {
        try {
            this.isLocationOff = isLocationOff;
            this.mContext.getSharedPreferences(Constant.PREFERENCE_REGISTER, 0).edit().putBoolean(Constant.LOCATION_OFF, this.isLocationOff).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public boolean isLocRcvrCalled() {
        return this.isLocRcvrCalled;
    }

    public void setLocRcvrCalled(boolean locRcvrCalled) {
        this.isLocRcvrCalled = locRcvrCalled;
    }

    public String getFromActivity() {
        return this.fromActivity;
    }

    public void setFromActivity(String fromActivity) {
        this.fromActivity = fromActivity;
    }

    public boolean isRegisterBroadcastRcvd() {
        return this.isRegisterBroadcastRcvd;
    }

    public void setIsRegisterBroadcastRcvd(boolean isRegisterBroadcastRcvd) {
        this.isRegisterBroadcastRcvd = isRegisterBroadcastRcvd;
    }

    public ProgressDialog getCommanDialog() {
        return this.commanDialog;
    }

    public void setCommanDialog(ProgressDialog commanDialog) {
        if (commanDialog == null) {
            this.commanDialog = commanDialog;
        } else {
            this.commanDialog = commanDialog;
        }
    }

    /*public int getPrevSelectedTabNo() {
        return this.prevSelectedTabNo;
    }

    public void setPrevSelectedTabNo(int prevSelectedTabNo) {
        this.prevSelectedTabNo = prevSelectedTabNo;
    }

    public int getSelectedTabNo() {
        return this.selectedTabNo;
    }

    public void setSelectedTabNo(int selectedTabNo) {
        this.selectedTabNo = selectedTabNo;
    }*/

    public boolean isBuyer() {
        return this.isBuyer;
    }

    public void setIsBuyer(boolean isBuyer) {
        this.isBuyer = isBuyer;
    }

   /* public ArrayList<ArrayMap<ChatUsersDTO, ArrayList<ChatUsersDTO>>> getListOfUserChats() {
        return this.listOfUserChats;
    }

    public void setListOfUserChats(ArrayList<ArrayMap<ChatUsersDTO, ArrayList<ChatUsersDTO>>> listOfUserChats) {
        this.listOfUserChats = listOfUserChats;
    }

    public ArrayList<ItemChatDTO> getListOfItemChats() {
        return this.listOfItemChats;
    }

    public void setListOfItemChats(ArrayList<ItemChatDTO> listOfItemChats) {
        this.listOfItemChats = listOfItemChats;
    }*/

    public boolean isChatActivityApplied() {
        return this.isChatActivityApplied;
    }

    public void setIsChatActivityApplied(boolean isChatActivityApplied) {
        this.isChatActivityApplied = isChatActivityApplied;
    }

    public boolean isChatSortingApplied() {
        return this.isChatSortingApplied;
    }

    public void setIsChatSortingApplied(boolean isChatSortingApplied) {
        this.isChatSortingApplied = isChatSortingApplied;
    }

    public boolean isChatDefaultLoaded() {
        return this.isChatDefaultLoaded;
    }

    public void setIsChatDefaultLoaded(boolean isChatDefaultLoaded) {
        this.isChatDefaultLoaded = isChatDefaultLoaded;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public boolean isSortingApplied() {
        return this.isSortingApplied;
    }

    public void setIsSortingApplied(boolean isSortingApplied) {
        this.isSortingApplied = isSortingApplied;
    }

    /*public int getSorting() {
        return this.sorting;
    }

    public void setSortingCriteria(int sorting) {
        this.sorting = sorting;
    }
*/
    public boolean isFilterAppled() {
        return this.isFilterAppled;
    }

    public void setIsFilterApply(boolean isFilterApply) {
        if (isSortingApplied() && isFilterApply) {
            setIsSortingApplied(false);
        }
        this.isFilterAppled = isFilterApply;
    }

    public Context getmContext() {
        return this.mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this;
        /*TestFairy.begin(this, Constant.TEST_FAIRY_APP_TOKEN);
        Account[] accounts = AccountManager.get(this.mContext).getAccounts();
        Map<String, Object> traits = new HashMap();
        for (Account account : accounts) {
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                traits.put(TestFairy.IDENTITY_TRAIT_EMAIL_ADDRESS, account.name);
                break;
            }
        }
        TestFairy.identify(TestFairy.IDENTITY_TRAIT_EMAIL_ADDRESS, traits);
        Thread.setDefaultUncaughtExceptionHandler(new ());*/
    }

    private void handleUncaughtException(Thread thread, Throwable e) {
        System.exit(1);
    }

   /* public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(this.mContext);
        }
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (this.mImageLoader == null) {
            this.mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }

    public HashSet<Integer> getSelectedCategories() {
        return this.selectedCategories;
    }

    public void setSelectedCategories(HashSet<Integer> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public ArrayList<ItemsDTO> getListOfItems() {
        return this.listOfItems;
    }

    public void setListOfItems(ArrayList<ItemsDTO> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public ArrayList<PostItemDTO> getListOfPostItems() {
        return this.listOfPostItems;
    }

    public void setListOfPostItems(ArrayList<PostItemDTO> listOfPostItems) {
        this.listOfPostItems = listOfPostItems;
    }*/

    public boolean isDisplayedOnboard() {
        return this.isDisplayedOnboard;
    }

    public void setIsDisplayedOnboard(boolean isDisplayedOnboard) {
        this.isDisplayedOnboard = isDisplayedOnboard;
    }

  /*  public UserDTO getUserDetails() {
        if (this.userDetails != null) {
            return this.userDetails;
        }
        UserDTO userDTO = new UserDTO();
        this.userDetails = userDTO;
        return userDTO;
    }

    public void setUserDetails(UserDTO userDetails) {
        this.userDetails = userDetails;
    }*/

    /*public UserAuthDTO getmUserAuthDTO() {
        if (this.mUserAuthDTO == null) {
            this.mUserAuthDTO = new UserAuthDTO(this.mContext);
        }
        return this.mUserAuthDTO;
    }

    public void setmUserAuthDTO(UserAuthDTO mUserAuthDTO) {
        try {
            this.mUserAuthDTO = mUserAuthDTO;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }*/

    public boolean isDefaultLoaded() {
        return this.isDefaultLoaded;
    }

    public void setIsDefaultLoaded(boolean isDefaultLoaded) {
        this.isDefaultLoaded = isDefaultLoaded;
    }

    public boolean isPostDefaultLoaded() {
        return this.isPostDefaultLoaded;
    }

    public void setIsPostDefaultLoaded(boolean isPostDefaultLoaded) {
        this.isPostDefaultLoaded = isPostDefaultLoaded;
    }

    public boolean isSearchApplied() {
        return this.isSearchApplied;
    }

    public void setIsSearchApplied(boolean isSearchApplied) {
        this.isSearchApplied = isSearchApplied;
    }

   /* public String getUserSearchKeyword() {
        return this.userSearchKeyword;
    }

    public void setUserSearchKeyword(String userSearchKeyword) {
        this.userSearchKeyword = userSearchKeyword;
    }*/

    public boolean isClearedFilter() {
        return this.isClearedFilter;
    }

    public void setIsClearedFilter(boolean isClearedFilter) {
        this.isClearedFilter = isClearedFilter;
    }

    public boolean isPostEdited() {
        return this.isPostEdited;
    }

    public void setIsPostEdited(boolean isPostEdited) {
        this.isPostEdited = isPostEdited;
    }

    public boolean isFeedback() {
        return this.isFeedback;
    }

    public void setIsFeedback(boolean isFeedback) {
        this.isFeedback = isFeedback;
    }

   /* public ArrayList<ItemsDTO> getTempListOfItems() {
        return this.tempListOfItems;
    }

    public void setTempListOfItems(ArrayList<ItemsDTO> tempListOfItems) {
        this.tempListOfItems = tempListOfItems;
    }*/

   /* public void resetData() {
        setIsChatActivityApplied(false);
        setIsSortingApplied(false);
        setIsFilterApply(false);
        setIsSearchApplied(false);
        setIsClearedFilter(false);
        setIsChatDefaultLoaded(false);
        setIsChatSortingApplied(false);
        setUserSearchKeyword(C0981a.f2097f);
        setSortingCriteria(-1);
        setSelectedCategories(new HashSet());
        setListOfItems(new ArrayList());
        setListOfPostItems(new ArrayList());
        setListOfItemChats(new ArrayList());
        setListOfUserChats(new ArrayList());
        UserDTO userDTO = getUserDetails();
        setIsPostEdited(false);
        userDTO.setMinDist(AppEventsConstants.EVENT_PARAM_VALUE_NO);
        userDTO.setMaxDist(AppEventsConstants.EVENT_PARAM_VALUE_NO);
        userDTO.setStPnt(AppEventsConstants.EVENT_PARAM_VALUE_NO);
        userDTO.setLimit(Constant.MAX_ITEM_LIMIT);
        setSelectedTabNo(0);
        setCommanDialog(null);
        setIsFeedback(false);
        setTempListOfItems(null);
        setIsPriceEdited(false);
    }*/

   /* public void initQBChat() {
        QBSettings.getInstance().init(this.mContext, Constant.APP_ID, Constant.AUTH_KEY, Constant.AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(Constant.ACCOUNT_KEY);
    }*/
}

