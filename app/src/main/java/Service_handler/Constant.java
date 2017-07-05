package Service_handler;

public interface Constant {

    String Server2 = "https://checkin.flexbillet.dk";
    //String Server2 = "http://34.197.228.70/";
    String LOGIN = Server2 + "/checkinsession/create";
    String UPDATE = Server2 + "/checkinsession/update";
    String Get_Subscription = Server2 + "api/order/getSubscriptionPackage";
    String Get_Testimonial = Server2 + "api/testimonial/getTestimonial";
    String Add_Order = "http://34.197.228.70/api/order/addOrder";
    String Update_profile_pic = Server2 + "api/login/updateUserProfilePic";
    String UPDATE_PASSWORD = Server2 + "api/login/updateUserPassword";
    String UPDATE_USER_PROFILE = Server2 + "api/login/updateUserFromProfile";
    String ADD_TESTIMONIAL = Server2 + "api/testimonial/addTestimonial";
    String GET_USER_PROFILE = Server2 + "/api/login/getProfile";
    String join = Server2 + "/api/login/createNewUser";
    String RESEND_OTP = Server2 + "api/login/resendOTP";
    String GET_ORDERLIST = Server2 + "api/order/getMealForDate";
    String GET_BANNER = Server2 + "api/order/getBanner";
    String ORDER_HISTORY = Server2 + "api/order/getMeal";
    String FORGOT_PASSWORD = Server2 + "api/login/resendOTPWithMobile";


}