package com.heng.controller;

import com.heng.domain.User;
import com.heng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author lfy
 * @Description:
 * @date 2020/9/1617:56
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/tologin")
    public String toLogin() {

        return "login";
    }

    /**
     * 登录用户
     * @param redirectAttributes
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/login.action")
    public String login(RedirectAttributes redirectAttributes, User user, HttpSession session) {

        //根据用户名查询用户
        List<User> users = userService.getUserByLoginName(user);

        if (users == null || users.size() == 0) {
            //用户不存在
            String message = "用户名不存在";
            redirectAttributes.addFlashAttribute("message", message);
            //跳回登录页面
            return "redirect:/tologin";
        } else {
            //用户存在
            if (users != null && users.size() > 0) {
                String loginName = users.get(0).getLoginName();
                String passWord = users.get(0).getPassWord();

                if (loginName.equals(user.getLoginName()) && passWord.equals(user.getPassWord())) {
                    //用户名和密码正确
                    session.setAttribute("session_user", users.get(0));
                    //登录成功,跳到首页
                    return "redirect:/toIndex";
                } else {
                    String message = "用户名或密码错误";

                    redirectAttributes.addFlashAttribute("message", message);
                    redirectAttributes.addFlashAttribute("loginName", loginName);
                    redirectAttributes.addFlashAttribute("passWord", passWord);
                }
            }
            //跳回登录页面
            return "redirect:/tologin";
        }
    }

    /**
     * 退出用户
     * @param session
     * @return
     */
    @RequestMapping("/quit.action")
    public String quit(HttpSession session){

        session.removeAttribute("session_user");
        return "redirect:/toIndex";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegister")
    public String register(){

        return "register";
    }

    /**
     * 注册用户,用户名验证
     * @param user
     * @return
     */
    @RequestMapping("/toRegisterVaild")
    @ResponseBody
    public String toRegisterVaild(@RequestBody User user){

        String errorMessage = null;
        List<User> users = userService.getUserByLoginName(user);
        if (users != null && users.size() > 0){
            errorMessage = "用户名已存在! ! !";
            return errorMessage;
        }
        return errorMessage;
    }

    /**
     * 注册用户
     * @param user
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/register.action")
    public String registerUser(User user, HttpSession session,Model model){

        if(session.getAttribute("verify_code") != null && !"".equals(session.getAttribute("verify_code"))){
            if (user.getAuthcode().equals(session.getAttribute("verify_code"))){
                user.setCreateDate(new Date());
                userService.saveUser(user);
                return "redirect:/tologin";
            }else {
                String tip = "验证码不正确";
                model.addAttribute("tip",tip);
                model.addAttribute("registerUser",user);
            }
        }
        return "forward:/toRegister";
    }



    //验证码
    private static final long serialVersionUID = 2554915394390709685L;
    /** 定义验证码图片的宽度 */
    private static final int IMG_WIDTH = 60;
    /** 定义验证码图片的高度 */
    private static final int IMG_HEIGHT = 22;
    /** 定义一个Random对象 */
    private static Random random = new Random();
    /** 定义字体对象 */
    private static Font font = new Font("宋体", Font.BOLD, 18);

    /*
     * 生成验证码
     * */
    @RequestMapping(value = "/verify.action")
    public void verify(HttpServletResponse response, HttpSession session)
            throws IOException {

        /** 设置响应的内容类型 */
        response.setContentType("images/jpeg");
        /** 创建一个图片缓冲流对象 */
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        /** 获取到画笔 */
        Graphics g = image.getGraphics();
        /** 填充一个矩形框 */
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
        /** 设置画笔的颜色 */
        g.setColor(Color.BLACK);
        /** 绘制一个矩形框 */
        g.drawRect(0, 0, IMG_WIDTH - 1, IMG_HEIGHT - 1);
        /** 绘制干扰线 */
        for (int i = 0; i < 50; i++){
            /** 设置画笔的颜色(颜色是随机生成) */
            g.setColor(new Color(180 + random.nextInt(75),
                    180 + random.nextInt(75),
                    180 + random.nextInt(75)));
            // 第一点
            int x1 = 2 + random.nextInt(IMG_WIDTH - 4);
            int y1 = 2 + random.nextInt(IMG_HEIGHT - 4);
            // 第二点
            int x2 = 2 + random.nextInt(IMG_WIDTH - 4);
            int y2 = 2 + random.nextInt(IMG_HEIGHT - 4);
            g.drawLine(x1, y1, x2, y2);
        }

        /** 绘制验证码(随机生成四个验证码) */
        g.setFont(font); // 设置字体
        String code = ""; // 保存最终生成的验证码
        for (int i = 0; i < 4; i++){
            String temp = generatorVerify();
            code += temp;
            /** 设置画笔的颜色(颜色是随机生成) */
            g.setColor(new Color(random.nextInt(20),
                    random.nextInt(40),
                    random.nextInt(20)));

            int offsetLeft = transferFrom(g);

            g.drawString(temp, 13 * i + offsetLeft, 17);
        }
        System.out.println(code);
        //生成验证码后保存至session中
        session.setAttribute("verify_code", code);
        /** 消毁画笔 */
        g.dispose();
        /** 输出 */
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }
    /**
     * 画笔位置倾斜方法
     * @param g
     * @return
     */
    private int transferFrom(Graphics g) {
        Graphics2D gr = (Graphics2D)g;
        AffineTransform tr =  gr.getTransform();
        // 随机生成倾斜率
        double shx = Math.random();
        // 保证倾斜率在(0.25-0.55)之间
        if (shx < 0.25) {
            shx = 0.25;
        }
        if (shx > 0.55) {
            shx = 0.55;
        }
        // 随机向右是左倾斜
        int temp = random.nextInt(2);
        int offsetLeft = 2;
        if (temp == 0){
            shx = 0 - shx;
            offsetLeft = 10;
        }
        tr.setToShear(shx, 0);
        gr.setTransform(tr);
        return offsetLeft;
    }
    /**
     * 随机生成一个验证码(大写字母、小写字母、数字、汉字)
     * @return
     */
    private String generatorVerify(){
        /** 随机生成0-3之间的数字 */
        int witch = (int)Math.round((Math.random() * 2));
        witch = 2;
        switch (witch){
            case 0: // 生成大写字母(A-Z|65-90)
                long temp = Math.round(Math.random() * 25 + 65);
                return String.valueOf((char)temp);
            case 1: // 生成小写字母(a-z|97-122)
                temp = Math.round(Math.random() * 25 + 97);
                return String.valueOf((char)temp);
            case 2: // 生成数字(0-9)
                return String.valueOf(Math.round(Math.random() * 9));
            default: // 生成汉字(0x4E00-0x9FBF)
                temp = Math.round(Math.random() * 500 + 0x4E00);
                return String.valueOf((char)temp);
        }
    }

}
