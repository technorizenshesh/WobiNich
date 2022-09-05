package com.my.wobinichapp.utils;

import com.my.wobinichapp.model.AddGrpModel;
import com.my.wobinichapp.model.AddNewMemberModel;
import com.my.wobinichapp.model.AddPostModel;
import com.my.wobinichapp.model.CreateFolderModel;
import com.my.wobinichapp.model.ForgotPasword;
import com.my.wobinichapp.model.GetAllWordModel;
import com.my.wobinichapp.model.GetFolderModel;
import com.my.wobinichapp.model.GetGrpModel;
import com.my.wobinichapp.model.GetPostBluVoilet;
import com.my.wobinichapp.model.GetPostModel;
import com.my.wobinichapp.model.GetTypeModel;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.GrpChatModel;
import com.my.wobinichapp.model.InsertGrpModel;
import com.my.wobinichapp.model.LeaveModel;
import com.my.wobinichapp.model.LoginModel;
import com.my.wobinichapp.model.PostSeenModel;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.model.SingleGetChatModel;
import com.my.wobinichapp.model.UpdateGrpModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface Api {

    // Oops Manager

    String signup = "signup";
    String login = "login?";
    String forgot_password = "forgot_password";
    String get_profile = "get_profile";
    String update_status = "update_status";
    String update_image = "update_image";
    String update_user = "update_user";
    String change_password = "change_password";
    String get_users = "get_users";
    String addgroupmember = "addgroupmember";
    String get_group = "get_group";
    String change_group_image = "change_group_image";
    String change_group_name = "change_group_name";
    String get_group_member = "get_group_member";

    String get_group_chat = "get_group_chat";
    String insert_group_chat = "insert_group_chat";

    String create_folder = "create_folder";
    String get_folder = "get_folder";
    String delete_folder = "delete_folder";
    String folder_rename = "folder_rename";
    String get_post = "get_post";
    String get_post_data = "get_post_data";
    String post_seen = "post_seen";

    String get_chat = "get_chat";
    String insert_chat = "insert_chat";
    String get_new_users = "get_new_users";
    String add_new_member = "add_new_member";
    String leave_group_member = "leave_group_member";

    String add_post = "add_post";
    String get_wobinich_type = "get_wobinich_type";
    String AllWOrd_user = "get_all_world";
    String get_all_post = "get_all_post";

    @FormUrlEncoded
    @POST(signup)
    Call<SignUpModel> signup(
            @Field("name") String name,
            @Field("contact") String contact,
            @Field("email") String email,
            @Field("password") String password,
            @Field("register_id") String register_id
    );

    @FormUrlEncoded
    @POST(login)
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("register_id") String register_id
    );

    @FormUrlEncoded
    @POST(change_group_name)
    Call<UpdateGrpModel> change_group_name(
            @Field("group_id") String group_id,
            @Field("group_name") String group_name
    );

    @FormUrlEncoded
    @POST(update_status)
    Call<SignUpModel> update_status(
            @Field("user_id") String user_id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST(change_password)
    Call<SignUpModel> change_password(
            @Field("user_id") String user_id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(get_profile)
    Call<SignUpModel> get_profile(
            @Field("user_id") String user_id
    );

  /*  @Multipart
    @POST(update_image)
    Call<SignUpModel> update_image(
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part part1
    );*/

    @POST(update_image)
    Call<ResponseBody> updateProfile(@Body RequestBody file);

    @POST(change_group_image)
    Call<ResponseBody> change_group_image(@Body RequestBody file);

   /* @Multipart
    @POST(change_group_image)
    Call<UpdateGrpModel> change_group_image(
            @Part("group_id") RequestBody group_id,
            @Part MultipartBody.Part part1
    );*/


    @FormUrlEncoded
    @POST(get_users)
    Call<GetUserModel> get_users(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_new_users)
    Call<GetUserModel> get_new_users(
            @Field("group_id") String group_id
    );

    @FormUrlEncoded
    @POST(add_new_member)
    Call<AddNewMemberModel> add_new_member(
            @Field("group_id") String group_id,
            @Field("member_id") String member_id
    );

    @FormUrlEncoded
    @POST(get_group)
    Call<ResponseBody> get_group(
            @Field("user_id") String user_id
    );

    @Multipart
    @POST(addgroupmember)
    Call<AddGrpModel> addgroupmember(
            @Part("user_id") RequestBody user_id,
            @Part("member_id") RequestBody member_id,
            @Part("group_name") RequestBody group_name,
            @Part("description") RequestBody description,
            @Part("group_admin") RequestBody group_admin,
            @Part MultipartBody.Part part1
    );

    @FormUrlEncoded
    @POST(get_group_chat)
    Call<ResponseBody> get_group_chat(
            @Field("group_id") String group_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_wobinich_type)
    Call<GetTypeModel> get_wobinich_type(
            @Field("user_id") String user_id,
            @Field("type") String type
    );


    @FormUrlEncoded
    @POST(get_group_member)
    Call<ResponseBody> get_group_member(
            @Field("group_id") String group_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(insert_group_chat)
    Call<InsertGrpModel> insert_group_chat(
            @Field("sender_id") String sender_id,
            @Field("group_id") String group_id,
            @Field("chat_message") String chat_message
    );

    @FormUrlEncoded
    @POST(create_folder)
    Call<CreateFolderModel> create_folder(
            @Field("user_id") String user_id,
            @Field("folder_name") String folder_name,
            @Field("type") String type,
            @Field("folder_image") String folder_image
    );

    @FormUrlEncoded
    @POST(get_folder)
    Call<ResponseBody> get_folder(
            @Field("user_id") String user_id,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(delete_folder)
    Call<ResponseBody> delete_folder(
            @Field("folder_id") String folder_id
    );

 @FormUrlEncoded
    @POST(folder_rename)
    Call<ResponseBody> folder_rename(
            @Field("folder_id") String folder_id,
            @Field("folder_name") String folder_name
    );

    @FormUrlEncoded
    @POST(get_post)
    Call<GetPostModel> get_post(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_post_data)
    Call<ResponseBody> get_post_data(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(post_seen)
    Call<PostSeenModel> post_seen(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_chat)
    Call<SingleGetChatModel> get_chat(
            @Field("sender_id") String sender_id,
            @Field("receiver_id") String receiver_id
    );

    @FormUrlEncoded
    @POST(insert_chat)
    Call<InsertGrpModel> insert_chat(
            @Field("sender_id") String sender_id,
            @Field("receiver_id") String receiver_id,
            @Field("chat_message") String chat_message
    );

    @FormUrlEncoded
    @POST(forgot_password)
    Call<ForgotPasword> forgot_password(
            @Field("email") String sender_id
    );

    @FormUrlEncoded
    @POST(leave_group_member)
    Call<LeaveModel> leave_group_member(
            @Field("group_id") String group_id,
            @Field("member_id") String member_id
    );

    @FormUrlEncoded
    @POST(update_user)
    Call<SignUpModel> update_user(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact
    );

   /* @Multipart
    @POST(add_post)
    Call<AddPostModel> add_post(
            @Part("user_id") RequestBody user_id,
            @Part("group_id") RequestBody group_id,
            @Part("lat") RequestBody lat,
            @Part("lon") RequestBody lon,
            @Part("comment") RequestBody comment,
            @Part("maps") RequestBody maps,
            @Part("website") RequestBody website,
            @Part("type") RequestBody type,
            @Part("landscape") RequestBody landscape,
            @Part("meal") RequestBody meal,
            @Part("places") RequestBody places,
            @Part("shop") RequestBody shop,
            @Part MultipartBody.Part part1
    );*/

    @POST(add_post)
    Call<ResponseBody> add_post(@Body RequestBody file);


    @FormUrlEncoded
    @POST(AllWOrd_user)
    Call<GetAllWordModel>get_all_world(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_all_post)
    Call<GetAllWordModel>get_all_post(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_challenge")
    Call<ResponseBody>get_all_answer(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("add_user_answer")
    Call<ResponseBody>submit_answer(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("update_user_answer")
    Call<ResponseBody>check_answer(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("get_post_detail")
    Call<ResponseBody>post_detail(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("add_favorite")
    Call<ResponseBody>postFav(@FieldMap Map<String,String> params);


    @FormUrlEncoded
    @POST("get_favorite")
    Call<ResponseBody>getAllFav(@FieldMap Map<String,String> params);





}
