package com.mrt7l.utils.retrofit;

import com.mrt7l.model.ContactUsModel;
import com.mrt7l.model.EditProfileResponse;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.model.RegisterResponse;
import com.mrt7l.model.changePasswordResponse;
import com.mrt7l.ui.activity.ChangePasswordResponse;
import com.mrt7l.ui.activity.CheckUserResponse;
import com.mrt7l.ui.activity.ConfirmCodeResponse;
import com.mrt7l.ui.activity.ForgetPasswordResponse;
import com.mrt7l.ui.activity.ProfileResponse;
import com.mrt7l.ui.activity.SendMessageResponse;
import com.mrt7l.ui.activity.SendRegisterMessageResponse;
import com.mrt7l.ui.activity.VerifyCaptchaResponse;
import com.mrt7l.ui.fragment.about.AboutAppResponse;
import com.mrt7l.ui.fragment.about.FaqResponse;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.explain_app.VideosResponse;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.mrt7l.ui.fragment.home.AddToFavouriteResponse;
import com.mrt7l.ui.fragment.home.HomeResponse;
import com.mrt7l.ui.fragment.mytrips.CancelOrderResponse;
import com.mrt7l.ui.fragment.mytrips.CurrentOrdersResponse;
import com.mrt7l.ui.fragment.mytrips.PastOrdersResponse;
import com.mrt7l.ui.fragment.mytrips.RateTripResponse;
import com.mrt7l.ui.fragment.mytrips.RequestPdfResponse;
import com.mrt7l.ui.fragment.notifications.NotificationsResponse;
import com.mrt7l.ui.fragment.passengers.AddPassengerResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;
import com.mrt7l.ui.fragment.passengers.RemovePassengerResponse;
import com.mrt7l.ui.fragment.passengers.edit_passenger.EditPassengersResponse;
import com.mrt7l.ui.fragment.reservation.CheckPassportResponse;
import com.mrt7l.ui.fragment.reservation.CheckPayStatusModel;
import com.mrt7l.ui.fragment.reservation.RequestPayModel;
import com.mrt7l.ui.fragment.reservation.ReservationConfirmedResponse;
import com.mrt7l.ui.fragment.reservation.ReservationResponse;
import com.mrt7l.ui.fragment.reservation.VerifyPromoResponse;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;
import com.mrt7l.ui.fragment.stations.StationsResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClient {

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/sign-in.json")
    Observable<LoginResponse> Login(@Field("mobile_or_passport_id") String mobile_or_passport_id,
                                    @Field("password") String password,
                                    @Field("deviceToken") String deviceToken);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("recovery-by-mobile.json")
    Observable<ForgetPasswordResponse> forgetPassword(@Field("mobile") String mobile);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("admin/user/send-sms.json")
    Observable<SendMessageResponse> sendMessage(@Field("mobile") String mobile);


    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("admin/user/public-sms.json")
    Observable<SendRegisterMessageResponse> sendRegisterMessage(
            @Field("mobile") String mobile,
            @Field("smsCode") String code);


    //    @Query("test") String userId
    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("admin/user/change-password-sms-code")
    Observable<ChangePasswordResponse> resetPassword(@Field("password") String password,
                                                     @Field("confirm_password") String confirm_password,
                                                     @Field("smsCode") String smsCode);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("check-code")
    Observable<ConfirmCodeResponse> confirmCode(@Field("smsCode") String smsCode);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("siteverify")
    Observable<VerifyCaptchaResponse> verifyApi(@Field("secret") String secret,
                                                @Field("response") String response,
                                                @Field("remoteip") String remoteip);


    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("promoCode.json")
    Observable<VerifyPromoResponse> VerifyPromo(
            @Field("company_id") String company_id,
            @Field("promoCode") String promoCode);


    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/register.json")
    Observable<RegisterResponse> register(
            @Part("username") RequestBody username,
            @Part("gender") RequestBody gender,
            @Part("date_of_birth") RequestBody date_of_birth,
            @Part("password") RequestBody password,
            @Part("confirm_password") RequestBody confirm_password,
            @Part("city_id") RequestBody city_id,
            @Part("mobile") RequestBody mobile,
            @Part("deviceToken") RequestBody deviceToken);


    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/user/update/{id}.json")
    Observable<EditProfileResponse> editProfile(@Header("Authorization") String token,
                                                @Path("id") String id,
                                                @Part("passport_id") RequestBody passport_id,
                                                @Part MultipartBody.Part passportFile
    );

    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/user/update/{id}.json")
    Observable<EditProfileResponse> editProfile(@Header("Authorization") String token,
                                                @Path("id") String id,
                                                @Part("username") RequestBody username,
                                                @Part("nationality_id") RequestBody nationality,
                                                @Part("gender") RequestBody gender,
                                                @Part("date_of_birth") RequestBody date_of_birth,
                                                @Part("passport_id") RequestBody passport_id,
                                                @Part("password") RequestBody password,
                                                @Part("confirm_password") RequestBody confirm_password,
                                                @Part("city_id") RequestBody city_id,
                                                @Part MultipartBody.Part passportFile
    );


    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/user/update/{id}.json")
    Observable<EditProfileResponse> editProfile(@Header("Authorization") String token,
                                                @Path("id") String id,
                                                @Part("username") RequestBody username,
                                                @Part("nationality_id") RequestBody nationality,
                                                @Part("gender") RequestBody gender,
                                                @Part("date_of_birth") RequestBody date_of_birth,
                                                @Part("passport_id") RequestBody passport_id,
                                                @Part("password") RequestBody password,
                                                @Part("confirm_password") RequestBody confirm_password,
                                                @Part("city_id") RequestBody city_id);


    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/passengers/create")
    Observable<AddPassengerResponse> AddPassenger(@Header("Authorization") String token,
                                                  @Part("full_name") RequestBody full_name,
                                                  @Part("nationality_id") RequestBody nationality,
                                                  @Part("gender") RequestBody gender,
                                                  @Part("date_of_birth") RequestBody date_of_birth,
                                                  @Part("passport_id") RequestBody passport_id,
                                                  @Part("mobile") RequestBody mobile,
                                                  @Part MultipartBody.Part passportFile
    );

    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/passengers/create")
    Observable<AddPassengerResponse> AddPassenger(@Header("Authorization") String token,
                                                  @Part("full_name") RequestBody full_name,
                                                  @Part("nationality_id") RequestBody nationality,
                                                  @Part("gender") RequestBody gender,
                                                  @Part("date_of_birth") RequestBody date_of_birth,
                                                  @Part("passport_id") RequestBody passport_id,
                                                  @Part("mobile") RequestBody mobile);


    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/passengers/update/{id}")
    Observable<EditPassengersResponse> EditPassenger(@Header("Authorization") String token,
                                                     @Path("id") String id ,
                                                     @Part("full_name") RequestBody full_name,
                                                     @Part("nationality_id") RequestBody nationality,
                                                     @Part("gender") RequestBody gender,
                                                     @Part("date_of_birth") RequestBody date_of_birth,
                                                     @Part("passport_id") RequestBody passport_id,
                                                     @Part("mobile") RequestBody mobile,
                                                     @Part MultipartBody.Part passportFile
    );

    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/passengers/update/{id}")
    Observable<EditPassengersResponse> EditPassenger(@Header("Authorization") String token,
                                                     @Path("id") String id ,
                                                     @Part("full_name") RequestBody full_name,
                                                     @Part("nationality_id") RequestBody nationality,
                                                     @Part("gender") RequestBody gender,
                                                     @Part("date_of_birth") RequestBody date_of_birth,
                                                     @Part("passport_id") RequestBody passport_id,
                                                     @Part("mobile") RequestBody mobile);


    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/find-trips.json")
    Observable<SearchTripsResponse> Search(@Query("page") String page,
                                           @Field("from_city") String from_city,
                                           @Field("tripDate") String tripDate,
                                           @Field("to_city") String to_city,
                                           @Field("tripTime") String tripTime,
                                           @Field("passangersCount") String passangersCount,
                                           @Field("bus_type") String bus_type,
                                           @Field("is_direct") String is_direct,
                                           @Field("from_price") String from_price,
                                           @Field("to_price") String to_price,
                                           @Field("company_id") String company_id,
                                           @Field("appCheck") String appCheck
    );

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/find-trips.json")
    Observable<SearchTripsResponse> SearchDetails(@Query("page") String page,
                                                  @Field("tripDate") String tripDate,
                                                  @Field("company_id") String company_id
    );

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/find-trips.json")
    Observable<HomeResponse> SearchHome(
            @Field("tripDate") String tripDate,
            @Field("appCheck") String appCheck,
            @Query("page") String page
    );



    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("collection.json")
    Observable<RegisterCollectionResponse> getRegisterCollection();

    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/user/view/{id}.json")
    Observable<ProfileResponse> getProfileData(@Header("Authorization") String token,
                                               @Path("id") String id);


    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("videoList.json")
    Observable<VideosResponse> getVideosList();

    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/aboutApp.json")
    Observable<AboutAppResponse> getAboutApp();

    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/faq/list.json")
    Observable<FaqResponse> getFaq();


    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/reservations/current-reservations")
    Observable<CurrentOrdersResponse> getCurrentTrips(@Header("Authorization") String token,
                                                      @Query("page") String page);
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/billPdf/{tripId}.json")
    Observable<RequestPdfResponse> requestPdf(@Header("Authorization") String token,
                                              @Path("tripId") String tripId);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/tab-gateway/webview/request")
    Observable<RequestPayModel> requestPay(@Header("Authorization") String token,
                                           @Field("reservation_id") String reservationId);
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/tab-gateway/webview/get-charge-status")
    Observable<CheckPayStatusModel> checkPayStatus(@Header("Authorization") String token,
                                                   @Query("chargeID") String reservationId);
    interface RetrofitDownload {
        @GET("/maven2/com/squareup/retrofit/retrofit/2.0.0-beta2/{fileName}")
        Call<ResponseBody> downloadRetrofit(@Path("fileName") String fileName);
    }
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/reservations/past-reservations")
    Observable<PastOrdersResponse> getPastTrips(@Header("Authorization") String token,
                                                @Query("page") String page);


    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/passengers/index")
    Observable<PassengersResponse> getPassengers(@Header("Authorization") String token,
                                                 @Query("page") String id,
                                                 @Query("limit") int limit);

    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @DELETE("website/passengers/delete/{id}")
    Observable<RemovePassengerResponse> removePassenger(@Header("Authorization") String token,
                                                        @Path("id") String id);



    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("recovery-by-mobile.json")
    Observable<CheckUserResponse> checkIFUserExists(
            @Field("mobile") String mobile
    );


    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/likeDislikeCompany.json")
    Observable<AddToFavouriteResponse> AddToFavourite(@Header("Authorization") String token,
                                                      @Field("companyID") String companyID,
                                                      @Field("status") String status);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/add-rates.json")
    Observable<RateTripResponse> rateTrip(@Header("Authorization") String token,
                                          @Field("reservation_id") String reservation_id,
                                          @Field("comfort") String comfort,
                                          @Field("bus_status") String bus_status,
                                          @Field("other_service") String other_service,
                                          @Field("wc") String wc,
                                          @Field("behavior") String behavior,
                                          @Field("skills") String skills,
                                          @Field("notes") String notes
    );


    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/contact/add.json")
    Observable<ContactUsModel> contactUs(@Field("full_name") String name,
                                         @Field("email") String email,
                                         @Field("mobile") String mobile,
                                         @Field("message") String message);
    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/stations/nearest-by-city.json")
    Observable<StationsResponse> getStations(@Field("city_id") String city_id,
                                             @Field("lat_at") String lat_at,
                                             @Field("long_at") String long_at);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("changePassword.json")
    Observable<changePasswordResponse> changePassword(@Field("mobile_or_passport_id") String mobile_or_passport_id,
                                                      @Field("password") String password,
                                                      @Field("newPassword") String newPassword,
                                                      @Field("rePassword") String rePassword);

    @FormUrlEncoded
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/reservation/cancle")
    Observable<CancelOrderResponse> cancelOrder(@Header("Authorization") String token,
                                                @Field("reservation_id") String reservation_id,
                                                @Field("cancelReason") String cancel_reason);


    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/myFavoriteCompanies.json")
    Observable<MyFavouriteResponse> getMyFavourite(@Header("Authorization") String token);

    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/notifications/index")
    Observable<NotificationsResponse> notifyList(@Header("Authorization") String token,
                                                 @Query("page") int page);

    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/reservation/chkPassportIdFile")
    Observable<CheckPassportResponse> checkPassport(@Header("Authorization") String token);

    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/reservation/create")
    Observable<ReservationResponse> addTripWithReset(@Header("Authorization") String token,
                                                     @Part("sub_passanger_id[]") List<RequestBody> subIdsList,
                                                     @Part ("promoCode")RequestBody promoCode,
                                                     @Part ("payMethod") RequestBody payMethod,
                                                     @Part  ("trip_date_id")RequestBody trip_date_id,
                                                     @Part ("beforeConfirm") RequestBody beforeConfirm,
                                                     @Part ("mainPassanger") RequestBody mainPassanger,
                                                     @Part("passport_id") RequestBody passportID,
                                                     @Part MultipartBody.Part resetImage
    );
    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/reservation/create")
    Observable<ReservationResponse> addTripWithPassport(@Header("Authorization") String token,
                                                        @Part("sub_passanger_id[]") List<RequestBody> subIdsList,
                                                        @Part ("promoCode")RequestBody promoCode,
                                                        @Part ("payMethod") RequestBody payMethod,
                                                        @Part  ("trip_date_id")RequestBody trip_date_id,
                                                        @Part ("beforeConfirm") RequestBody beforeConfirm,
                                                        @Part ("mainPassanger") RequestBody mainPassanger,
                                                        @Part("passport_id") RequestBody passportID,
                                                        @Part MultipartBody.Part passportImage
    );
    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/reservation/create")
    Observable<ReservationResponse> addTrip(@Header("Authorization") String token,
                                            @Part("sub_passanger_id[]") List<RequestBody> subIdsList,
                                            @Part ("promoCode")RequestBody promoCode,
                                            @Part ("payMethod") RequestBody payMethod,
                                            @Part  ("trip_date_id")RequestBody trip_date_id,
                                            @Part ("beforeConfirm") RequestBody beforeConfirm,
                                            @Part("passport_id") RequestBody passportID,
                                            @Part ("mainPassanger") RequestBody mainPassanger,
                                            @Part MultipartBody.Part passportImage

    );
    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/reservation/create")
    Observable<ReservationResponse> addTrip(@Header("Authorization") String token,
                                            @Part("sub_passanger_id[]") List<RequestBody> subIdsList,
                                            @Part ("promoCode")RequestBody promoCode,
                                            @Part ("payMethod") RequestBody payMethod,
                                            @Part  ("trip_date_id")RequestBody trip_date_id,
                                            @Part ("beforeConfirm") RequestBody beforeConfirm,
                                            @Part ("mainPassanger") RequestBody mainPassanger

    );


    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/reservation/create")
    Observable<ReservationConfirmedResponse> addTripWithResetConfirmed(@Header("Authorization") String token,
                                                                       @Part("sub_passanger_id[]") List<RequestBody> subIdsList,
                                                                       @Part ("promoCode")RequestBody promoCode,
                                                                       @Part ("payMethod") RequestBody payMethod,
                                                                       @Part  ("trip_date_id")RequestBody trip_date_id,
                                                                       @Part ("beforeConfirm") RequestBody beforeConfirm,
                                                                       @Part ("mainPassanger") RequestBody mainPassanger,
                                                                       @Part MultipartBody.Part resetImage);

    @Multipart
    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @POST("website/reservation/create")
    Observable<ReservationConfirmedResponse> addTripConfirmed(@Header("Authorization") String token,
                                                              @Part("sub_passanger_id[]") List<RequestBody> subIdsList,
                                                              @Part ("promoCode")RequestBody promoCode,
                                                              @Part ("payMethod") RequestBody payMethod,
                                                              @Part  ("trip_date_id")RequestBody trip_date_id,
                                                              @Part ("beforeConfirm") RequestBody beforeConfirm,
                                                              @Part ("mainPassanger") RequestBody mainPassanger
    );



    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
    @GET("website/company/view/{id}.json")
    Observable<CompanyDetailsResponse> getCompanyDetails(@Header("Authorization") String token,
                                                         @Path("id") String id);

//    @Headers({"CONNECT_TIMEOUT:20000", "READ_TIMEOUT:20000", "WRITE_TIMEOUT:20000"})
//    @GET("MatchVideos/allTeams.json")
//    Observable<TeamsResponse> getTeams();

}