package com.wei.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wei.entity.MiaoShaUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author weiwei
 */
public class UserUtil {

    private static void createUser(int count) throws Exception{
        List<MiaoShaUser> users = new ArrayList(count);
        //生成用户
        for(int i=0;i<count;i++) {
            MiaoShaUser user = new MiaoShaUser();
            user.setId(String.valueOf(13000000000L+i));
            user.setCount(1);
            user.setName("user"+i);
            user.setRegister(new Date());
            user.setSalt("1a2b3c4d");
            user.setPassword(Md5Util.inputPassToDbPass("123123", user.getSalt()));
            users.add(user);
        }
        System.out.println("create user");
//		//插入数据库
	/*	Connection conn = DBUtil.getConn();
		String sql = "insert into miaosha(login_count, name, register_date, salt, password, id)values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for(int i=0;i<users.size();i++) {
			MiaoShaUser user = users.get(i);
			pstmt.setInt(1, user.getCount());
			pstmt.setString(2, user.getName());
			pstmt.setTimestamp(3, new Timestamp(user.getRegister().getTime()));
			pstmt.setString(4, user.getSalt());
			pstmt.setString(5, user.getPassword());
			pstmt.setLong(6, Long.valueOf(user.getId()));
			//添加到批量执行sql任务里
			pstmt.addBatch();
		}
		//执行批量任务
		pstmt.executeBatch();
		pstmt.close();
		conn.close();
		System.out.println("insert to db");*/
        //登录，生成token
        String urlString = "http://localhost:8080/login/do_login";
        File file = new File("D:/tokens.txt");
        if(file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        //文件指针从头开始
        raf.seek(0);
        for(int i=0;i<users.size();i++) {
            MiaoShaUser user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection)url.openConnection();
            co.setRequestMethod("POST");
            //设置可以传输参数
            co.setDoOutput(true);
            //获取输出流
            OutputStream out = co.getOutputStream();
            String params = "mobile="+user.getId()+"&password="+Md5Util.inputPassFromPass("123123");
            out.write(params.getBytes());
            out.flush();
            //获取输入流
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0 ,len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            JSONObject jo = JSON.parseObject(response);
            String token = jo.getString("data");
            System.out.println("create token : " + user.getId());

            String row = user.getId()+","+token;
            //将写文件指针移到文件尾
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();

        System.out.println("over");
    }

    public static void main(String[] args)throws Exception {
        createUser(50000);
    }
}