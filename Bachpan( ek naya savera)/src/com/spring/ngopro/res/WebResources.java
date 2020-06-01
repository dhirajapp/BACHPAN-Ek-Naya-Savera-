package com.spring.ngopro.res;

public interface WebResources {
	
	String Prefix="http://";
String IP_ADDRESS="careersociety.in";
	String PORT="";
String CONTEXT_PATH="/WebBachpan/";

//	String Prefix="http://";
//String IP_ADDRESS="192.168.1.9";
	//String PORT=":8084";
//String CONTEXT_PATH="/WebBachpan/";
	
	String LOGIN_URL=Prefix+IP_ADDRESS+PORT+CONTEXT_PATH+"LoginServices";
	String LOGIN_FORGOT=Prefix+""+IP_ADDRESS+PORT+""+CONTEXT_PATH+"ForgotPass";
	String RegistrationUrl=Prefix+""+IP_ADDRESS+PORT+""+CONTEXT_PATH+"RegistrationServices";
	String TrackReport=Prefix+""+IP_ADDRESS+PORT+""+CONTEXT_PATH+"GetReportServices";
	String GetAllNgo = Prefix+""+IP_ADDRESS+PORT+""+CONTEXT_PATH+"GetAllNGOServices";
	String Upload= Prefix+""+IP_ADDRESS+PORT+""+CONTEXT_PATH+"UploadServlet";	
//	String LOGIN_FORGOT= "http://192.168.1.7:8084/UploadWeb/UploadServlet";	
//UploadServlet



	/*String LOGIN_URL="http://172.20.10.11:8084/UploadWeb/LoginServices";
	String LOGIN_FORGOT="http://172.20.10.11:8084/UploadWeb/ForgotPass";
	String RegistrationUrl="http://172.20.10.11:8084/UploadWeb/RegistrationServices";
	String TrackReport="http://172.20.10.11:8084/UploadWeb/GetReportServices";
	String GetAllNgo = "http://172.20.10.11:8084/UploadWeb/GetAllNGOServices";*/
	
	/*------------------------write like this easy to change------------------------------------------------------*/

	
	//String LOGIN_URL2=Prefix+IP_ADDRESS+PORT+CONTEXT_PATH+"LoginServices";
	//String LOGIN_FORGOT2=Prefix+IP_ADDRESS+PORT+CONTEXT_PATH+"ForgotPass";
	//String RegistrationUrl2=Prefix+IP_ADDRESS+PORT+CONTEXT_PATH+"RegistrationServices";
	//String TrackReport2=Prefix+IP_ADDRESS+PORT+CONTEXT_PATH+"GetReportServices";
	//String GetAllNgo2=Prefix+IP_ADDRESS+PORT+CONTEXT_PATH+"GetAllNGOServices";
	
	/*------------------------------------------------------------------------------------------------------------*/
	
//	String LOGIN_URL = "http://careersociety.in/UploadWeb/LoginServices";
	//String RegistrationUrl = "http://careersociety.in/UploadWeb/RegistrationServices";
//	String TrackReport="http://careersociety.in/UploadWeb/GetReportServices";
	//String GetAllNgo = "http://careersociety.in/UploadWeb/GetAllNGOServices";
	
//	String LOGIN_URL = "http://192.168.1.13:80/bachapan/bachapan/public/u/login.php";
//	String RegistrationUrl = "http://192.168.1.13:80/bachapan/bachapan/public/u/register.php";
}
