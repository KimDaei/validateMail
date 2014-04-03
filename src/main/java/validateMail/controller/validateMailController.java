package validateMail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/*")
public class validateMailController {

	@SuppressWarnings("unchecked")
	@RequestMapping("validate_email")
	public String validateMail(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String,?> params) {

		JSONObject jsonObject = new JSONObject();
		if( null == params.get("email") ){
			jsonObject.put("resulst", "fail" );
			jsonObject.put("msg",  "파라메터가 누락되었습니다." );
			returnMsg(response, jsonObject);
			return null;
		}
		
		System.out.println("========== [emailt] : " + params.get("email").toString() );
		String pattern = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		
		jsonObject.put( "result", params.get("email").toString().matches(pattern) );
		jsonObject.put( "email", params.get("email").toString() );
		returnMsg(response, jsonObject);
		return null;
	}
	
	private void returnMsg(HttpServletResponse response, JSONObject jsonObject){
		
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter printWriter = null;
		try{
			printWriter = response.getWriter();
			printWriter.write(jsonObject.toJSONString());
			printWriter.flush();
		}catch(IOException e){
			System.out.println("========== [출력 스트림 오류] =========");
		}catch(Exception e){
			System.out.println("========== [시스템 오류] =========");
		}finally{
			if( null != printWriter ){
				printWriter.close();
			}
		}
	}
}