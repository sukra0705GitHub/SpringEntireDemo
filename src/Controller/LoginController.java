package Controller;

import Implement.Student;
import Interface.StudentIF;
import Utils.FileManager;
import Utils.FinalConst;
import com.sun.net.httpserver.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@Component
@Controller
@RequestMapping("/")
public class LoginController {
    //@Autowired
    //HttpServletRequest httpServletRequest;

    @GetMapping("/mvindex")
    public ModelAndView index(ModelAndView mv){
        System.out.println("Triggle Index!");
        mv.setViewName("index");

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        mv.addObject("student", personMap);
        mv.addObject("name", "Sukra");

        return mv;
    }

    @GetMapping("/mv1index")
    public ModelAndView index(){
        System.out.println("Triggle Index!");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        mv.addObject("student", personMap);
        mv.addObject("name", "Sukra");

        return mv;
    }

    @GetMapping("/index")
    public String index(ModelMap mm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("Triggle Index!");
        System.out.println("RequestMethod:" + httpServletRequest.getMethod());
        //Response setting
        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("Content-Type", "text/html;charset=urf-8");
        httpServletResponse.setHeader("Access-Control-Allow-origin", "http://172.31.162.193:8080/");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST");

        //Test request parameter
        System.out.println("URI:" + httpServletRequest.getRequestURI());
        System.out.println("PathInfo:" + httpServletRequest.getPathInfo());
        System.out.println("ContextPath:" + httpServletRequest.getContextPath());
        System.out.println("Servlet-ContextPath:" + httpServletRequest.getServletContext().getContextPath());
        System.out.println("ServletPath:" + httpServletRequest.getServletPath());
        System.out.println("RealPath:" + httpServletRequest.getServletContext().getRealPath(httpServletRequest.getRequestURI()).trim());

        /*String requestPathName;
        String basePath = "C:/JetBrains/ideaProjectsLocation/HelloJava";
        String requestPath = httpExchange.getRequestURI().getPath();
        String host = httpExchange.getRequestURI().getHost();
        System.out.println("host: "+ host + "__path: " + requestPath);
        if (requestPath.matches("/.+")){
            requestPathName = basePath + requestPath;
        } else {
            requestPathName = "C:\\JetBrains\\ideaProjectsLocation\\HelloJava\\WebPage\\index.html";
        }*/

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        mm.addAttribute("student", personMap);
        mm.addAttribute("name", "Sukra");

        return "index";
    }

    /**
     *  This method transfer the object failed to ftl.  We can't transfer object to front page by new ModelMap directly but can define ModelMap on method parameter.
     * @return
     */
    @GetMapping("/mmindex")
    public String index1(){
        System.out.println("Triggle Index!");

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        ModelMap mm = new ModelMap();
        mm.addAttribute("student", personMap);
        mm.addAttribute("name", "Sukra");

        return "index";
    }

    @GetMapping("/login/{userId}")
    @ResponseBody
    public String login(@PathVariable String userId, @RequestParam("username") String username){
        System.out.println("Triggle Login controller!");
        System.out.println("UserId:" + userId + "_UserName:" + username);
        return "Login";
    }

    @GetMapping("/download")
    public void downLoad(@RequestParam("fileName") String fileName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        //Example For DownLoad TxT file
        FileManager fileManager = new FileManager();
        File file = fileManager.createFile(fileName);
        fileManager.writeInfoToFile("Hello Requester! File info! Success!", file);

        //Example For DownLoad PDF file
            /*responseHeader.add("Content-Disposition","attachment;filename=downLoad.txt");//inline
            responseHeader.add("Content-Type", "application/pdf");
            File file = new File("C:\\JetBrains\\ideaProjectsLocation\\HelloJava\\test.pdf");*/

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileByte = new byte[fileInputStream.available()];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        bufferedInputStream.read(fileByte,0 ,fileInputStream.available());

        httpServletResponse.addHeader("Content-Disposition","attachment;filename=downLoad.txt");
        httpServletResponse.addHeader("Content-Type", "text/plain");
        httpServletResponse.setStatus(HttpURLConnection.HTTP_OK);
        httpServletResponse.setContentLength(fileInputStream.available());

        OutputStream responseBody = httpServletResponse.getOutputStream();
        responseBody.write(fileByte, 0, fileByte.length);
        if (responseBody != null){
            responseBody.close();
        }
    }

    @GetMapping("/downloading")
    public void downLoadFile(@RequestParam("fileName") String fileName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        //Example For DownLoad TxT file
        httpServletResponse.addHeader("Content-Disposition","attachment;filename=excel.xls");
        httpServletResponse.addHeader("Content-Type", "application/vnd.ms-excel");
        //File file = new File("C:\\JetBrains\\ideaProjectsLocation\\HelloJava\\excel.xml");
        //File file = new File("C:\\JetBrains\\ideaProjectsLocation\\HelloJava\\src\\excelTest.xml");

            /*commonUtil.FileManager fileManage = new commonUtil.FileManager();
            File file = fileManage.createFile("downLoad.txt");
            fileManage.writeInfoToFile("Hello function service! File info! Success!", file);*/

        //Example For DownLoad PDF file
            /*responseHeader.add("Content-Disposition","attachment;filename=downLoad.txt");//inline
            responseHeader.add("Content-Type", "application/pdf");
            File file = new File("C:\\JetBrains\\ideaProjectsLocation\\HelloJava\\test.pdf");*/

            /*FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileByte = new byte[fileInputStream.available()];
            fileInputStream.read(fileByte);*/

            /*BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(fileByte,0 ,fileInputStream.available());*/
        //httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, fileInputStream.available());
        String testString = FinalConst.excelString;
        httpServletResponse.setStatus(HttpURLConnection.HTTP_OK);
        httpServletResponse.setContentLength(testString.getBytes().length);

        OutputStream responseBody = httpServletResponse.getOutputStream();
        DataOutputStream dataInputStream = new DataOutputStream(responseBody);
        dataInputStream.write(testString.getBytes());
        if (dataInputStream != null){
            dataInputStream.close();
        }

        //outputStreamWriter can't write info to download file, the down load file content is empty.
            /*OutputStreamWriter outputStreamWriter = new OutputStreamWriter(responseBody);
            outputStreamWriter.write(new String(fileByte));*/

        //responseBody.write(fileByte, 0, fileByte.length);
        //responseBody.write(fileByte);
            /*if (responseBody != null){
                responseBody.close();
            }*/
    }

}
