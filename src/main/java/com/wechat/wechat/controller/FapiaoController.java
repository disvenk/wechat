package com.wechat.wechat.controller;

import com.wechat.wechat.gongzhonghaoLogin.EmailUtil;
import com.wechat.wechat.utils.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * Created by disvenk.dai on 2018-06-13 16:56
 */
@Controller
@RequestMapping("fapiao")
public class FapiaoController {

    @RequestMapping("fapiao")
    @ResponseBody
    public String getTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String strDirPath =request.getContextPath();
        System.out.println(strDirPath);

        HttpClient client = new DefaultHttpClient();
       // String ticket = FaPiaoUtils.getTicket("20141234123412341", "201712121708060405", "91310230MA1K05DW59");
        String ticket ="";
        HttpGet get = new HttpGet(ticket);
        HttpResponse execute = null;
        //try {
            execute = client.execute(get);
            InputStream inputStream =  execute.getEntity().getContent();
            ServletOutputStream outputStream =response.getOutputStream();
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String path = new FapiaoController().getClass().getResource("/").getPath();

                                     //获取item中的上传文件的输入流
        String s = UUID.randomUUID().toString();
        //创建一个文件输出流
                          BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                          FileOutputStream out = new FileOutputStream(path +s +".pdf");
                          BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
                              //创建一个缓冲区
                                 byte buffer[] = new byte[1024];
                                 //判断输入流中的数据是否已经读完的标识
                                   int len = 0;
                                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                                while((len=bufferedInputStream.read(buffer))>0){
                                      //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                                    bufferedOutputStream.write(buffer, 0, len);
                                      }
                               //关闭输入流
                                     inputStream.close();
                                   //关闭输出流
                                out.close();
                                 //删除处理文件上传时生成的临时文件

        String path1 = new FapiaoController().getClass().getResource("/"+s+".pdf").getPath();
        //boolean b = EmailUtil.sendEmail(new Email("smtp.163.com", "dd", "13317182430", "yanduo@24799", "yanzhuan051", "13477058114@163.com", "545279664@qq.com", "自动回复", "来自卷神的回复", new String[]{path1}));
        Boolean b = EmailUtil.sendEmail("发票发送通知","小满手工粉","13477058114@163.com","" +
                "",null,new File(path1));
        if(b){
            File file = new File(path1);
            file.delete();
        }
        // byte[] bytes = new byte[1024*1024];
            //inputStream.read(bytes);
            /*response.setContentType("application/pdf");

            String filename = "fapiao.pdf";
            String agent = request
                    .getHeader("user-agent");
            filename = FileUtils.encodeDownloadFilename(filename, agent);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + filename);
            response.addHeader("Content-Length", "(37509");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for(int i;(i=inputStream.read())!=-1;){
                baos.write(i);
            }
            baos.flush();
            Document doc = new Document();
            doc.open();
            PdfStream pdfStream=new PdfStream(baos.toByteArray());
            PdfWriter pw = null;
            try {
                pw = PdfWriter.getInstance(doc,outputStream);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            pdfStream.toPdf(pw,outputStream);
            pw.flush();

            baos.close();
            pw.close();
            outputStream.close();
            inputStream.close();
         //   doc.close();

        } catch (IOException e) {
            e.printStackTrace();*/
        //}
       return null;
    }

    public static void main(String[] args) {
        Boolean b = EmailUtil.sendEmail("发票发送通知","小满手工粉","13477058114@163.com","" +
                "",null,null);
    }
}
