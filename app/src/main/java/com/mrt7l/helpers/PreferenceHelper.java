package com.mrt7l.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;


public class PreferenceHelper {

	private SharedPreferences app_prefs;
	private Context context;
	private final String USERID = "user_id";
	private final String PHONE = "user_phone";
	private final String TOKEN = "wait_time";
	private final String GROUPID = "user_group_id";
	private final String Unconfirmed = "point_stop";
	private final String VERIFYCODE = "random_number";
	private final String EMAIL = "email";
	private final String ISACTIVE = "isActive";
	private final String SCHOOLID = "schoolId";
	private final String USERNAME = "userName";
	private final String ISVERIFIED = "isVerified";
	private final String ISRESENDCODE = "isResendCode";
	private final String ISLOGIN = "isLogin";
	private final String PageNumber = "page";
	private final String ReloadFavourite = "ReloadFavourite";
	private final String LastDestinationsViewd = "LastDestinationsViewd";
	private final String PHOTO = "PHOTO";
	private final String Confirmed = "Confirmed";
	private final String ReloadMyTrips = "ReloadMyTrips";
	private final String ReloadProfile = "ReloadProfile";
	private final String ReloadNotification = "ReloadNotification";
	private final String ReloadMain = "ReloadMain";
	private final String DeviceToken = "deviceToken";
	private final String ISDeviceTokenUpdated = "ISDeviceTokenUpdated";
	private final String orderID = "orderID";
	private final String SchoolStepId = "SchoolStepId";
	private final String version = "versionNumber";
	private final String Onboard = "Onboard";
	public PreferenceHelper(Context context) {
		String PREF_NAME = "mrt7l_pref";
		app_prefs = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		this.context = context;
	}


	public void setTOKEN(String token) {
		Editor edit = app_prefs.edit();
		edit.putString(TOKEN, token);
		edit.apply();
	}

	public String getTOKEN() {
		return app_prefs.getString(TOKEN, null);
	}



	public void setVersion(String token) {
		Editor edit = app_prefs.edit();
		edit.putString(version, token);
		edit.apply();
	}

	public String getVersion() {
		return app_prefs.getString(version, null);
	}



	public void setDeviceToken(String token) {
		Editor edit = app_prefs.edit();
		edit.putString(DeviceToken, token);
		edit.apply();

	}

	public String getDeviceToken() {
		return app_prefs.getString(DeviceToken, null);
	}



	public void setPHOTO(String token) {
		Editor edit = app_prefs.edit();
		edit.putString(PHOTO, token);
		edit.apply();

	}

	public String getPHOTO() {
		return app_prefs.getString(PHOTO, null);
	}
	public void setConfirmed(int token) {
		Editor edit = app_prefs.edit();
		edit.putInt(Confirmed, token);
		edit.apply();

	}

	public int getConfirmed() {
		return app_prefs.getInt(Confirmed, 0);
	}





	public Set<String> getLastDestinationsViewd() {
		return app_prefs.getStringSet(LastDestinationsViewd, null);
	}

	public void setLastDestinationsViewd(Set<String> gid) {
		Editor edit = app_prefs.edit();
		edit.putStringSet(LastDestinationsViewd, gid);
		edit.apply();

	}

	public Set<String> getSchoolStepId() {
		return app_prefs.getStringSet(SchoolStepId, null);
	}

	public void setSchoolStepId(Set<String> gid) {
		Editor edit = app_prefs.edit();
		edit.putStringSet(SchoolStepId, gid);
		edit.apply();

	}
	public void setGROUPID(String gid) {
		Editor edit = app_prefs.edit();
		edit.putString(GROUPID, gid);
		edit.apply();
	}

	public String getGROUPID() {
		return app_prefs.getString(GROUPID, null);
	}
	public void setPageNumber(int gid) {
		Editor edit = app_prefs.edit();
		edit.putInt(PageNumber, gid);
		edit.apply();

	}

	public int getPageNumber() {
		return app_prefs.getInt(PageNumber, 1);
	}



	public void setUSERNAME(String gid) {
		Editor edit = app_prefs.edit();
		edit.putString(USERNAME, gid);
		edit.apply();

	}

	public String getUSERNAME() {
		return app_prefs.getString(USERNAME, null);
	}

	public void setISDeviceTokenUpdated(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ISDeviceTokenUpdated, gid);
		edit.apply();
	}

	public boolean isDeviceTokenUpdated() {
		return app_prefs.getBoolean(ISDeviceTokenUpdated, false);
	}
	public void setOrderID(int gid) {
		Editor edit = app_prefs.edit();
		edit.putInt(orderID, gid);
		edit.apply();
	}

	public int getOrderId() {
		return app_prefs.getInt(orderID, 0);
	}

	public void setISVERIFIED(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ISVERIFIED, gid);
		edit.apply();

	}

	public boolean isVerified() {
		return app_prefs.getBoolean(ISVERIFIED, false);
	}
	public void setReloadFavourite(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ReloadFavourite, gid);
		edit.apply();

	}

	public boolean isReloadFavourite() {
		return app_prefs.getBoolean(ReloadFavourite, false);
	}


	public void setReloadMyTrips(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ReloadMyTrips, gid);
		edit.apply();

	}

	public boolean isReloadMyTrips() {
		return app_prefs.getBoolean(ReloadMyTrips, false);
	}

	public void setReloadProfile(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ReloadProfile, gid);
		edit.apply();

	}

	public boolean isReloadProfile() {
		return app_prefs.getBoolean(ReloadProfile, false);
	}

	public void setReloadNotification(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ReloadNotification, gid);
		edit.apply();

	}

	public boolean isReloadNotification() {
		return app_prefs.getBoolean(ReloadNotification, true);
	}
	public void setReloadMain(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ReloadMain, gid);
		edit.apply();

	}

	public boolean isReloadMain() {
		return app_prefs.getBoolean(ReloadMain, true);
	}




	public void setISLOGIN(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ISLOGIN, gid);
		edit.apply();

	}

	public boolean isLogin() {
		return app_prefs.getBoolean(ISLOGIN, false);
	}


	public void setIsChangePhone(boolean gid) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(ISRESENDCODE, gid);
		edit.apply();

	}

	public boolean isChangePhone() {
		return app_prefs.getBoolean(ISRESENDCODE, false);
	}


	public void setUnconfirmed(int gid) {
		Editor edit = app_prefs.edit();
		edit.putInt(Unconfirmed, gid);
		edit.apply();

	}

	public int getUnConfirmed() {
		return app_prefs.getInt(Unconfirmed, 0);
	}
	public void setEMAIL(String gid) {
		Editor edit = app_prefs.edit();
		edit.putString(EMAIL, gid);
		edit.apply();

	}

	public String getEMAIL() {
		return app_prefs.getString(EMAIL, null);
	}
	public void setVERIFYCODE(int gid) {
		Editor edit = app_prefs.edit();
		edit.putInt(VERIFYCODE, gid);
		edit.apply();

	}

	public int getVERIFYCODE() {
		return app_prefs.getInt(VERIFYCODE, 0);
	}
	public void setOnboardLoaded(boolean ddd) {
		Editor edit = app_prefs.edit();
		edit.putBoolean(Onboard, ddd);
		edit.apply();
	}

	public boolean getOnboardloaded() {
		return app_prefs.getBoolean(Onboard, false);
	}

	public void setUSERID(int ddd) {
		Editor edit = app_prefs.edit();
		edit.putInt(USERID, ddd);
		edit.apply();

	}

	public int getUSERID() {
		return app_prefs.getInt(USERID, 0);
	}
	public void setPHONE(String ddd) {
		Editor edit = app_prefs.edit();
		edit.putString(PHONE, ddd);
		edit.apply();
	}

	public String getPhone() {
		return app_prefs.getString(PHONE, null);
	}

	public void setISACTIVE(int ddd) {
		Editor edit = app_prefs.edit();
		edit.putInt(ISACTIVE, ddd);
		edit.apply();

	}

	public int getISACTIVE() {
		return app_prefs.getInt(ISACTIVE, 0);
	}
	public void setSCHOOLID(int ddd) {
		Editor edit = app_prefs.edit();
		edit.putInt(SCHOOLID, ddd);
		edit.apply();

	}

	public int getSCHOOLID() {
		return app_prefs.getInt(SCHOOLID, 0);
	}


	public void Logout() {
		setEMAIL(null);
		setUSERID(0);
		setUSERNAME(null);
		setTOKEN(null);
		setGROUPID(null);
		setPHONE(null);
		setVERIFYCODE(0);
		setConfirmed(0);
 		setUnconfirmed(0);
		setPHOTO(null);
		setISACTIVE(0);
		setISVERIFIED(false);
		setSCHOOLID(0);
		setISLOGIN(false);
 		clearReload();
	}



	public void clearReload(){
		setReloadFavourite(true);
		setReloadNotification(true);
		setReloadProfile(true);
		setReloadMyTrips(true);
		setReloadMain(true);
	}

}
