<<<<<<< HEAD
package com.zzy.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zzy.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/3/26.
 */
public class PowerInterceptor extends HandlerInterceptorAdapter {
    /**
     *预处理回调方法，实现处理器的预处理（如登录检查）。
     *第三个参数为响应的处理器，即controller。
     *返回true，表示继续流程，调用下一个拦截器或者处理器。
     *返回false，表示流程中断，通过response产生响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // 验证用户是否登陆
        Object obj = request.getSession().getAttribute("username");


        if (obj == null || !(obj instanceof String)) {
            JSONObject json = new JSONObject();
            json.put("status",302);
            json.put("message","请登录！");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(json.toJSONString());
            return false;
        }
        return true;
    }

    /**
     *当前请求进行处理之后，也就是Controller 方法调用之后执行，
     *但是它会在DispatcherServlet 进行视图返回渲染之前被调用。
     *此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     *方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。
     *这个方法的主要作用是用于进行资源清理工作的。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }

=======
package com.zzy.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zzy.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/3/26.
 */
public class PowerInterceptor extends HandlerInterceptorAdapter {
    /**
     *预处理回调方法，实现处理器的预处理（如登录检查）。
     *第三个参数为响应的处理器，即controller。
     *返回true，表示继续流程，调用下一个拦截器或者处理器。
     *返回false，表示流程中断，通过response产生响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // 验证用户是否登陆
        Object obj = request.getSession().getAttribute("username");


        if (obj == null || !(obj instanceof String)) {
            JSONObject json = new JSONObject();
            json.put("status",302);
            json.put("message","请登录！");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(json.toJSONString());
            return false;
        }
        return true;
    }

    /**
     *当前请求进行处理之后，也就是Controller 方法调用之后执行，
     *但是它会在DispatcherServlet 进行视图返回渲染之前被调用。
     *此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     *方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。
     *这个方法的主要作用是用于进行资源清理工作的。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }

>>>>>>> 87862a9414168c7958e3f27eded4d0e8c1646de5
}